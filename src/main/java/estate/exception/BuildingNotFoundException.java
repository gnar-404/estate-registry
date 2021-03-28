package estate.exception;

public class BuildingNotFoundException extends RuntimeException{
    public BuildingNotFoundException(Long id) {
        super(String.format("Building with id %d not found", id));
    }
}
