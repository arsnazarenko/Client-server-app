package server.properties;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerPropertiesReader implements IServerPropertiesReader {
    private final static Logger logger = LogManager.getLogger(ServerPropertiesReader.class.getName());

    private final String PROPERTIES_PATH;

    public ServerPropertiesReader(String propertiesPath) {
        PROPERTIES_PATH = propertiesPath;
    }

    private boolean propertyIsValid(String propValue) {
        return propValue != null && !propValue.isEmpty() && !propValue.isBlank();
    }

    public ServerContext read() throws IOException {
        final Pattern pattern = Pattern.compile("^\\$\\{.+\\}$");
        final Properties properties = new Properties();
        boolean dotEnvLoaded = false;
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.configure().load();
            dotEnvLoaded = true;
        } catch (DotenvException e) {
            logger.info("Dotenv file was not loaded");
        }
        final ServerContext serverContext = new ServerContext();
        try (InputStream inputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(inputStream);
        }

        for (ServerPropertiesNames propKey : ServerPropertiesNames.values()) {
            String propValue = properties.getProperty(propKey.getPropNameFromFile());
            if (propValue == null || propValue.isEmpty() || propValue.isBlank()) {
                throw new IllegalPropertyException(propKey.getPropNameFromFile(), propValue);
            }
            Matcher matcher = pattern.matcher(propValue);
            if (matcher.find()) {
                String envValue = matcher.group().substring(2, matcher.group().length() - 1);
                String systemEnv = System.getenv(envValue);
                if (systemEnv != null) {
                    propValue = systemEnv;
                }
                else if (dotEnvLoaded) {
                    propValue = dotenv.get(envValue, propValue);
                }
            }
            propKey.getSetterHandler().accept(serverContext, propValue);
        }
        return serverContext;
    }


    enum ServerPropertiesNames {

        SERVER_HOST("server.host", ServerContext::setServerHost),
        SERVER_PORT("server.port", ServerContext::setServerPort),
        DB_HOST("database.host", ServerContext::setDbHost),
        DB_PORT("database.port", ServerContext::setDbPort),
        DB_NAME("database.name", ServerContext::setDbName),
        DB_USERNAME("database.username", ServerContext::setDbUserName),
        DB_PASSWORD("database.password", ServerContext::setDbPassword);



        private final String propNameFromFile;
        private final BiConsumer<ServerContext, String> setterHandler;

        ServerPropertiesNames(String propNameFromFile, BiConsumer<ServerContext, String> setterHandler) {
            this.propNameFromFile = propNameFromFile;
            this.setterHandler = setterHandler;
        }


        public String getPropNameFromFile() {
            return propNameFromFile;
        }

        public BiConsumer<ServerContext, String> getSetterHandler() {
            return setterHandler;
        }
    }

}
