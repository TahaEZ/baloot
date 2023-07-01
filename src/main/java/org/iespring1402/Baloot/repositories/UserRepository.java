package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Query("SELECT COUNT(*) > 0 FROM User u JOIN u.usedDiscounts d WHERE u.username = :username AND d.discountCode = :discountCode")
    boolean existsByUsernameAndUsedDiscountsCode(@Param("username") String username,
            @Param("discountCode") String discountCode);

    User findByUsername(String username);
}
