package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.DiscountCode;
import org.iespring1402.Baloot.models.User;
import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {
    private Baloot balootInstance = Baloot.getInstance();

    @PostMapping(value = "/{username}")
    @ResponseBody
    public Object validityCheck(
            @RequestParam(value = "discountCode", required = false) String code,
            @PathVariable String username) {
        User user = balootInstance.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if (user.getBuyList().getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Buy list is empty!");
        }
        if (code != null) {

            if (balootInstance.discountCodeValidityCheck(code)) {
                if (user.isDiscountCodeUsed(code)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount used before!");
                } else {

                    DiscountCode discountCode = balootInstance.findDiscountCodeByCode(code);
                    double totalCost = ((user.getBuyList().totalCost()) * (100 - discountCode.getDiscount()))/100;
                    
                    System.out.println(discountCode.getDiscount()/100);
                    System.out.println(totalCost);
                    if(user.getCredit() < totalCost)
                    {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient credit!");
                    }
                    user.addToUsedDiscounts(discountCode);
                    for(CommodityDTO commodity : balootInstance.getBuyList(username)){
                        user.addToPurchasedList(commodity);
                        user.removeItemFromBuyListCompletely(commodity.getId());
                    }
                    user.setCredit(user.getCredit() - totalCost);
                    
                    return ResponseEntity.status(HttpStatus.OK).body(null);

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid discount code not found!");
            }
        }
        else{

                DiscountCode discountCode = balootInstance.findDiscountCodeByCode(code);
                double totalCost = user.getBuyList().totalCost();
                if (user.getCredit() < totalCost) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient credit!");
                }
                user.addToUsedDiscounts(discountCode);
                for (CommodityDTO commodity : balootInstance.getBuyList(username)) {
                    user.addToPurchasedList(commodity);
                    user.removeItemFromBuyListCompletely(commodity.getId());
                }
                user.setCredit(user.getCredit()-totalCost);
                return ResponseEntity.status(HttpStatus.OK).body(null);

            }
        
    }

}