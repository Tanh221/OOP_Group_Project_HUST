package exception;

public class DatabaseNotAvailableException extends Exception {
    public DatabaseNotAvailableException(String message) {
        super(message);
    }
}
