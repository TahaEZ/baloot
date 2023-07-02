package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Commodity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Long> {
List<Commodity> findByCategories(String categories);

@Query("SELECT * FROM Commodity c JOIN Category cat WHERE c.in_stock > 0 AND c.category =: category")
List<Commodity> findAvailablesByCategories(@Param("category")String categories);

Commodity findById(int id);
@Override
default List<Commodity> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
}


@Query("SELECT * FROM Commodity c WHERE c.in_stock > 0")
List<Commodity> availableCommodities();

List<Commodity> findByName(String name);

@Query("SELECT c FROM Commodity c WHERE c.name = :name AND c.in_stock > 0")
List<Commodity> findByNameIfAvailable(@Param("name") String name);

@Query("SELECT c FROM Commodity c JOIN Provider p ON c.providerId = p.id WHERE p.name = :providerName")
List<Commodity> findByProviderName(@Param("providerName")String ProviderName);

@Query("SELECT c FROM Commodity c JOIN Provider p ON c.providerId = p.id WHERE p.name = :providerName AND c.in_stock > 0")
List<Commodity> findAvailablesByProviderName(@Param("providerName") String ProviderName);

}
