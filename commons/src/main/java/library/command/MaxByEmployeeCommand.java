package library.command;


import library.model.UserData;

public class MaxByEmployeeCommand extends Command {
    public MaxByEmployeeCommand(UserData userData) {
        super(userData);
    }
}
