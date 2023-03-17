package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Commodity;
import org.iespring1402.Provider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ProviderPage extends Page {
    public static String result(int providerId) throws IOException {
        String dir = System.getProperty("user.dir");
        File providersTemplate = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/Provider.html");
        Document providersDocument = Jsoup.parse(providersTemplate, "UTF-8");
        Element providerIdElement = providersDocument.body().select("li#id").first();
        Element providerNameElement = providersDocument.body().select("li#name").first();
        Element providerRegDateElement = providersDocument.body().select("li#registryDate").first();
        Element table = providersDocument.select("table").first();

        Provider provider = Baloot.getInstance().findProviderByProviderId(providerId);
        if (provider == null)
            return NotFoundPage.result();

        providerIdElement.text("ID: " + providerId);
        providerNameElement.text("Name: " + provider.getName());
        providerRegDateElement.text("Registry Date: " + provider.getRegistryDate());

        for (Map.Entry<Integer, Float> ratingPair : provider.getRatings().entrySet()) {
            int commodityId = ratingPair.getKey();
            float commodityRating = ratingPair.getValue();
            Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);

            Element tableRow = providersDocument.createElement("tr");
            ArrayList<String> categories = commodity.getCategories();

            tableRow.append("<td>" + commodity.getId() + "</td>");
            tableRow.append("<td>" + commodity.getName() + "</td>");
            tableRow.append("<td>" + commodity.getPrice() + "</td>");
            tableRow.append("<td>" + String.join(", ", categories) + "</td>");
            tableRow.append("<td>" + commodityRating + "</td>");
            tableRow.append("<td>" + commodity.getInStock() + "</td>");
            tableRow.append("<td><a href=/commodities/" + commodity.getId() + ">Link</a></td>");

            table.appendChild(tableRow);
        }

        return providersDocument.outerHtml();
    }
}
