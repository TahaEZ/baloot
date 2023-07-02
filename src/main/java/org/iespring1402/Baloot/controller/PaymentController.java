package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;

import java.util.ArrayList;
import java.util.HashMap;

import org.iespring1402.Baloot.entities.BuyList;
import org.iespring1402.Baloot.entities.Commodity;
import org.iespring1402.Baloot.entities.CommodityDTO;
import org.iespring1402.Baloot.entities.DiscountCode;
import org.iespring1402.Baloot.entities.User;
import org.iespring1402.Baloot.repositories.CommodityDAO;
import org.iespring1402.Baloot.repositories.DiscountDAO;
import org.iespring1402.Baloot.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
@CrossOrigin
public class PaymentController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    DiscountDAO discountDAO;

    @Autowired
    CommodityDAO commodityDAO;

    @PostMapping(value = "/{username}")
    @ResponseBody
    public Object validityCheck(
            @RequestParam(value = "discountCode", required = false) String code,
            @PathVariable String username, @RequestAttribute boolean unauthorized) {

        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if (user.getBuyList().getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buy list is empty!");
        }
        if (code != null) {

            if (discountDAO.isValid(code)) {
                if (userDAO.isDiscountCodeUsedByUsername(username, code)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount used before!");
                } else {

                    DiscountCode discountCode = discountDAO.findDiscountCodeByCode(code);
                    double totalCost = ((user.getBuyList().totalCost()) * (100 - discountCode.getDiscount())) / 100;

                    System.out.println(discountCode.getDiscount() / 100);
                    System.out.println(totalCost);
                    if (user.getCredit() < totalCost) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient credit!");
                    }
                    user.addToUsedDiscounts(discountCode);
                    for (CommodityDTO commodity : getBuyListAsList(user.getBuyList())) {
                        user.addToPurchasedList(commodity);
                        boolean succeed = updateCommodityInstock(commodity.getId(), commodity.getQuantity());
                        if (succeed == false) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("Insufficient number of Instock!");

                        }                        user.removeItemFromBuyListCompletely(commodity.getId());
                    }
                    user.setCredit(user.getCredit() - totalCost);
                    userDAO.save(user);

                    return ResponseEntity.status(HttpStatus.OK).body(null);

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid discount code not found!");
            }
        } else {

            DiscountCode discountCode = discountDAO.findDiscountCodeByCode(code);
            double totalCost = user.getBuyList().totalCost();
            if (user.getCredit() < totalCost) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient credit!");
            }
            user.addToUsedDiscounts(discountCode);
            for (CommodityDTO commodity : getBuyListAsList(user.getBuyList())) {
                user.addToPurchasedList(commodity);
                boolean succeed = updateCommodityInstock(commodity.getId(), commodity.getQuantity());
                if(succeed == false)
                {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient number of Instock!");

                }
                user.removeItemFromBuyListCompletely(commodity.getId());
            }
            user.setCredit(user.getCredit() - totalCost);
            userDAO.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        }

    }

    public ArrayList<CommodityDTO> getBuyListAsList(BuyList buyList) {
            HashMap<Integer, Integer> buylist = buyList.getItems();
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
    public boolean updateCommodityInstock(int commodityId , int quantity)
    {
        Commodity commodityToChangeInstock = commodityDAO.findCommodityById(commodityId);
        if(quantity > commodityToChangeInstock.getInStock())
        {
            return false;
        }
        commodityToChangeInstock.setInStock(commodityToChangeInstock.getInStock() - quantity);
        commodityDAO.save(commodityToChangeInstock);
        return true;
    }
}
