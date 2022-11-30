package client.commandProducers;

import library.command.Command;
import library.model.UserData;
import library.command.ExecuteScriptCommand;

public class ExecuteScriptCommandProd implements StandardCommandProducer, ArgumentProperties {
    private String script = null;
    //тут проверка тсроки не нужна, проверяеся при запуске
    @Override
    public Command createCommand(UserData userData) {
        return new ExecuteScriptCommand(script, userData);
    }

    @Override
    public void setArgument(String parameter) {
        this.script = parameter;
    }
}
