package org.iespring1402.Baloot.controller;

import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.models.AuthToken;
import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/v1/login")
@CrossOrigin
public class LoginController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping(value = "")
    @ResponseBody
    public Object credentialsCheck(@RequestParam String username, String password) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null || !user.getPassword().equals(User.hashPassword(password))) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password!");
        } else {
            Map<String, Object> authResponse = new HashMap();
            try {
                balootInstance.setCurrentUser(username);
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
