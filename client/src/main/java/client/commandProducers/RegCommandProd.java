package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.RegCommand;

public class RegCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new RegCommand(userData);
    }
}
