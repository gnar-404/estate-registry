package estate.exception;

public class TaxRateExistsException extends RuntimeException{
    public TaxRateExistsException() {
        super(String.format("Tax rate for this property type already exists"));
    }
}
