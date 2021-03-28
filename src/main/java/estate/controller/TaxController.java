package estate.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import estate.dto.TaxRateDto;
import estate.service.TaxService;

import javax.validation.Valid;


@RestController
public class TaxController {

    @Autowired
    private TaxService taxService;


    @RequestMapping(value = "/api/taxes/calculate/{owner_personal_code}", method = RequestMethod.GET)
    @ApiOperation(value = "Calculate real estate tax for particular owner",
            notes = "Calculates the total annual real estate tax for all properties owned by a particular owner")
    public ResponseEntity calculateTaxForPerson(
            @ApiParam(value = "Personal code", required = true)
            @PathVariable final String owner_personal_code
    ) {
        return new ResponseEntity(taxService.calculateTaxForPerson(owner_personal_code),HttpStatus.OK);
    }


    @RequestMapping(value = "/api/taxes", method = RequestMethod.POST)
    @ApiOperation(value = "Add tax rate", notes = "Add new tax rate for APARTMENT, HOUSE, INDUSTRIAL or OTHER property type")
    public ResponseEntity addTaxRate(@ApiParam(value = "Tax rate Data", required = true) @RequestBody @Valid TaxRateDto taxRateDto) {
        taxService.addTaxRate(taxRateDto);
        return new ResponseEntity("Tax rate created ", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/taxes", method = RequestMethod.GET)
    @ApiOperation(value = "Get all tax rates", notes = "Returns all tax rates")
    public ResponseEntity<?> getAllTaxRates() {
        return new ResponseEntity<>(taxService.getAllTaxRates(), HttpStatus.OK);
    }
}
