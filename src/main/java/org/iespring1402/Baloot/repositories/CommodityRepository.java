package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Commodity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Long> {

}
