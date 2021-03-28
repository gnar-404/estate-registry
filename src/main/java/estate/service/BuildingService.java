package estate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import estate.dao.BuildingDao;
import estate.domain.Address;
import estate.domain.Building;
import estate.domain.Owner;
import estate.dto.BuildingDto;
import estate.dto.BuildingIdSpecificDto;
import estate.exception.BuildingExistsException;
import estate.exception.BuildingNotFoundException;
import estate.exception.NoDataFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private AddressService addressService;


    /**
     * Adds a building to the database if address
     * and a person exists.
     *
     * @param addressId   id of the address in the database
     * @param ownerId     id of owner from database
     * @param buildingDto data transfer object
     */
    @Transactional
    public void addBuilding(@Valid BuildingDto buildingDto, Long ownerId, Long addressId) {

        Owner owner = ownerService.findById(ownerId);

        Address address = addressService.findById(addressId);

        List<Building> buildings = buildingDao.findBuildingsByAddress(address);

        Boolean buildingExists = buildings.stream()
                .filter(building -> building.getPropertyType().equals(buildingDto.getPropertyType()))
                .collect(Collectors.toList()).size()>0;

        if(buildingExists) {
            String stringifiedAddress = address.getCity() +" "+address.getStreet()+" "+address.getNumber();
            throw new BuildingExistsException(stringifiedAddress, buildingDto.getPropertyType().toString());
        }

        Building building = new Building();
        building.setAddress(address);
        building.setOwner(owner);
        building.setSize(buildingDto.getSize());
        building.setMarketValue(buildingDto.getMarketValue());
        building.setPropertyType(buildingDto.getPropertyType());

        buildingDao.save(building);
    }

    @Transactional(readOnly = true)
    public BuildingIdSpecificDto getBuilding(Long buildingId) {

        Building building = buildingDao.findById(buildingId)
                .orElseThrow(() -> new BuildingNotFoundException(buildingId));

        return new BuildingIdSpecificDto(building);
    }

    @Transactional(readOnly = true)
    public List<BuildingIdSpecificDto> getAllBuildings() {

        List<Building> buildings = buildingDao.findAll();

        if (buildings.isEmpty()) {
            throw new NoDataFoundException();
        }

        List<BuildingIdSpecificDto> buildingList = buildings.stream().map(item -> new BuildingIdSpecificDto(item)).collect(Collectors.toList());
        return buildingList;
    }

    @Transactional
    public void updateBuilding(Long buildingId, Long ownerId, Long addressId, BuildingDto buildingDto) {

        Owner owner = ownerService.findById(ownerId);

        Address address = addressService.findById(addressId);

        Building building = buildingDao.findById(buildingId)
                .orElseThrow(() -> new BuildingNotFoundException(addressId));

        building.setMarketValue(buildingDto.getMarketValue());
        building.setPropertyType(buildingDto.getPropertyType());
        building.setSize(buildingDto.getSize());
        building.setOwner(owner);
        building.setAddress(address);

        buildingDao.save(building);
    }

    @Transactional(readOnly = true)
    public List<Building> findAllByOwner(Owner owner) {
        return buildingDao.findAllByOwner(owner);
    }


}
