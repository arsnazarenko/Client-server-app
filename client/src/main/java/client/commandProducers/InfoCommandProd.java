package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.InfoCommand;

public class InfoCommandProd implements StandardCommandProducer{

    @Override
    public Command createCommand(UserData userData) {
        return new InfoCommand(userData);
    }
}
