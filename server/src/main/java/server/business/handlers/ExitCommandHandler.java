package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import server.business.dao.UserDAO;

public class ExitCommandHandler implements ICommandHandler {
    private final UserDAO<UserData, String> usrDao;

    public ExitCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override

    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            return "Завершение";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
