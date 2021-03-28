package estate.exception;

public class DataExistsException extends RuntimeException {
    public DataExistsException() {
        super("Data already exists");
    }
}
