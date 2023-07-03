package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/logout")
@CrossOrigin
public class LogoutController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping(value = "", params = "username")
    @ResponseBody
    public Object logout(@PathParam("username") String username, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}