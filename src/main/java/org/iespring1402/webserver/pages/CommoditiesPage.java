package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Commodity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommoditiesPage extends Page {
    public static Document createCommoditiesDocument(ArrayList<Commodity> commodities) throws IOException {
        String dir = System.getProperty("user.dir");
        File commoditiesTemplate = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/Commodities.html");
        Document commoditiesDocument = Jsoup.parse(commoditiesTemplate, "UTF-8");
        Element table = commoditiesDocument.select("table").first();

        for (Commodity commodity : commodities) {
            Element tableRow = commoditiesDocument.createElement("tr");
            ArrayList<String> categories = commodity.getCategories();

            tableRow.append("<td>" + commodity.getId() + "</td>");
            tableRow.append("<td>" + commodity.getName() + "</td>");
            tableRow.append("<td>" + commodity.getProviderId() + "</td>");
            tableRow.append("<td>" + commodity.getPrice() + "</td>");
            tableRow.append("<td>" + String.join(", ", categories) + "</td>");
            tableRow.append("<td>" + commodity.getRating() + "</td>");
            tableRow.append("<td>" + commodity.getInStock() + "</td>");
            tableRow.append("<td><a href=/commodities/" + commodity.getId() + ">Link</a></td>");

            table.appendChild(tableRow);
        }
        return commoditiesDocument;
    }

    public static String result() throws IOException {
        ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
        Document commoditiesDocument = createCommoditiesDocument(commodities);
        return commoditiesDocument.outerHtml();
    }
}
