package server.business.handlers;


import library.command.Command;
import library.utils.InfoCollection;
import library.utils.SpecialSignals;
import library.model.UserData;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.Date;

public class InfoCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;

    public InfoCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            synchronized (collectionManager) {
                Class<?> type = collectionManager.getOrgCollection().getClass();
                int count = collectionManager.getOrgCollection().size();
                Date date = collectionManager.getCreationCollectionDate();
                return new InfoCollection(type, count, date);
            }
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
        //информация о колллекции


    }
}
