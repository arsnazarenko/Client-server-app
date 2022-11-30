package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.PrintAscendingCommand;

public class PrintAscendingCommandProd implements StandardCommandProducer{

    @Override
    public Command createCommand(UserData userData) {
        return new PrintAscendingCommand(userData);
    }
}
