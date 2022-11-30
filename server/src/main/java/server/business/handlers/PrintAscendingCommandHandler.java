package server.business.handlers;

import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.model.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PrintAscendingCommandHandler implements ICommandHandler {

    private final CollectionManager collectionManager;
    private final UserDAO<UserData, String> usrDao;

    public PrintAscendingCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            synchronized (collectionManager) {
                return collectionManager.getOrgCollection().
                        stream().
                        sorted(Comparator.comparing(Organization::getCreationDate)).collect(Collectors.toCollection(ArrayDeque::new));
            }
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

    }
}
