package estate.exception;

public class TaxRateNotExistsException extends RuntimeException{
    public TaxRateNotExistsException() {
        super(String.format("Tax rate not exists"));
    }
}
