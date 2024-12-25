package model.exception;

public class DatabaseNotAvailableException extends Exception {
    public DatabaseNotAvailableException(String message) {
        super(message);
    }
}
