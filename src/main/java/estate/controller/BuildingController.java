package estate.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import estate.dto.BuildingDto;
import estate.service.BuildingService;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
public class BuildingController {

    @Autowired
    private BuildingService buildingService;


    @RequestMapping(value = "/api/buildings/{owner_id}/{address_id}", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new building", notes = "Creates a new building")
    public ResponseEntity addBuilding(
            @PathVariable final Long owner_id,
            @PathVariable final Long address_id,
            @RequestBody @Valid BuildingDto buildingDto
    ) {
        buildingService.addBuilding(buildingDto, owner_id, address_id);
        return new ResponseEntity("Building created " + LocalDateTime.now(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/buildings/{building_id}/{owner_id}/{address_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates a single building", notes = "Updates a single building by id")
    public ResponseEntity updateBuilding(
            @ApiParam(value = "", required = true)
            @PathVariable Long building_id,
            @PathVariable Long owner_id,
            @PathVariable Long address_id,
            @RequestBody BuildingDto buildingDto

    ) {
        buildingService.updateBuilding(building_id, owner_id, address_id, buildingDto);
        return new ResponseEntity("Building updated " + LocalDateTime.now(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/buildings/{building_id}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns a single building", notes = "Returns a single building by id")
    public ResponseEntity<?> getBuilding(@PathVariable final Long building_id) {
        return new ResponseEntity<>(buildingService.getBuilding(building_id), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/buildings", method = RequestMethod.GET)
    @ApiOperation(value = "Returns a list of all buildings", notes = "Returns all buildings")
    public ResponseEntity<?> getBuildings() {
        return new ResponseEntity<>(buildingService.getAllBuildings(), HttpStatus.OK);
    }

}
