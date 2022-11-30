package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.ExitCommand;

public class ExitCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new ExitCommand(userData);
    }
}
