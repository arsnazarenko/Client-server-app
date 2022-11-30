package client.commandProducers;

import client.servises.ArgumentValidateManager;
import library.command.Command;
import library.model.UserData;
import library.command.FilterContainsNameCommand;

public class FilterContainsNameProd implements StandardCommandProducer, ArgumentProperties{
    private String name = null;
    private final ArgumentValidateManager argumentValidateManager;

    public FilterContainsNameProd(ArgumentValidateManager argumentValidateManager) {
        this.argumentValidateManager = argumentValidateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.name = argumentValidateManager.subStringIsValid(parameter);
    }

    @Override
    public Command createCommand(UserData userData) {
        return name!= null?new FilterContainsNameCommand(name, userData):null;
    }
}
