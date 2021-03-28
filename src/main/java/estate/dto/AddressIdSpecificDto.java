package estate.dto;

import estate.domain.Address;

import javax.validation.constraints.NotNull;

public class AddressIdSpecificDto extends AddressDto{

    @NotNull
    private Long id;

    public AddressIdSpecificDto(Address address) {
        super(address);
        this.id = address.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
