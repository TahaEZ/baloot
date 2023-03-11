package org.iespring1402;

import java.util.ArrayList;

public class CategoryFilter {
    private Object category;
    private static CategoryFilter instance;

    public static CategoryFilter getInstance() {
        if (instance == null)
            instance = new CategoryFilter();
        return instance;
    }
    public CategoryFilter(ArrayList<String> categoryListItemItem) {
        this.category = categoryListItemItem;
    }

    public CategoryFilter(String categoryItem) {
        this.category = categoryItem;
    }

    public CategoryFilter() {
        super();
    }

    public Object getCategoryListItem() {
        return category;
    }

    public void setCategoryItem(String categoryItem) {
        this.category = categoryItem;
    }

    public ArrayList<Commodity> applyFilter(ArrayList<Commodity> commodities) {
        ArrayList<Commodity> filteredCommoditiesByCategory = new ArrayList<Commodity>();
        for (Commodity commodity : commodities) {
            if (commodity.getCategories().contains(this.category)) {
                filteredCommoditiesByCategory.add(commodity);
            }
        }
        return filteredCommoditiesByCategory;
    }

    public void setCategoryListItem(ArrayList<String> categoryListItem) {
        this.category = categoryListItem;
    }
}
