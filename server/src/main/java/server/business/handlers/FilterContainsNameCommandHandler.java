package server.business.handlers;


import library.command.Command;
import library.utils.SpecialSignals;
import library.model.UserData;
import library.command.FilterContainsNameCommand;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class FilterContainsNameCommandHandler implements ICommandHandler {
    private final UserDAO<UserData, String> usrDao;
    private final CollectionManager collectionManager;

    public FilterContainsNameCommandHandler(UserDAO<UserData, String> usrDao, CollectionManager collectionManager) {
        this.usrDao = usrDao;
        this.collectionManager = collectionManager;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {

            FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
            String subString = filterContainsNameCommand.getSubString();
            synchronized (collectionManager) {
                return collectionManager.getOrgCollection().
                        stream().
                        filter(o -> o.getName().contains(subString)).
                        collect(Collectors.toCollection(ArrayDeque::new));
            }
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

    }
}
