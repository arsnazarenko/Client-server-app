package client.servises;



import library.model.Organization;

import java.util.Scanner;

/**
 * Интерфейс, содержащий перегруженный метод для создания объекта коллекции.
 */

public interface IObjectCreator {
    Organization create(Scanner reader);
}
