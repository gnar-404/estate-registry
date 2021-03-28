package estate.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column
    private Double size;

    @Column
    private BigDecimal marketValue;

    @Column
    @Enumerated(EnumType.STRING)
    private PropertyTypeEnum propertyType;


    public Building() {
    }

    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
