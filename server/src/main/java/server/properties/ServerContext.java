package server.properties;

public class ServerContext {

    public ServerContext() {

    }

    private String serverHost;
    private int serverPort;
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUserName;
    private String dbPassword;

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(String serverPort) {
        try {
            this.serverPort = Integer.parseInt(serverPort);
        } catch (NumberFormatException e) {
            throw new IllegalPropertyException(ServerPropertiesReader.ServerPropertiesNames.SERVER_PORT.getPropNameFromFile(),
                    serverPort);
        }
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
