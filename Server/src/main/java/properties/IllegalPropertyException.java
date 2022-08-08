package properties;

public class IllegalPropertyException extends IllegalArgumentException {
    public IllegalPropertyException(String propKey, String propValue) {
        super("Value of property \"" + propKey + "\" is invalid: \"" + propValue + "\"");
    }
}
