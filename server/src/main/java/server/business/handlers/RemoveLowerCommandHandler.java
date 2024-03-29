package server.business.handlers;

import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.command.RemoveLowerCommand;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveLowerCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;
    private final ObjectDAO<Organization, Long> orgDao;

    public RemoveLowerCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long> orgDao) {

        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
    }

    @Override
    public Object processCommand(Command command) {
        UserData userData = command.getUserData();
        if (authorization(userData, usrDao) != 0) {
            RemoveLowerCommand removeLowerCommand = (RemoveLowerCommand) command;
            Organization organization = removeLowerCommand.getOrganization();
            organization.setCreationDate(new Date());
            synchronized (collectionManager) {
                List<Long> ids = lower(organization, userData.getLogin());
                //если объекты меньше есть
                if (!ids.isEmpty()) {
                    //если удаление из базы прошло успешно, обновляем коллекцию
                    if (orgDao.deleteByKeys(ids)) {
                        collectionManager.getOrgCollection().removeIf(o -> ids.contains(o.getId()));
                    }
                }
            }
            return collectionManager.getOrgCollection();
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }


    private List<Long> lower(Organization organization, String login) {
        List<Long> orgId = collectionManager.getOrgCollection().stream().filter(o -> o.compareTo(organization) < 0 && o.getUserLogin().equals(login)).map(Organization::getId).collect(Collectors.toList());
        return orgId;
    }
}
