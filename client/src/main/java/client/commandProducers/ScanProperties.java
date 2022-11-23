package client.commandProducers;

import java.util.Scanner;
/**
 * Интерфес команды, которая для создания использует различные потоки вводы
 */
public interface ScanProperties extends StandardCommandProducer{
    /**
     *
     * @param scanner - способ чтения входных данных
     * по умолчанию будет читать данные с помощью потока с клавиатуры
     */
    void setReaderForCreate(Scanner scanner);

}
