package server.business;


import library.command.Command;

public interface IHandlersController {
    Object handle(Command command);
}
