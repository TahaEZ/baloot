package org.iespring1402.webserver.pages;

import org.iespring1402.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UserPage extends Page{
    public static String result(String userId) throws IOException {
        String dir = System.getProperty("user.dir");
        File userTemplate = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/User.html");
        Document userDocument = Jsoup.parse(userTemplate, "UTF-8");
        Element paymentForm = userDocument.body().select("form#payment").first();
        Element userUserNameElement = userDocument.body().select("li#username").first();
        Element userEmailElement = userDocument.body().select("li#email").first();
        Element userBirthDateElement = userDocument.body().select("li#birthDate").first();
        Element userAddressElement = userDocument.body().select("li#address").first();
        Element userCreditElement = userDocument.body().select("li#credit").first();
        Element buyListTable = userDocument.select("table#buyList").first();
        Element purchasedListTable = userDocument.select("table#purchasedList").first();

        User user = Baloot.getInstance().findUserByUsername(userId);
        if (user == null)
            return NotFoundPage.result();

        userUserNameElement.text("Username: " + user.getUsername());
        userEmailElement.text("Email: " + user.getEmail());
        userBirthDateElement.text("Birth Date: " + user.getBirthDate());
        userAddressElement.text(user.getAddress());
        userCreditElement.text("Credit: "+user.getCredit());
        paymentForm.select("input[name=\"userId\"]").val(user.getUsername()+ "");



        BuyList userBuyList = user.getBuyList();
        for (int buyListItem :userBuyList.getList()) {
            Commodity commodity = Baloot.getInstance().findCommodityById(buyListItem);
            Element tableRow = userDocument.createElement("tr");
            ArrayList<String> categories = commodity.getCategories();

            tableRow.append("<td>" + commodity.getId() + "</td>");
            tableRow.append("<td>" + commodity.getName() + "</td>");
            tableRow.append("<td>" + commodity.getProviderId() + "</td>");
            tableRow.append("<td>" + commodity.getPrice() + "</td>");
            tableRow.append("<td>" + String.join(", ", categories) + "</td>");
            tableRow.append("<td>" + commodity.getRating() + "</td>");
            tableRow.append("<td>" + commodity.getInStock() + "</td>");
            tableRow.append("<td><a href=/commodities/" + commodity.getId() + ">Link</a></td>");
            tableRow.append("<td>"
                    +"<form id=\"removeFromBuyList\"  method=\"POST\" action= \"/RemoveFromBuyList\" >"
                    + "<input type=\"hidden\" name=\"commodityId\" value=\"" + commodity.getId()  + "\" />"
                    + "<input type=\"hidden\" name=\"userId\" value=\"" + user.getUsername()  + "\" />"
                    + "<button type=\"submit\">Remove</button>"
                    + "</form>"
                    + "</td>");

            buyListTable.appendChild(tableRow);
        }

        PurchasedList userPurchasedList = user.getPurchasedList();
        for ( Commodity purchased : userPurchasedList.getPurchasedItems()) {
            Element tableRow = userDocument.createElement("tr");
            ArrayList<String> categories = purchased.getCategories();

            tableRow.append("<td>" + purchased.getId() + "</td>");
            tableRow.append("<td>" + purchased.getName() + "</td>");
            tableRow.append("<td>" + purchased.getProviderId() + "</td>");
            tableRow.append("<td>" + purchased.getPrice() + "</td>");
            tableRow.append("<td>" + String.join(", ", categories) + "</td>");
            tableRow.append("<td>" + purchased.getRating() + "</td>");
            tableRow.append("<td>" + purchased.getInStock() + "</td>");
            tableRow.append("<td><a href=/commodities/" + purchased.getId() + ">Link</a></td>");

            purchasedListTable.appendChild(tableRow);
        }
        return userDocument.outerHtml();
    }
}
