package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Commodity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class FindCommodityByPriceRangePage {

    private static ArrayList<Commodity> filteredByPriceRange = new ArrayList<>();
    public static String result(long startPrice , long endPrice ) throws IOException {


        if(startPrice > endPrice || startPrice < 0) {return ForbiddenPage.result();}

        filteredByPriceRange.clear();

        ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
        for(Commodity commodity : commodities)
        {
            if(commodity.getPrice() >= startPrice && commodity.getPrice() <= endPrice )
                filteredByPriceRange.add(commodity);
        }

        if (filteredByPriceRange.isEmpty()) return NotFoundPage.result();

        Document commoditiesByPriceRangeDocument = CommoditiesPage.createCommoditiesDocument(filteredByPriceRange);
        return commoditiesByPriceRangeDocument.outerHtml();
    }

    public static ArrayList<Commodity> getFilteredByPriceRange() {
        return filteredByPriceRange;
    }
}
