package estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import estate.domain.Owner;

@Repository
public interface OwnerDao extends JpaRepository<Owner, Long> {
    Owner findByPersonalCode(String personalCode);
}
