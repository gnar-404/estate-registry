package estate.integrationTests;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import estate.controller.AddressController;
import estate.controller.BuildingController;
import estate.controller.OwnerController;
import estate.dao.AddressDao;
import estate.dao.BuildingDao;
import estate.domain.*;
import estate.dto.AddressDto;
import estate.dto.BuildingDto;
import estate.dto.OwnerDto;
import estate.service.BuildingService;
import estate.service.OwnerService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DisplayName("When running Building controller")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BuildingEndpointTest {

    @Autowired
    private AddressController addressController;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerController ownerController;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingController buildingController;

    @BeforeEach
    void init(){
        Owner owner = new Owner();
        owner.setPersonalCode("49041725847");
        owner.setFirstName("Jane");
        owner.setLastName("Hill");
        ownerController.addOwner(new OwnerDto(owner));

        Address address = new Address();
        address.setCity("VILNIUS");
        address.setStreet("River street");
        address.setNumber("48");
        addressController.addAddress(new AddressDto(address));
    }

    @Test
    @DisplayName("add building")
    @Transactional
    void testCreateBuildingFromController() {
        Owner owner = ownerService.findByPersonalCode("49041725847");
        Address address = addressDao.findAll().get(0);

        Building building = new Building();
        building.setAddress(address);
        building.setOwner(owner);
        building.setSize(50.55);
        building.setPropertyType(PropertyTypeEnum.APARTMENT);
        building.setMarketValue(BigDecimal.valueOf(60_000));

        buildingController.addBuilding(owner.getId(), address.getId(), new BuildingDto(building));

        assertEquals(1, buildingService.getAllBuildings().size(), "should add building");
    }

    @Test
    @DisplayName("update building")
    @Transactional
    void testUpdateBuildingFromController() {
        Owner owner = ownerService.findByPersonalCode("49041725847");
        Address address = addressDao.findAll().get(0);

        Building buildingToBeUpdated = new Building();
        buildingToBeUpdated.setAddress(address);
        buildingToBeUpdated.setOwner(owner);
        buildingToBeUpdated.setSize(50.55);
        buildingToBeUpdated.setPropertyType(PropertyTypeEnum.APARTMENT);
        buildingToBeUpdated.setMarketValue(BigDecimal.valueOf(60_000));

        buildingController.addBuilding(owner.getId(), address.getId(), new BuildingDto(buildingToBeUpdated));

        Building updatedBuilding = new Building();
        updatedBuilding.setAddress(address);
        updatedBuilding.setOwner(owner);
        updatedBuilding.setSize(60.00);
        updatedBuilding.setPropertyType(PropertyTypeEnum.APARTMENT);
        updatedBuilding.setMarketValue(BigDecimal.valueOf(70_000));

        List<Building> buildings = buildingService.findAllByOwner(owner);

        buildingController.updateBuilding(buildings.get(0).getId(),owner.getId(), address.getId(), new BuildingDto(updatedBuilding));

        assertEquals(60.00, buildingService.getBuilding(buildings.get(0).getId()).getSize(), "Building size should be updated");
        assertEquals(BigDecimal.valueOf(70_000), buildingService.getBuilding(buildings.get(0).getId()).getMarketValue(), "Building market value should be updated");
    }

    @Test
    @DisplayName("Get buildings")
    @Transactional
    void testGetAllBuildingsFromController() {
        Owner owner = ownerService.findByPersonalCode("49041725847");
        Address address = addressDao.findAll().get(0);

        Building building = new Building();
        building.setAddress(address);
        building.setOwner(owner);
        building.setSize(50.55);
        building.setPropertyType(PropertyTypeEnum.APARTMENT);
        building.setMarketValue(BigDecimal.valueOf(60_000));

        buildingService.addBuilding(new BuildingDto(building),owner.getId(),address.getId());

        ResponseEntity buildingsResponse = buildingController.getBuildings();
        List<BuildingDto> buildings = (List<BuildingDto>) buildingsResponse.getBody();

        assertEquals(PropertyTypeEnum.APARTMENT, buildings.get(0).getPropertyType());
        assertEquals(BigDecimal.valueOf(60_000), buildings.get(0).getMarketValue());
        assertEquals(50.55, buildings.get(0).getSize());
    }

    @Test
    @DisplayName("Get single building")
    @Transactional
    void testGetBuildingFromController() {
        Owner owner = ownerService.findByPersonalCode("49041725847");
        Address address = addressDao.findAll().get(0);

        Building buildingEntity = new Building();
        buildingEntity.setAddress(address);
        buildingEntity.setOwner(owner);
        buildingEntity.setSize(50.55);
        buildingEntity.setPropertyType(PropertyTypeEnum.APARTMENT);
        buildingEntity.setMarketValue(BigDecimal.valueOf(60_000));
        buildingService.addBuilding(new BuildingDto(buildingEntity),owner.getId(),address.getId());


        Long buildingID = buildingDao.findAll().get(0).getId();

        ResponseEntity buildingResponse = buildingController.getBuilding(buildingID);
        BuildingDto building = (BuildingDto) buildingResponse.getBody();

        assertEquals(PropertyTypeEnum.APARTMENT, building.getPropertyType());
        assertEquals(BigDecimal.valueOf(60_000), building.getMarketValue());
        assertEquals(50.55, building.getSize());
    }


}
