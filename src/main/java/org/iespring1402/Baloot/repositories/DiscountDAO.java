package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.models.DiscountCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountDAO {

    @Autowired
    private DiscountRepository repo;

    public void save(DiscountCode discount) {
        repo.save(discount);
    }

}
