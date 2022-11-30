package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.ShowCommand;

public class ShowCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new ShowCommand(userData);
    }
}
