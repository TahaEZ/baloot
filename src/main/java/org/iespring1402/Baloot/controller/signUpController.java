package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signUp")
@CrossOrigin
public class signUpController {
    private Baloot balootInstance = new Baloot();

    @PostMapping(value = "")
    @ResponseBody
    public Object signupForm(@RequestParam String username, String password, String email, String birthDate, String address, long credit) {
        if(balootInstance.findUserByUsername(username) == null || password != null)
        {
            if (credit != 0) {
                credit = 0;               
            }
            User newUser = new User(username, password, email, birthDate, address, credit);
            balootInstance.addUser(newUser);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate user or password field is empty!");
        }
    }

}
