package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.BuyList;
import org.iespring1402.Commodity;
import org.iespring1402.User;

import java.io.IOException;
import java.util.ArrayList;

public class BuyListPaymentPage extends Page{
    public static String result(String username) throws IOException {
        User user = Baloot.getInstance().findUserByUsername(username);
        BuyList buyLIst = user.getBuyList();
        long totalCost = 0;
        ArrayList<Commodity> actualBuyList = new ArrayList<>();
        for(int commodityId : buyLIst.getList())
        {
             actualBuyList.add(Baloot.getInstance().findCommodityById(commodityId));
             totalCost += Baloot.getInstance().findCommodityById(commodityId).getPrice();
        }
        long userCredit = user.getCredit();
        if(totalCost > userCredit)
        {
            return ForbiddenPage.result();
        }
        else{
            user.setCredit(userCredit - totalCost);
            for (Commodity commodity : actualBuyList){
                user.addToPurchasedList(commodity);
                user.removeFromBuyList(commodity.getId());
            }
            return OKPage.result();
        }
    }
}
