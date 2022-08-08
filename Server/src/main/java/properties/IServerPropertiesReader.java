package properties;

import java.io.IOException;

public interface IServerPropertiesReader {
    ServerContext read() throws IOException;
}
