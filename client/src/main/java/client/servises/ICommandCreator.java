package client.servises;



import library.command.Command;
import library.model.UserData;

import java.io.InputStream;
import java.util.Queue;

public interface ICommandCreator {
    Command createCommand(InputStream inputStream, UserData userData);
    Command authorization(InputStream inputStream);
    Queue<Command> createCommandQueue(InputStream inputStream, UserData userData);

}
