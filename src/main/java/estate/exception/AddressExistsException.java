package estate.exception;

public class AddressExistsException extends RuntimeException{
    public AddressExistsException() {
        super(String.format("Address already exists"));
    }
}
