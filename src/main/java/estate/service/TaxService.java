package estate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import estate.dao.TaxRateDao;
import estate.domain.*;
import estate.dto.TaxDto;
import estate.dto.TaxRateDto;
import estate.exception.NoDataFoundException;
import estate.exception.TaxRateExistsException;
import estate.exception.TaxRateNotExistsException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxService {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private TaxRateDao taxRateDao;


    /**
     * Calculates the total yearly real estate tax for particular owner
     * by his personal code.
     *
     * @param personalCode unique String type attribute with length validation
     * @return rounded sum of total estate
     */
    public TaxDto calculateTaxForPerson(String personalCode) {

        Owner owner = ownerService.findByPersonalCode(personalCode);
        List<Building> buildingList = buildingService.findAllByOwner(owner);

        return new TaxDto(calculateTotalSumOfTaxesForBuildings(buildingList));

    }

    /**
     * Calculates total sum of real estate tax by iterating through list of
     * the buildings.
     *
     * @param buildings list of buildings.
     * @return rounded sum of total real estate
     * @throws NoDataFoundException occurs if building list is empty.
     */
    public BigDecimal calculateTotalSumOfTaxesForBuildings(List<Building> buildings) {

        BigDecimal totalTax = new BigDecimal(0).setScale(2, RoundingMode.FLOOR);

        if (buildings != null) {
            for (Building building : buildings) {
                BigDecimal buildingTax = calculateTaxForBuildingByItsPropertyType(building.getMarketValue(), building.getPropertyType());
                totalTax = totalTax.add(buildingTax);
            }
        } else {
            throw new NoDataFoundException();
        }

        return totalTax;
    }

    /**
     * Calculates real estate tax for building depending on its type.
     * The calculation is made by multiplying the market value of building and tax rate.
     *
     * @param propertyType property type of the building
     * @param marketValue  market value of the building
     * @return rounded sum of real estate tax
     */
    public BigDecimal calculateTaxForBuildingByItsPropertyType(BigDecimal marketValue, PropertyTypeEnum propertyType) {

        BigDecimal realEstateTax = marketValue.multiply(getTaxRateByPropertyType(propertyType).getTaxRateValue()
                .multiply(BigDecimal.valueOf(0.01)))
                .setScale(2, RoundingMode.FLOOR);

        return realEstateTax;
    }

    @Transactional
    public TaxRate getTaxRateByPropertyType(PropertyTypeEnum propertyType) {

        TaxRate taxRate = taxRateDao.findTaxRateByPropertyType(propertyType);

        if (taxRate == null) {
            throw new TaxRateNotExistsException();
        } else {
            return taxRate;
        }
    }

    @Transactional
    public void addTaxRate(@Valid TaxRateDto taxRateDto) {

        TaxRate taxRate = taxRateDao.findTaxRateByPropertyType(taxRateDto.getPropertyType());

        if (taxRate == null) {

            taxRate = new TaxRate();
            taxRate.setPropertyType(taxRateDto.getPropertyType());
            taxRate.setTaxRateValue(taxRateDto.getTaxRateValue());

            taxRateDao.save(taxRate);

        } else {
            throw new TaxRateExistsException();
        }
    }

    @Transactional(readOnly = true)
    public List<TaxRateDto> getAllTaxRates() {

        List<TaxRate> taxRates = taxRateDao.findAll();

        if (taxRates.isEmpty()) {
            throw new NoDataFoundException();
        }

        return taxRates.stream().map(TaxRateDto::new).collect(Collectors.toList());
    }


}
