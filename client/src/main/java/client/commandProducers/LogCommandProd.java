package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.LogCommand;

public class LogCommandProd implements StandardCommandProducer{
    @Override
    public Command createCommand(UserData userData) {
        return new LogCommand(userData);
    }
}
