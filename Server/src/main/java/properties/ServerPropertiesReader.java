package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.BiConsumer;

public class ServerServerPropertiesReader implements IServerPropertiesReader {
    private final String PROPERTIES_PATH;

    public ServerServerPropertiesReader(String propertiesPath) {
        PROPERTIES_PATH = propertiesPath;
    }

    private boolean propertyIsValid(String propValue) {
        return propValue != null && !propValue.isEmpty() && !propValue.isBlank();
    }

    public ServerContext read() throws IOException {
        final Properties properties = new Properties();
        final ServerContext serverContext = new ServerContext();
        try (InputStream inputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(inputStream);
        }

        for (ServerPropertiesNames propKey : ServerPropertiesNames.values()) {
            String propValue = properties.getProperty(propKey.getPropNameFromFile());
            if (propValue == null || propValue.isEmpty() || propValue.isBlank()) {
                throw new IllegalPropertyException(propKey.getPropNameFromFile(), propValue);
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
