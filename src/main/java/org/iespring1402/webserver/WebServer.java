package org.iespring1402.webserver;

import io.javalin.Javalin;
import org.iespring1402.Baloot;
import org.iespring1402.webserver.pages.*;

public class WebServer {
    public static void main(String[] args) {
        Baloot.getInstance();

        Javalin app = Javalin.create().start(8080);
        app.get("/", context -> context.html(HelloPage.result()));
        app.get("/commodities", context -> context.html(CommoditiesPage.result()));
        app.get("/providers/{provider-id}", context -> {
            try {
                int providerId = Integer.parseInt(context.pathParam("provider-id"));
                context.html(ProviderPage.result(providerId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });
        app.get("/users/{user-id}", context -> {
            try {
                String userId = context.pathParam("user-id");
                context.html(UserPage.result(userId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });

        app.post("/buyListPayment",context -> {
            try {
                String userId = context.formParam("userId");
                context.html(BuyListPaymentPage.result(userId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });

        app.get("/addCredit/{user-id}/{credit}",context -> {
            try {
                String userId = context.pathParam("user-id");
                long creditToAdd = Long.parseLong(context.pathParam("credit"));
                if(creditToAdd <= 0 )
                    context.html(ForbiddenPage.result());
                else
                    context.html(AddToCreditPage.result(userId,creditToAdd));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });

        app.post("/removeFromBuyList",context -> {
            try {
                String username = context.formParam("userId");
                int commodityId = Integer.parseInt(context.formParam("commodityId"));
                context.redirect("/removeFromBuyList/" + username + "/" + commodityId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });

        app.get("/removeFromBuyList/{username}/{commodityId}", context -> {
                try {
                    String userId = context.pathParam("username");
                    int commodityId = Integer.parseInt(context.pathParam("commodityId"));
                    context.html(RemoveFromBuyListPage.result(userId,commodityId));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    context.html(ForbiddenPage.result());
                }
        });

        app.post("addToBuyList", context -> {
            String username = context.formParam("username");
            String commodityId = context.formParam("commodity-id");
            context.redirect("/addToBuyList/" + username + "/" + commodityId);
        });
        app.get("/addToBuyList/{username}/{commodity-id}", context -> {
            try {
                String username = context.pathParam("username");
                int commodityId = Integer.parseInt(context.pathParam("commodity-id"));
                context.html(AddToBuyListPage.result(username, commodityId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });
        app.post("rateCommodity", context -> {
            String username = context.formParam("username");
            String commodityId = context.formParam("commodity-id");
            String rate = context.formParam("rate");
            context.redirect("/rateCommodity/" + username + "/" + commodityId + "/" + rate);
        });
        app.get("/rateCommodity/{username}/{commodity-id}/{rate}", context -> {
            try {
                String username = context.pathParam("username");
                int commodityId = Integer.parseInt(context.pathParam("commodity-id"));
                int rate = Integer.parseInt(context.pathParam("rate"));
                context.html(RateCommodityPage.result(username, commodityId, rate));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });

        app.get("/commodities/search/{start_price}/{end_price}" , context -> {
            long startPrice = Long.parseLong(context.pathParam("start_price"));
            long endPrice = Long.parseLong(context.pathParam("end_price"));
            context.html(FindCommodityByPriceRangePage.result(startPrice,endPrice));
        });

        app.get("/commodities/search/{categories}", context -> {
            String categories = context.pathParam("categories");
            context.html(SearchCommodityByCategoryPage.result(categories));
        });
        app.post("voteComment", context -> {
           String username = context.formParam("username");
            String commentId = context.formParam("comment-id");
            String vote = context.formParam("vote");
            context.redirect("/voteComment/" + username + "/" + commentId + "/" + vote);
        });
        app.get("/voteComment/{username}/{comment-id}/{vote}", context -> {
            try {
                String username = context.pathParam("username");
                String commentId = context.pathParam("comment-id");
                int vote = Integer.parseInt(context.pathParam("vote"));
                context.html(VoteCommentPage.result(username, commentId, vote));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });
        app.get("/commodities/{commodity-id}", context -> {
            try {
                int commodityId = Integer.parseInt(context.pathParam("commodity-id"));
                context.html(CommodityPage.result(commodityId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                context.html(ForbiddenPage.result());
            }
        });
        app.get("200", context -> context.html(OKPage.result()));
        app.get("/403", context -> context.html(ForbiddenPage.result()));
        app.get("/404", context -> context.html(NotFoundPage.result()));
        app.error(404, context -> context.html(NotFoundPage.result()));
    }
}
