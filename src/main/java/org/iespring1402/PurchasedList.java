package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

import java.util.ArrayList;

public class PurchasedList {
    private ArrayList<Commodity> purchasedItems;

    public PurchasedList(int id, String name, int providerId, int price, ArrayList<String> categories, float rating, int inStock) {
        purchasedItems = new ArrayList<Commodity>();
    }

    public Response add(Commodity commodity) {
        purchasedItems.add(commodity);
        return new SuccessfulResponse();
    }

    public ArrayList<Commodity> getPurchasedItems() {
        return purchasedItems;
    }
}
