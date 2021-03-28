package estate.dto;

import estate.domain.Building;
import estate.domain.PropertyTypeEnum;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BuildingDto {

    @NotNull
    private Double size;

    @NotNull
    private BigDecimal marketValue;

    @NotNull
    private PropertyTypeEnum propertyType;

    public BuildingDto() {
    }

    public BuildingDto(Building building) {
        this.size = building.getSize();
        this.propertyType = building.getPropertyType();
        this.marketValue = building.getMarketValue();
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
    }

}
