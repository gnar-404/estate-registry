package estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import estate.domain.Address;
import estate.domain.Building;
import estate.domain.Owner;

import java.util.List;

@Repository
public interface BuildingDao extends JpaRepository<Building, Long> {
    List<Building> findAllByOwner(Owner owner);
    List<Building> findBuildingsByAddress(Address address);
}
