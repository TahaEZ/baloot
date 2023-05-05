package org.iespring1402.Baloot.models;

import org.iespring1402.Baloot.models.views.CommodityDTO;
import org.iespring1402.Baloot.response.Response;
import org.iespring1402.Baloot.response.SuccessfulResponse;

import java.util.ArrayList;

public class PurchasedList {
    private ArrayList<CommodityDTO> purchasedItems = new ArrayList<>();

    public PurchasedList(ArrayList<CommodityDTO> purchased) {
        purchasedItems = purchased;
    }
    public PurchasedList(){
        purchasedItems = new ArrayList<>();
    }

    public Response add(CommodityDTO commodity) {
        purchasedItems.add(commodity);
        return new SuccessfulResponse();
    }

    public ArrayList<CommodityDTO> getPurchasedItems() {
        return purchasedItems;
    }
}
