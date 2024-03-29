package library.serialization;

import java.io.*;

public class SerializationManager {

    public SerializationManager() {
    }

    public static byte[] objectSerial(Object serial) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(serial);
            oos.flush();
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }

    public static Object objectDeserial(byte[] array) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(array);
             ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }


}

