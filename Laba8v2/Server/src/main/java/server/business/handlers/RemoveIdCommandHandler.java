package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveIdCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

public class RemoveIdCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;
    private ObjectDAO<Organization, Long> orgDao;

    public RemoveIdCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long> orgDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
    }

    @Override
    public Object processCommand(Command command) {
        UserData userData = command.getUserData();
        if (authorization(userData, usrDao) != 0) {
            RemoveIdCommand removeIdCommand = (RemoveIdCommand) command;
            Long orgId = removeIdCommand.getId();
            synchronized (collectionManager) {
                boolean result = byThisUser(orgId, userData.getLogin());
                //если объекты меньше есть
                if (result) {
                    //если удаление из базы прошло успешно, обновляем коллекцию
                    if (orgDao.delete(orgId) != 0) {
                        collectionManager.getOrgCollection().removeIf(o -> o.getId().equals(orgId));
                    }
                }
            }
            return collectionManager.getOrgCollection();
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }

    private boolean byThisUser(Long id, String login) {
        return collectionManager.getOrgCollection().stream().anyMatch(o -> o.getId().equals(id) && o.getUserLogin().equals(login));
    }
}
