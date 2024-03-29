package server.business.handlers;

import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.command.UpdateIdCommand;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.stream.Collectors;


public class UpdateIdCommandHandler implements ICommandHandler {
    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;
    private final ObjectDAO<Organization, Long> orgDao;

    public UpdateIdCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long> orgDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
    }

    @Override
    public Object processCommand(Command command) {
        UserData userData = command.getUserData();
        if (authorization(userData, usrDao) != 0) {
            UpdateIdCommand updateIdCommand = (UpdateIdCommand) command;
            Long id = updateIdCommand.getId();
            Organization organization = updateIdCommand.getOrganization();
            organization.setCreationDate(new Date());
            synchronized (collectionManager) {
                if (collectionManager.getOrgCollection().stream().
                        anyMatch(o -> o.getId().equals(id) && o.getUserLogin().equals(userData.getLogin()))) {
                    if (orgDao.update(id, organization)) {
                        organization.setId(id);
                        organization.setUserLogin(userData.getLogin());

                        collectionManager.setOrgCollection(collectionManager.getOrgCollection().stream().
                                map(o -> {
                                    if (o.getId().equals(id)) {
                                        return organization;
                                    } else {
                                        return o;
                                    }
                                }).
                                collect(Collectors.toCollection(ArrayDeque::new)));
                    }
                }
            }
            return collectionManager.getOrgCollection();

        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
