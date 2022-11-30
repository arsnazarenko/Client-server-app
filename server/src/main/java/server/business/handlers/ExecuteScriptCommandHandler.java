package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import server.business.dao.UserDAO;

public class ExecuteScriptCommandHandler implements ICommandHandler {
    private final UserDAO<UserData, String> usrDao;

    public ExecuteScriptCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        //можно не кастовать, просто отправляем сообщение о том, что сервер получил команду запуска скрипта
        if (authorization(command.getUserData(), usrDao) != 0L) {
            return "Скрипт...";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
