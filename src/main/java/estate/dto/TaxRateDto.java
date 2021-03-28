package estate.dto;

import estate.domain.PropertyTypeEnum;
import estate.domain.TaxRate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TaxRateDto {


    @NotNull
    private PropertyTypeEnum propertyType;

    @NotNull
    private BigDecimal taxRateValue;

    public TaxRateDto() {
    }

    public TaxRateDto(@NotNull TaxRate taxRate) {
        this.propertyType = taxRate.getPropertyType();
        this.taxRateValue = taxRate.getTaxRateValue();
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
    }

    public BigDecimal getTaxRateValue() {
        return taxRateValue;
    }

    public void setTaxRateValue(BigDecimal taxRateValue) {
        this.taxRateValue = taxRateValue;
    }
}
