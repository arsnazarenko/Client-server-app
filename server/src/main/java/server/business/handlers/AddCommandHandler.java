package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.command.AddCommand;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.Date;

public class AddCommandHandler implements ICommandHandler {
    private final CollectionManager collectionManager;
    private final ObjectDAO<Organization, Long> orgDao;
    private final UserDAO<UserData, String> usrDao;

    public AddCommandHandler(CollectionManager collectionManager, ObjectDAO<Organization, Long> orgDao, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.orgDao = orgDao;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        AddCommand addCommand = (AddCommand) command;
        UserData userData = command.getUserData();
        Long userId = authorization(command.getUserData(), usrDao);
        if (userId != 0L) {
            Organization organization = addCommand.getOrganization();
            organization.setUserLogin(userData.getLogin());
            organization.setCreationDate(new Date());
            Long objectId = orgDao.create(organization, userId);
            if (objectId != 0) {
                organization.setId(objectId);
                synchronized (collectionManager) {
                    collectionManager.getOrgCollection().addLast(organization);
                }
            }
            return collectionManager.getOrgCollection();
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
