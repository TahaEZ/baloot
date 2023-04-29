package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.Provider;
import org.iespring1402.Baloot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
@CrossOrigin
public class providerController {
    private Baloot balootInstance = new Baloot();

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

}
