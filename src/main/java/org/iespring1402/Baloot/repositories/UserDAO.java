package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {
    @Autowired
    UserRepository repo;

    public void save(User user) {
        repo.save(user);
    }

    public boolean isDiscountCodeUsedByUsername(String username , String code)
    {
        return repo.existsByUsernameAndUsedDiscountsCode(username, code);
    }

    public User getUserByUsername(String username)
    {
        return repo.findByUsername(username);
    }

}
