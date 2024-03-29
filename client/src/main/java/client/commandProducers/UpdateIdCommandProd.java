package client.commandProducers;

import client.servises.IObjectCreator;
import client.servises.ArgumentValidateManager;
import library.command.Command;
import library.model.UserData;
import library.command.UpdateIdCommand;

import java.util.Scanner;

public class UpdateIdCommandProd implements ScanProperties, ArgumentProperties {
    private final ArgumentValidateManager argumentValidateManager;
    private final IObjectCreator objectCreator;
    private Long id = null;
    private Scanner scanner;

    public UpdateIdCommandProd(ArgumentValidateManager argumentValidateManager, IObjectCreator objectCreator) {
        this.argumentValidateManager = argumentValidateManager;
        this.objectCreator = objectCreator;
    }

    @Override
    public void setArgument(String parameter) {
        this.id = argumentValidateManager.idValid(parameter);
    }

    @Override
    public void setReaderForCreate(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Command createCommand(UserData userData) {
        if(!(scanner == null) && !(id == null)) {
            return new UpdateIdCommand(objectCreator.create(scanner), id, userData);
        } else {
            return null;
        }
    }
}
