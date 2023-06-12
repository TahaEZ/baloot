package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.entities.Provider;
import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.repositories.ProviderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/providers")
@CrossOrigin
public class ProvidersController {
    @Autowired
    ProviderDAO providerDAO;

    @GetMapping(value = "/{providerId}")
    @ResponseBody
    public Object getProviderById(@PathVariable("providerId") int providerId, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        Provider provider = providerDAO.findById(providerId);

        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("provider not found!");
        } else {
            return provider;
        }
    }

    @PostMapping(value = "")
    @ResponseBody
    public Object addProvider(@RequestBody Provider provider, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        if (providerDAO.checkIfExist(provider.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        }

        else {
            providerDAO.save(provider);
            return provider;
        }

    }

}
