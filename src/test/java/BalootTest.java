import org.iespring1402.*;
import org.iespring1402.views.CommodityNoInStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BalootTest {
    Baloot baloot;

    @BeforeEach
    void setUp() {
        baloot = new Baloot();

        baloot.addUser(new User("ali", "12345", "ali@gmail.com", "2000-09-01", "Tehran", 5000));
        baloot.addUser(new User("taha", "4512", "taha@gmail.com", "2001-03-31", "Rey", 3000));

        baloot.addProvider(new Provider(1, "Headphone Provider", "2000-01-01"));
        baloot.addProvider(new Provider(2, "Car Provider", "2010-10-10"));
        baloot.addProvider(new Provider(3, "Vegetable Provider", "1900-09-09"));

        baloot.addCommodity(new Commodity(1, "Headphone A", 1, 5200, new ArrayList<>(Arrays.asList("Technology", "Headphone")), 8.3F, 20));
        baloot.addCommodity(new Commodity(2, "Headphone B", 1, 3200, new ArrayList<>(Arrays.asList("Technology", "Headphone")), 10F, 0));
        baloot.addCommodity(new Commodity(3, "Bugatti", 2, 10000000, new ArrayList<>(Arrays.asList("Car")), 8.3F, 2));
        baloot.addCommodity(new Commodity(4, "Onion", 3, 200, new ArrayList<>(Arrays.asList("Vegetables")), 8.3F, 1000));
    }

    @Test
    void testRateCommodity() {
        baloot.rateCommodity("ali", 3, 1);
        Commodity ratedCommodity = baloot.findCommodityById(3);
        assertEquals(1, ratedCommodity.getRating(),
                "Regular rating doesn't work");
        baloot.rateCommodity("ali", 3, 4);
        assertEquals(4, ratedCommodity.getRating(),
                "Changing your rating doesn't work");
        baloot.rateCommodity("taha", 3, 1);
        assertEquals(2.5F, ratedCommodity.getRating(),
                "Multiple ratings doesn't work");
    }

    @Test
    void testGetCommoditiesByCategory() {
        CategoryFilter cf = new CategoryFilter("Technology");
        ArrayList<Commodity> filteredCommodities = cf.applyFilter(baloot.getCommodities());
        for (Commodity commodity : filteredCommodities) {
            assertTrue(commodity.getCategories().contains("Technology"));
        }

        cf.setCategoryItem("Car");
        filteredCommodities = cf.applyFilter(baloot.getCommodities());
        for (Commodity commodity : filteredCommodities) {
            assertTrue(commodity.getCategories().contains("Car"));
        }

        cf.setCategoryItem("Headphone");
        filteredCommodities = cf.applyFilter(baloot.getCommodities());
        for (Commodity commodity : filteredCommodities) {
            assertTrue(commodity.getCategories().contains("Headphone"));
        }
    }

    @Test
    void testAddTobuyList() {
        baloot.addToBuyList("ali", 2);
        ArrayList<CommodityNoInStock> buyList = baloot.getBuyList("ali");
        assertTrue(buyList.isEmpty());

        baloot.addToBuyList("ali", 3);
        buyList = baloot.getBuyList("ali");
        assertEquals(3, buyList.get(0).getId());
    }

    @Test
    void testGetCommodityById() {
        assertNotNull(baloot.findCommodityById(1));
        assertNotNull(baloot.findCommodityById(4));
        assertNull(baloot.findCommodityById(85));
        assertNull(baloot.findCommodityById(69));
    }


}
