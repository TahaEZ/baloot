package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.iespring1402.Baloot.entities.DiscountCode;


@Repository
public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByUsernameContainingUsedDiscounts(String username,String code);
}
