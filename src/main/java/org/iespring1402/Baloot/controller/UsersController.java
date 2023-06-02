package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.models.User;
import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/users")
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
        HashMap<String, Object> buylist = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            buylist.put("buylist", balootInstance.getBuyList(username));
            buylist.put("totalCost",balootInstance.findUserByUsername(username).getBuyList().totalCost());
            return buylist;
        }
    }

    @PostMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object addBuyListItemByUsername(@PathVariable("username") String username, @RequestParam int commodityId) {
        User user = balootInstance.findUserByUsername(username);
        HashMap<String, Object> response = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = balootInstance.findCommodityById(commodityId);
            if (commodity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            } else if (commodity.getInStock() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient commodity quantity!");
            } else {
                Response responseStatus = user.addToBuyList(commodityId);
                if (responseStatus.success) {
                    ArrayList<CommodityDTO> buyList = balootInstance.getBuyList(username);
                    response.put("buylist", buyList);
                    response.put("totalCost", balootInstance.findUserByUsername(username).getBuyList().totalCost());
                    return response;
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal Error!");
                }
            }
        }
    }

    @DeleteMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object deleteBuyListItemByUsername(@PathVariable("username") String username, @RequestParam int commodityId) {
        HashMap<String, Object> response = new HashMap<>();
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = balootInstance.findCommodityById(commodityId);
            if (commodity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            } else {
                Response responseStatus = user.removeFromBuyList(commodityId);
                if (responseStatus.success) {
                    ArrayList<CommodityDTO> buyList = balootInstance.getBuyList(username);
                    response.put("buylist", buyList);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not found in buylist!");
                }
            }
        }
    }

    @GetMapping(value = "/{username}/purchasedList")
    @ResponseBody
    public Object getPurchasedListByUsername(@PathVariable("username") String username) {
        User user = balootInstance.findUserByUsername(username);
        HashMap<String, Object> purchasedList = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            purchasedList.put("purchasedList", balootInstance.findUserByUsername(username).getPurchasedList().getPurchasedItems());
            return purchasedList;
        }
    }

    @PostMapping(value = "/{username}")
    @ResponseBody
    public Object addBuyListItemByUsername(@PathVariable("username") String username, @RequestParam long creditToAdd) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Response addCreditRes = user.addCredit(creditToAdd);
            if (addCreditRes.success) {
                return ResponseEntity.status(HttpStatus.OK).body(addCreditRes.data);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCreditRes.data);
            }
        }
    }
}
