package estate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import estate.dao.OwnerDao;
import estate.domain.Owner;
import estate.dto.OwnerDto;
import estate.dto.OwnerIdSpecificDto;
import estate.exception.NoDataFoundException;
import estate.exception.OwnerExistsException;
import estate.exception.OwnerNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    @Autowired
    private OwnerDao ownerDao;

    @Transactional
    public void addOwner(@Valid OwnerDto ownerDto) {

        Owner owner = ownerDao.findByPersonalCode(ownerDto.getPersonalCode());

        if(owner != null) {
            throw new OwnerExistsException();
        }

        owner = new Owner();
        owner.setFirstName(ownerDto.getFirstName());
        owner.setLastName(ownerDto.getLastName());
        owner.setPersonalCode(ownerDto.getPersonalCode());
        ownerDao.save(owner);
    }

    @Transactional(readOnly = true)
    public OwnerDto getOwner(Long ownerId) {

        Owner owner = ownerDao.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));

        return new OwnerDto(owner);
    }

    @Transactional(readOnly = true)
    public List<OwnerIdSpecificDto> getAllOwners() {

        List<Owner> owners = ownerDao.findAll();

        if (owners.isEmpty()) {
            throw new NoDataFoundException();
        }

        List<OwnerIdSpecificDto> ownerList = owners.stream().map(item -> new OwnerIdSpecificDto(item)).collect(Collectors.toList());

        return ownerList;
    }

    @Transactional
    public OwnerIdSpecificDto getOwnerByPersonalCode(String personalCode) {

        Owner owner = ownerDao.findByPersonalCode(personalCode);

        if (owner == null) {
            throw new OwnerNotFoundException(personalCode);
        } else {
            return new OwnerIdSpecificDto(owner);
        }
    }

    @Transactional(readOnly = true)
    public Owner findById(Long ownerId) {
        return ownerDao.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    @Transactional(readOnly = true)
    public Owner findByPersonalCode(String personalCode){
        Owner owner = ownerDao.findByPersonalCode(personalCode);
        if (owner == null) {
            throw new OwnerNotFoundException(personalCode);
        }
        return owner;
    }

}
