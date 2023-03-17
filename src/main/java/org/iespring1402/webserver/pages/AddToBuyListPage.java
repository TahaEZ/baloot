package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Commodity;
import org.iespring1402.User;
import org.iespring1402.response.Response;

import java.io.IOException;

public class AddToBuyListPage extends Page {
    public static String result(String username, int commodityId) throws IOException {
        User user = Baloot.getInstance().findUserByUsername(username);
        Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);
        if (user == null || commodity == null) return NotFoundPage.result();
        else {
            Response addToBuyListResponse = user.addToBuyList(commodityId);
            if (addToBuyListResponse.success) {
                return OKPage.result();
            } else {
                return ForbiddenPage.result();
            }
        }
    }
}
