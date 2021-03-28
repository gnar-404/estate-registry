package estate.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class TaxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PropertyTypeEnum propertyType;

    @Column
    private BigDecimal taxRateValue;


    public TaxRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
