package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.DiscountCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface DiscountRepository extends CrudRepository<DiscountCode, Long> {
   boolean existsByDiscountCode(String code);
   DiscountCode findByCode(String code);
}
