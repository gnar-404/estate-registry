package estate.dto;

import estate.domain.Owner;

import javax.validation.constraints.NotNull;

public class OwnerIdSpecificDto extends OwnerDto {

    @NotNull
    private Long id;

    public OwnerIdSpecificDto(Owner owner) {
        super(owner);
        this.id= owner.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
