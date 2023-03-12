package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.CategoryFilter;
import org.iespring1402.Commodity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SearchCommodityByCategoryPage extends Page {
    public static String result(String category) throws IOException {
        ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
        CategoryFilter filter = new CategoryFilter();
        filter.setCategoryItem(category);
        commodities = filter.applyFilter(commodities);

        if (commodities.isEmpty()) return NotFoundPage.result();

        Document commoditiesDocument = CommoditiesPage.createCommoditiesDocument(commodities);
        return commoditiesDocument.outerHtml();
    }
}
