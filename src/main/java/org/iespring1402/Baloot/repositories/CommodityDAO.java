package org.iespring1402.Baloot.repositories;

import java.util.List;

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
    public List <Commodity> findByCategory(String category,boolean available) {
        if (available == true) {
            return repo.findAvailablesByCategories(category);
        }
        return repo.findByCategories(category);

        
    }
    public Commodity findCommodityById(int id)
    {
        return repo.findById(id);
    }

    public List<Commodity> getAllCommodities()
    {
     return repo.findAll();   
    }

    public List<Commodity> getAvailableCommodities()
    {
        return repo.availableCommodities();
    }

    public List<Commodity> getCommoditiesByProviderName(String providerName , boolean available)
    {
        if(available == true){
            return repo.findAvailablesByProviderName(providerName);
        }
        return repo.findByProviderName(providerName);
    }

    public List<Commodity> findCommodityByName(String name ,boolean available){
        if (available == true) {
            return repo.findByNameIfAvailable(name);
        }
        return repo.findByName(name);
    }
}
