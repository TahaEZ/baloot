package org.iespring1402.Baloot.controller;

import java.util.ArrayList;
import java.util.HashMap;

public class PaginationController {

    private ArrayList<Object> items;

    public PaginationController(ArrayList<?> itemsToPaginate) {
        items = (ArrayList<Object>) itemsToPaginate;
    }

    public HashMap<String, Object> paginateItems(int pageSize, int pageNo) {
        HashMap<String, Object> paginatedPack = new HashMap<>();
        int itemsSize = items.size();
        int totalPagesNumber = itemsSize / pageSize;
        if (itemsSize % pageSize != 0) {
            totalPagesNumber += 1;
        }
        paginatedPack.put("totalPages", totalPagesNumber);
        if (pageNo > totalPagesNumber) {
            return paginatedPack;
        } else {
            int start = (pageNo - 1) * pageSize;
            ArrayList<Object> itemsPage = new ArrayList<>();
            if ((itemsSize - (start)) >= pageSize) {

                for (int dynamicStart = start; dynamicStart < start + pageSize; dynamicStart++) {
                    itemsPage.add(items.get(dynamicStart));
                }

                paginatedPack.put("commoditiesList", itemsPage);
                return paginatedPack;

            } else {
                for (int dynamicStart = start; dynamicStart < itemsSize; dynamicStart++) {
                    itemsPage.add(items.get(dynamicStart));
                }

                paginatedPack.put("commoditiesList", itemsPage);
                return paginatedPack;
            }
        }
    }

}
