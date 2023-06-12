package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.DiscountCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountDAO {

    @Autowired
    private DiscountRepository repo;

    public void save(DiscountCode discount) {
        repo.save(discount);
    }

    public boolean isValid(String code) {
        return repo.existsByDiscountCode(code);
    }

    public DiscountCode findDiscountCodeByCode(String code) {
        return repo.findByCode(code);
    }
}
