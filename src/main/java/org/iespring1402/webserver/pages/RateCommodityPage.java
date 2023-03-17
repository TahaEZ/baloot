package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.User;
import org.iespring1402.response.Response;

import java.io.IOException;

public class RateCommodityPage extends Page {
    public static String result(String username, int commodityId, int rate) throws IOException {
        User user = Baloot.getInstance().findUserByUsername(username);
        if (user == null) return NotFoundPage.result();
        else {
            Response rateCommodityResponse = Baloot.getInstance().rateCommodity(username, commodityId, rate);
            if (rateCommodityResponse.success) {
                return OKPage.result();
            } else {
                return NotFoundPage.result();
            }
        }
    }
}
