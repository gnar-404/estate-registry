package estate.dto;

import estate.domain.Address;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddressDto {

    @NotNull
    private String street;

    @NotNull
    @Pattern(regexp = "^[0-9]{0,5}[a-z]{0,1}$")
    private String number;

    @NotNull
    private String city;

    public AddressDto() {
    }

    public AddressDto(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.city = address.getCity();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
