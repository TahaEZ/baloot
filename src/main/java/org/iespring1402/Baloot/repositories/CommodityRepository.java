package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Integer> {

}
