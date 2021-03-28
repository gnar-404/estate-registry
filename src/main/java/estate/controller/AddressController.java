package estate.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import estate.dto.AddressDto;
import estate.service.AddressService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/api/address", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new address", notes = "Creates a new address. Number field should be number with" +
            " max length 5 and if need one letter")
    public ResponseEntity addAddress(
            @ApiParam(value = "Address Data", required = true)
            @RequestBody @Valid AddressDto addressDto
    ) {
        addressService.addAddress(addressDto);
        return new ResponseEntity("Address created " + LocalDateTime.now(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/address", method = RequestMethod.GET)
    @ApiOperation(value = "Get all addresses", notes = "Returns all addresses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAddresses() {
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

}
