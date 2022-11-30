package client.commandProducers;

import client.servises.ArgumentValidateManager;
import library.command.Command;
import library.model.UserData;
import library.command.RemoveIdCommand;

public class RemoveByIdCommandProd implements StandardCommandProducer, ArgumentProperties {
    private final ArgumentValidateManager argumentValidateManager;
    private Long id = null;

    public RemoveByIdCommandProd(ArgumentValidateManager argumentValidateManager) {
        this.argumentValidateManager = argumentValidateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.id = argumentValidateManager.idValid(parameter);
    }

    @Override
    public Command createCommand(UserData userData) {
        return (id != null)?new RemoveIdCommand(id, userData):null;
    }
}
