package org.iespring1402.Baloot.controller;

import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.entities.User;
import org.iespring1402.Baloot.models.AuthToken;
import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.repositories.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("api/v1/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserDAO userDAO;


    @GetMapping(value = "")
    @ResponseBody
    public Object credentialsCheck(@RequestParam String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(User.hashPassword(password))) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password!");
        } else {
            Map<String, Object> authResponse = new HashMap();
            try {
                AuthToken authToken = new AuthToken(Baloot.SECRET_KEY, Baloot.ISSUER, user.getUsername());
                authResponse.put("token", authToken.getToken());
                authResponse.put("user", user);
                return ResponseEntity.status(HttpStatus.OK).body(authResponse);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
            }
        }
    }

}
