package estate.unitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import estate.dao.TaxRateDao;
import estate.domain.*;
import estate.service.BuildingService;
import estate.service.OwnerService;
import estate.service.TaxService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TaxServiceTest {

    @Spy
    @InjectMocks
    private TaxService taxService = new TaxService();

    @Mock
    private BuildingService buildingService;

    @Mock
    private TaxRateDao taxRateDao;

    @Mock
    private OwnerService ownerService;


    @Test
    public void calculateTaxForPersonTest() {

        mockTaxRate();

        Owner owner = new Owner();

        Mockito.when(ownerService.findByPersonalCode("12345678910")).thenReturn(owner);

        List<Building> buildings = new ArrayList<>();

        Building buildingApartment = new Building();
        buildingApartment.setMarketValue(BigDecimal.valueOf(10_000));
        buildingApartment.setPropertyType(PropertyTypeEnum.APARTMENT);

        Building buildingIndustrial = new Building();
        buildingIndustrial.setMarketValue(BigDecimal.valueOf(500_000));
        buildingIndustrial.setPropertyType(PropertyTypeEnum.INDUSTRIAL);

        buildings.add(buildingApartment);
        buildings.add(buildingIndustrial);

        Mockito.when(buildingService.findAllByOwner(owner)).thenReturn(buildings);

        Assertions.assertEquals(BigDecimal.valueOf(2020).setScale(2, RoundingMode.FLOOR),
                taxService.calculateTaxForPerson("12345678910").getTotalTax());
    }

    @Test
    public void calculateTaxForBuildingByItsPropertyTypeTest() {

        TaxRate taxRateApartment = new TaxRate();
        taxRateApartment.setTaxRateValue(BigDecimal.valueOf(0.2));
        Mockito.when(taxRateDao.findTaxRateByPropertyType(PropertyTypeEnum.APARTMENT)).thenReturn(taxRateApartment);

        Assertions.assertEquals(BigDecimal.valueOf(400).setScale(2, RoundingMode.FLOOR),
                taxService.calculateTaxForBuildingByItsPropertyType(BigDecimal.valueOf(200_000), PropertyTypeEnum.APARTMENT));

    }

    @Test
    public void calculateTotalSumOfTaxesForBuildingsTest() {

        mockTaxRate();

        Building apartment = new Building();
        apartment.setPropertyType(PropertyTypeEnum.APARTMENT);
        apartment.setMarketValue(BigDecimal.valueOf(50_000));

        Building industrial = new Building();
        industrial.setPropertyType(PropertyTypeEnum.INDUSTRIAL);
        industrial.setMarketValue(BigDecimal.valueOf(450_000));

        List<Building> buildings = new ArrayList<>();
        buildings.add(apartment);
        buildings.add(industrial);

        Assertions.assertEquals(BigDecimal.valueOf(1900).setScale(2, RoundingMode.FLOOR),
                taxService.calculateTotalSumOfTaxesForBuildings(buildings));
    }

    private void mockTaxRate() {

        TaxRate taxRateApartment = new TaxRate();
        taxRateApartment.setTaxRateValue(BigDecimal.valueOf(0.2));
        Mockito.when(taxRateDao.findTaxRateByPropertyType(PropertyTypeEnum.APARTMENT)).thenReturn(taxRateApartment);

        TaxRate taxRateIndustrial = new TaxRate();
        taxRateIndustrial.setTaxRateValue(BigDecimal.valueOf(0.4));
        Mockito.when(taxRateDao.findTaxRateByPropertyType(PropertyTypeEnum.INDUSTRIAL)).thenReturn(taxRateIndustrial);
    }


}
