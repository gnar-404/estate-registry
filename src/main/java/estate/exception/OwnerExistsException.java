package estate.exception;

public class OwnerExistsException extends RuntimeException {
    public OwnerExistsException() {
        super(String.format("Owner already exists"));
    }
}
