package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Commodity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommodityRepository extends CrudRepository<Commodity, Long> {
@Query("SELECT c FROM Commodity c WHERE ?1 MEMBER OF c.categories")
List<Commodity> findByCategories(String categories);

@Query("select c from Commodity c where ?1 member of c.categories and c.inStock > 0")
List<Commodity> findAvailablesByCategories(@Param("categories")String categories);

Commodity findById(int id);
@Override
default List<Commodity> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
}


@Query("SELECT c FROM Commodity c WHERE c.inStock > 0")
List<Commodity> availableCommodities();

List<Commodity> findByName(String name);

@Query("SELECT c  FROM Commodity c WHERE c.name = :name AND c.inStock > 0")
List<Commodity> findByNameIfAvailable(@Param("name") String name);

@Query("SELECT c  FROM Commodity c JOIN Provider p ON c.providerId = p.id WHERE p.name = :providerName")
List<Commodity> findByProviderName(@Param("providerName")String ProviderName);

@Query("SELECT c  FROM Commodity c JOIN Provider p ON c.providerId = p.id WHERE p.name = :providerName AND c.inStock > 0")
List<Commodity> findAvailablesByProviderName(@Param("providerName") String ProviderName);

}
