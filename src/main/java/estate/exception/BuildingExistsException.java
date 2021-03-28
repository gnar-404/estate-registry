package estate.exception;

public class BuildingExistsException extends RuntimeException{
    public BuildingExistsException(String address, String propertyType) {
        super(String.format("Building type: %s with address: %s already exists",  propertyType, address));
    }
}
