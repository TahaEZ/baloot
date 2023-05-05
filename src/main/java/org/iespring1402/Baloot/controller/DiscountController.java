package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discounts")
@CrossOrigin
public class DiscountController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping(value = "")
    @ResponseBody
    public Object validityCheck(
        @RequestParam String code,
        @RequestParam String username) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } 
        if (balootInstance.discountCodeValidityCheck(code)){
            if(user.isDiscountCodeUsed(username))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount used before!");
            }
            else
            {
                
                return balootInstance.findDiscountCodeByCode(code);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid discount code not found!");
        }
    }


}