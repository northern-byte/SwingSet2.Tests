package exceptions;

public class NoSuchConfigurationException extends RuntimeException {
    public NoSuchConfigurationException() {
    }

    public NoSuchConfigurationException(String message) {
        super(message);
    }
}
