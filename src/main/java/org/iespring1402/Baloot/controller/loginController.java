package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.model.Baloot;
import org.iespring1402.Baloot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class loginController{
    
    private Baloot balootInstance = new Baloot();

    @GetMapping(value = "")
    @ResponseBody
    public Object credentialsCheck(@RequestParam String username, String password) throws Exception {
        User user = balootInstance.findUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            ObjectMapper mapper = new ObjectMapper();
            String body;
            body=mapper.writeValueAsString("Invalid username or password!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

}
