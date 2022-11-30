package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.ClearCommand;

public class ClearCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new ClearCommand(userData);
    }
}
