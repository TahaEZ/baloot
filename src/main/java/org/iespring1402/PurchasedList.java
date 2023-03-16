package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

import java.util.ArrayList;

public class PurchasedList {
    private ArrayList<Commodity> purchasedItems = new ArrayList<>();

    public PurchasedList(ArrayList<Commodity> purchased) {
        purchasedItems = purchased;
    }
    public PurchasedList(){
        purchasedItems = new ArrayList<>();
    }

    public Response add(Commodity commodity) {
        purchasedItems.add(commodity);
        return new SuccessfulResponse();
    }

    public ArrayList<Commodity> getPurchasedItems() {
        return purchasedItems;
    }
}
