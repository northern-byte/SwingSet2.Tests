package exceptions;

public class FixtureNotInitializedException extends RuntimeException{
    public FixtureNotInitializedException() {
    }

    public FixtureNotInitializedException(String message) {
        super(message);
    }
}
