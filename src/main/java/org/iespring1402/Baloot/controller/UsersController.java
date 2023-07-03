package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.entities.BuyList;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.entities.User;
import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.repositories.CommodityDAO;
import org.iespring1402.Baloot.repositories.UserDAO;
import org.iespring1402.Baloot.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UserDAO userDAO;

    @Autowired 
    private CommodityDAO commodityDAO;

    @GetMapping(value = "/{username}")
    @ResponseBody
    public Object getUserByUsername(@PathVariable("username") String username, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            return user;
        }
    }

    @GetMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object getBuyListByUsername(@PathVariable("username") String username,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        HashMap<String, Object> buylist = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            buylist.put("buylist", getBuyListAsList(user.getBuyList()) );
            buylist.put("totalCost", user.getBuyList().totalCost());
            return buylist;
        }
    }

    @PostMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object addBuyListItemByUsername(@PathVariable("username") String username, @RequestParam int commodityId,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        HashMap<String, Object> response = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = commodityDAO.findCommodityById(commodityId);
            
            if (commodity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            } else if (commodity.getInStock() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient commodity quantity!");
            } else {
                Response responseStatus = user.addToBuyList(commodityId);
                if (responseStatus.success) {
                    userDAO.save(user);
                    ArrayList<CommodityDTO> buyList = getBuyListAsList(userDAO.getUserByUsername(username).getBuyList());
                    response.put("buylist", buyList);
                    response.put("totalCost", userDAO.getUserByUsername(username).getBuyList().totalCost());
                    return response;
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal Error!");
                }
            }
        }
    }
    
    public ArrayList<CommodityDTO> getBuyListAsList(BuyList buyList) {
        Map<Integer, Integer> buylist = buyList.getItems();
        ArrayList<CommodityDTO> result = new ArrayList<>();
        for (HashMap.Entry<Integer, Integer> item : buylist.entrySet()) {
            Commodity commodity = commodityDAO.findCommodityById(item.getKey());

            CommodityDTO buylistItem = new CommodityDTO(commodity.getId(), commodity.getName(),
                    commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(),
                    commodity.getRating(), commodity.getInStock(), item.getValue(), commodity.getImage());

            result.add(buylistItem);
        }
        return result;
    }

    @DeleteMapping(value = "/{username}/buyList")
    @ResponseBody
    public Object deleteBuyListItemByUsername(@PathVariable("username") String username,
            @RequestParam int commodityId, @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        HashMap<String, Object> response = new HashMap<>();
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Commodity commodity = commodityDAO.findCommodityById(commodityId);
            if (commodity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commodity not found!");
            } else {
                Response responseStatus = user.removeFromBuyList(commodityId);
                if (responseStatus.success) {
                    ArrayList<CommodityDTO> buyList = getBuyListAsList(userDAO.getUserByUsername(username).getBuyList());
                    response.put("buylist", buyList);
                    userDAO.save(user);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not found in buylist!");
                }
            }
        }
    }

    @GetMapping(value = "/{username}/purchasedList")
    @ResponseBody
    public Object getPurchasedListByUsername(@PathVariable("username") String username,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        HashMap<String, Object> purchasedList = new HashMap<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            purchasedList.put("purchasedList",
                    userDAO.getUserByUsername(username).getPurchasedItems());
            return purchasedList;
        }
    }

    @PostMapping(value = "/{username}")
    @ResponseBody
    public Object addBuyListItemByUsername(@PathVariable("username") String username, @RequestParam long creditToAdd,
            @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            Response addCreditRes = user.addCredit(creditToAdd);
            if (addCreditRes.success) {
                userDAO.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(addCreditRes.data);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCreditRes.data);
            }
        }
    }
}
