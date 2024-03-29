package server.business.handlers;

import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.dao.UserDAO;

public class RegCommandHandler implements ICommandHandler {
    private final UserDAO<UserData, String> usrDao;
    private static final Logger logger = LogManager.getLogger(RegCommandHandler.class.getName());

    public RegCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        //если пользоватеть с таким логином найден в базе, пишем что логин занят;
        //иначе регистрируем пользователя
        UserData userData = command.getUserData();
        //если налл, то такого пользователя нет, регистрируемся
        if (usrDao.read(userData.getLogin()).getUserId() == 0L) {
            usrDao.create(userData);
            return SpecialSignals.REG_TRUE;
        } else {
            return SpecialSignals.REG_FALSE;
        }
    }
}
