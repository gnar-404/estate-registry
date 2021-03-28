package estate.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import estate.dto.OwnerDto;
import estate.service.OwnerService;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/api/owners", method = RequestMethod.POST)
    @ApiOperation(value = "Add owner", notes = "Creates a new owner. Personal code can not be null, its length must be 11")
    public ResponseEntity addOwner(@ApiParam(value = "Owner Data", required = true) @RequestBody @Valid OwnerDto ownerDto) {

        ownerService.addOwner(ownerDto);

        return new ResponseEntity("Owner " + ownerDto.getFirstName() + " " + ownerDto.getLastName() + " created "
                + LocalDateTime.now(), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/api/owners/id/{owner_id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get single owner by id", notes = "Returns a single owner by id")
    public ResponseEntity<?> getOwner(@PathVariable final Long owner_id) {
        return new ResponseEntity<>(ownerService.getOwner(owner_id), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/owners/{personalCode}", method = RequestMethod.GET)
    @ApiOperation(value = "Get owner by personal code", notes = "Returns a single owner by personal code")
    public ResponseEntity<?> getOwnerByPersonalCode(@PathVariable final String personalCode) {
        return new ResponseEntity<>(ownerService.getOwnerByPersonalCode(personalCode), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/owners", method = RequestMethod.GET)
    @ApiOperation(value = "Get all owners", notes = "Returns all owners")
    public ResponseEntity<?> getOwners() {
        return new ResponseEntity<>(ownerService.getAllOwners(), HttpStatus.OK);
    }
}
