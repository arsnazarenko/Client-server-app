package client.commandProducers;

import library.command.Command;
import library.model.UserData;

public interface StandardCommandProducer {
    Command createCommand(UserData userData);
}
