package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.HelpCommand;

public class HelpCommandProd  implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new HelpCommand(userData);
    }
}
