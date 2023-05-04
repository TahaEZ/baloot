package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@CrossOrigin
public class SignUpController {
    private Baloot balootInstance = Baloot.getInstance();

    @PostMapping(value = "")
    @ResponseBody
    public Object signupForm(@RequestBody User user) {
        if(balootInstance.findUserByUsername(user.getUsername()) == null)
        {
            if (user.getCredit() != 0) {
                user.setCredit(0);               
            }
            balootInstance.addUser(user);
            return user;
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate username!");
        }
    }

}
