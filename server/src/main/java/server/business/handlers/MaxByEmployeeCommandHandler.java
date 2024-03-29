package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.Comparator;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            Organization organization = null;
            synchronized (collectionManager) {
                organization = collectionManager.getOrgCollection().
                        stream().
                        max(Comparator.comparing(Organization::getCreationDate)).
                        orElse(null);
            }
            return organization;
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
