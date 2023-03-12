package org.iespring1402.webserver;

import io.javalin.Javalin;
import org.iespring1402.webserver.pages.*;

public class WebServer {
    public static void main(String[] args) {
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

        app.get("200", context -> context.html(OKPage.result()));

        app.get("/403", context -> context.html(ForbiddenPage.result()));

        app.get("/404", context -> context.html(NotFoundPage.result()));

        app.error(404, context -> context.html(NotFoundPage.result()));
    }
}
