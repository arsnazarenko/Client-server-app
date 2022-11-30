package client.commandProducers;

import client.servises.IObjectCreator;
import library.command.Command;
import library.model.UserData;
import library.command.RemoveLowerCommand;

import java.util.Scanner;

public class RemoveLowerCommandProd implements StandardCommandProducer, ScanProperties {
    private final IObjectCreator objectCreator;
    private Scanner scanner = null;

    public RemoveLowerCommandProd(IObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }

    @Override
    public void setReaderForCreate(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Command createCommand(UserData userData) {
        if (scanner != null) {
            return new RemoveLowerCommand(objectCreator.create(scanner), userData);
        } else {
            return null;
        }
    }
}
