package estate.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long id) {
        super(String.format("Owner with id %d not found", id));
    }

    public OwnerNotFoundException(String personalCode) {
        super(String.format("Owner with personal code %s not found", personalCode));
    }
}
