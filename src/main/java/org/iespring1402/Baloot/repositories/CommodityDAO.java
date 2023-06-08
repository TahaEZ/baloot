package org.iespring1402.Baloot.repositories;

import java.util.List;
import java.util.Optional;

import org.iespring1402.Baloot.entities.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityDAO {
    @Autowired
    private CommodityRepository repo;

    public void save(Commodity commodity) {
        repo.save(commodity);
    }
    public List <Commodity> findByCategory(String category) {
        return repo.findByCategories(category);
        
    }
}
