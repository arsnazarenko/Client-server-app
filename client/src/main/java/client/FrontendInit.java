package client;

import client.servises.*;
import gui.ClientManager;
import gui.graphicsInterface.Menu;
import gui.graphicsInterface.controllers.Controllers;
import gui.graphicsInterface.loginForm.LogInWindow;

import java.util.Locale;

public class FrontendInit {
    private final ClientManager clientManager;
    private final Locale DEFAULT_LOCALE = new Locale("ru");
    private final String FONT = "Century Gothic";
    private final Menu menu;
    private final LogInWindow logInWindow;
    private final Controllers controllers;


    public FrontendInit(MessageService messageService, ArgumentValidateManager argumentValidator) {

        menu = new Menu(DEFAULT_LOCALE, FONT);
        logInWindow = new LogInWindow(FONT,DEFAULT_LOCALE);
        clientManager = new ClientManager(messageService, argumentValidator, new ScriptManager(new CommandCreator(
                new Reader(new CommandProduceManager(new ObjectCreator(new ObjectDataValidator()), new ArgumentValidateManager())))));
        controllers = new Controllers(logInWindow,clientManager,menu,DEFAULT_LOCALE);
        controllers.setLogListeners();
        controllers.setMenuListeners();
        controllers.setWindowListener();
        logInWindow.setJMenuBar(menu);
        logInWindow.setVisible(true);
    }

    public synchronized Controllers getControllers() {
        return controllers;
    }
}
