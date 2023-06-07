package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.models.DiscountCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscountRepository extends CrudRepository<DiscountCode, Long> {

}
