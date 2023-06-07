package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderDAO {
    @Autowired
    ProviderRepository repo;

    public void save(Provider provider) {
        repo.save(provider);
    }
}
