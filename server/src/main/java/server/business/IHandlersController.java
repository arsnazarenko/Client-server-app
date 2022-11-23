package server.business;


import library.clientCommands.Command;

public interface IHandlersController {
    Object handle(Command command);
}
