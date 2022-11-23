import library.model.Organization;
import library.model.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.*;
import server.business.dao.*;
import server.business.services.IService;
import server.business.services.ServerHandler;
import server.business.services.ServerReceiver;
import server.business.services.ServerSender;
import server.properties.ServerContext;
import server.properties.ServerPropertiesReader;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStarter {

    private final static Logger logger = LogManager.getLogger(ServerStarter.class.getName());
    private static final String PROPERTY_FILE = "server.properties";

    public static void run(SocketAddress serverAddress, String host, String port,
                           String dataBaseName, String user, String password) throws SQLException, ClassNotFoundException, SocketException {

        DatabaseCreator.init(host, port, dataBaseName, user, password);
        ObjectDAO<Organization, Long> orgDao = new OrganizationDAO(DatabaseCreator.getConnection());
        UserDAO<UserData, String> userDAO = new UserDaoImpl(DatabaseCreator.getConnection());
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.setOrgCollection(orgDao.readAll());
        IHandlersController handlersManager = new HandlersController(collectionManager, orgDao, userDAO);
        Map<Class<?>, BlockingQueue<LetterInfo>> queues = new HashMap<>();
        queues.put(ServerHandler.class, new ArrayBlockingQueue<>(35));
        queues.put(ServerSender.class, new ArrayBlockingQueue<>(35));

        MessageSystem messageSystem = new MessageSystem(queues);

        IService[] services = new IService[3];
        ServerReceiver receiver = new ServerReceiver(messageSystem, 256 * 1024, DatabaseCreator.getConnection(), serverAddress);
        DatagramSocket serverSocket = receiver.openConnection(); // открываем соединение, при ошибке сервер закроется
        services[0] = receiver;
        services[1] = new ServerHandler(handlersManager, messageSystem);
        services[2] = new ServerSender(serverSocket, messageSystem);
        for (IService service : services) {
            service.start();
        }

    }

    public static void main(String[] args) {
        ServerPropertiesReader propertiesReader = new ServerPropertiesReader(PROPERTY_FILE);

        try {
            ServerContext context = propertiesReader.read();
            InetSocketAddress sock = new InetSocketAddress(context.getServerHost(), context.getServerPort());
            String dbHost = context.getDbHost();
            String dbPort = context.getDbPort();
            String dbName = context.getDbName();
            String dbUserName = context.getDbUserName();
            String dbPassword = context.getDbPassword();
            run(sock, dbHost, dbPort, dbName, dbUserName, dbPassword);
        } catch (SQLException e) {
            logger.error("Error connecting to database");
            logger.info("Establish the correct database connection");
        } catch (ClassNotFoundException e) {
            logger.error("DB driver Class load error");
        } catch (IllegalArgumentException e) {
            logger.error("INVALID PARAMETERS");
        } catch (SocketException e) {
            logger.error("SERVER STARTING ERROR");
        } catch (IOException ex) {
            logger.error("Properties file with name \"" + PROPERTY_FILE + "\" not found");
        }
    }

}
