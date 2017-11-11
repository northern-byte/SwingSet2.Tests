package exceptions;

public class NoSuchPropertyException extends RuntimeException{
    public NoSuchPropertyException() {
    }

    public NoSuchPropertyException(String message) {
        super(message);
    }
}
