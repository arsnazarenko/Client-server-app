package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.MaxByEmployeeCommand;

public class MaxByEmployeesCommandProd implements StandardCommandProducer{
    @Override
    public Command createCommand(UserData userData) {
        return new MaxByEmployeeCommand(userData);
    }
}
