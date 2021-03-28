package estate.dto;

import estate.domain.Building;

import javax.validation.constraints.NotNull;

public class BuildingIdSpecificDto extends BuildingDto {

    @NotNull
    private Long id;

    public BuildingIdSpecificDto(Building building) {
        super(building);
        this.id = building.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
