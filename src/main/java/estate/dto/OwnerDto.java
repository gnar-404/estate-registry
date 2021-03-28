package estate.dto;


import estate.domain.Owner;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class OwnerDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[0-9]{11}$")
    private String personalCode;

    public OwnerDto() {
    }

    public OwnerDto(Owner owner) {
        this.firstName = owner.getFirstName();
        this.lastName = owner.getLastName();
        this.personalCode = owner.getPersonalCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }
}
