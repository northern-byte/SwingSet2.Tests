package exceptions;

public class FailedToLoadPropertiesException extends RuntimeException{
    public FailedToLoadPropertiesException() {
    }

    public FailedToLoadPropertiesException(String message) {
        super(message);
    }
}
