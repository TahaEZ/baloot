package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
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
@RequestMapping("/users")
@CrossOrigin
public class usersController {
    private Baloot balootInstance = new Baloot();

    @GetMapping(value = "/{username}")
    @ResponseBody
    public Object getUserByUsername(@PathVariable("username") String username) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            return user;
        }
    }

}
