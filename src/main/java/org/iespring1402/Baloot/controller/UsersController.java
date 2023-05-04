package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.Commodity;
import org.iespring1402.Baloot.models.User;
import org.iespring1402.Baloot.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
    private Baloot balootInstance = Baloot.getInstance();

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

    @GetMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object getBuyListByUsername(@PathVariable("username") String username) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            return user.getBuyList();
        }
    }

    @PostMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object addBuyListItemByUsername(@PathVariable("username") String username, @RequestParam int commodityId,int quantity) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = balootInstance.findCommodityById(commodityId);
            if(commodity == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            }
            else if (commodity.getInStock() == 0)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient commodity quantity!");
            }
            else{
                Response response = user.addToBuyList(commodityId);
                if (response.success) {
                    
                    return user.getBuyList();
                }
                else{
                   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal Error!");
                }
            }
        }
    }

    @DeleteMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object deleteBuyListItemByUsername(@PathVariable("username") String username, @RequestParam int commodityId,
            int quantity) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = balootInstance.findCommodityById(commodityId);
            if (commodity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            } 
            else {
                Response response = user.removeFromBuyList(commodityId);
                if (response.success) {
                    return user.getBuyList();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal Errors!");
                }
            }
        }
    }

}
