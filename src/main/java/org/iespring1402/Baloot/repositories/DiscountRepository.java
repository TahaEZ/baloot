package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountCode, String> {

}
