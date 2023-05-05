package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/providers")
@CrossOrigin
public class ProvidersController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping(value = "/{providerId}")
    @ResponseBody
    public Object getProviderById(@PathVariable("providerId") int providerId) {
        Provider provider = balootInstance.findProviderByProviderId(providerId);
        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("provider not found!");
        } else {
            return provider;
        }
    }

    @PostMapping(value = "")
    @ResponseBody
    public Object addProvider(@RequestBody Provider provider) {

        if (balootInstance.findProviderByProviderId(provider.getId()).getId() == provider.getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate was occurred!");
        }

        else {
            balootInstance.addProvider(provider);
            return provider;
        }

    }

}
