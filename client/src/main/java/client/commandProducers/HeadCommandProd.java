package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.HeadCommand;

public class HeadCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new HeadCommand(userData);
    }
}
