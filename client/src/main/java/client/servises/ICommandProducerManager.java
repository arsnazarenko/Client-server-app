package client.servises;



import client.commandData.CommandData;
import library.command.Command;

import java.util.Scanner;

public interface ICommandProducerManager {
    Command buildCommand(CommandData commandData, Scanner scanner);
}
