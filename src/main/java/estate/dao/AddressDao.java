package estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import estate.domain.Address;

public interface AddressDao extends JpaRepository<Address, Long> {
}
