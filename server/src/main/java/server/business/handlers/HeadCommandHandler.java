package server.business.handlers;

import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

public class HeadCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;

    public HeadCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
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
                        findFirst().
                        orElse(null);
            }
            return organization;
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
