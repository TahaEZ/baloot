package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

import java.util.ArrayList;

public class BuyList {
    private ArrayList<Integer> list;

    public BuyList() {
        list = new ArrayList<Integer>();
    }

    public Response add(int commodityId) {
        if (list.contains(commodityId)) {
            return new FailedResponse("This commodity already exists in your buy list!");
        }

        list.add(commodityId);
        return new SuccessfulResponse();
    }

    public Response remove(int commodityId) {
        int index = list.indexOf(commodityId);
        if (index != -1) {
            list.remove(index);
            return new SuccessfulResponse();
        } else return new FailedResponse("No commodity found with this commodity id in your buy list!");
    }

    public int totalCost(){
        int total = 0;
        for(int id : list)
        {
            int cost = Baloot.getInstance().findCommodityById(id).getPrice();
            total += cost;
        }
        return total;
    }
    public ArrayList<Integer> getList() {
        return list;
    }
}
