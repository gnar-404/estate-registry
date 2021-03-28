package estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import estate.domain.PropertyTypeEnum;
import estate.domain.TaxRate;

public interface TaxRateDao extends JpaRepository<TaxRate, Long> {
    TaxRate findTaxRateByPropertyType(PropertyTypeEnum propertyTypeEnum);
}
