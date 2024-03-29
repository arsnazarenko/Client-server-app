package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import server.business.CollectionManager;
import server.business.dao.UserDAO;


public class ShowCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;

    public ShowCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            synchronized (collectionManager) {
                return collectionManager.getOrgCollection();
            }
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
