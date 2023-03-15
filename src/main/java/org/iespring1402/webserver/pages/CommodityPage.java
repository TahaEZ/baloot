package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Comment;
import org.iespring1402.Commodity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommodityPage extends Page {
    public static String result(int commodityId) throws IOException {
        Baloot baloot = Baloot.getInstance();
        Commodity commodity = baloot.findCommodityById(commodityId);
        if (commodity == null) return NotFoundPage.result();
        else {
            String dir = System.getProperty("user.dir");
            File commodityTemplate = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/Commodity.html");
            Document commodityDocument = Jsoup.parse(commodityTemplate, "UTF-8");

            Element commodityIdElement = commodityDocument.body().select("li#id").first();
            Element commodityNameElement = commodityDocument.body().select("li#name").first();
            Element commodityProviderIdElement = commodityDocument.body().select("li#providerId").first();
            Element commodityPriceElement = commodityDocument.body().select("li#price").first();
            Element commodityCategoriesElement = commodityDocument.body().select("li#categories").first();
            Element commodityRatingElement = commodityDocument.body().select("li#rating").first();
            Element commodityInStockElement = commodityDocument.body().select("li#inStock").first();
            Element rateCommodityForm = commodityDocument.body().select("form#rate-commodity").first();
            Element addToBuyListForm = commodityDocument.body().select("form#add-to-buy-list").first();
            Element table = commodityDocument.select("table").first();

            commodityIdElement.text("ID: " + commodity.getId());
            commodityNameElement.text("Name: " + commodity.getName());
            commodityProviderIdElement.text("Provider ID: " + commodity.getProviderId());
            commodityPriceElement.text("Price: " + commodity.getPrice());
            commodityCategoriesElement.text("Categories: " + String.join(", ", commodity.getCategories()));
            commodityRatingElement.text("Rating: " + commodity.getRating());
            commodityInStockElement.text("In Stock: " + commodity.getInStock());

            rateCommodityForm.select("input[name=\"commodity-id\"]").val(commodityId + "");
                addToBuyListForm.select("input[name=\"commodity-id\"]").val(commodityId + "");

            ArrayList<Comment> commodityComments = baloot.getFilteredCommentsByCommodityId(commodityId);
            for (Comment comment : commodityComments) {
                Element tableRow = commodityDocument.createElement("tr");
                tableRow.append("<td>" + comment.getUserEmail() + "</td>");
                tableRow.append("<td>" + comment.getText() + "</td>");
                tableRow.append("<td>" + comment.getDate() + "</td>");
                tableRow.append("<td>" +
                        "<form id=\"like-comment-" + comment.getId() + "\" method=\"POST\" action=\"/voteComment\">" +
                        "<input type=\"text\" name=\"username\" /><br/>" +
                        "<input type=\"hidden\" name=\"comment-id\" value=\"" + comment.getId() + "\" />" +
                        "<span>" + comment.likesCount() + "</span>" +
                        "<input type=\"hidden\" name=\"vote\" value=\"1\" />" +
                        "<button type=\"submit\">Like</button>" +
                        "</form>" +
                        "</td>");
                tableRow.append("<td>" +
                        "<form id=\"dislike-comment-" + comment.getId() + "\" method=\"POST\" action=\"/voteComment\">" +
                        "<input type=\"text\" name=\"username\"/><br/>" +
                        "<input type=\"hidden\" name=\"comment-id\" value=\"" + comment.getId() + "\" />" +
                        "<span>" + comment.dislikesCount() + "</span>" +
                        "<input type=\"hidden\" name=\"vote\" value=\"-1\" />" +
                        "<button type=\"submit\">Dislike</button>" +
                        "</form>" +
                        "</td>");

                table.appendChild(tableRow);
            }

            return commodityDocument.outerHtml();
        }
    }
}
