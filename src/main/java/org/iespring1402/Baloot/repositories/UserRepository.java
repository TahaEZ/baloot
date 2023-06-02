package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
