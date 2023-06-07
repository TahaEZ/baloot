package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Integer> {

}
