import org.iespring1402.Baloot;

import org.iespring1402.BuyList;
import org.iespring1402.Commodity;
import org.iespring1402.response.Response;
import org.iespring1402.webserver.pages.AddToBuyListPage;
import org.iespring1402.webserver.pages.FindCommodityByPriceRangePage;
import org.iespring1402.webserver.pages.RemoveFromBuyListPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WebServerTest {

    private Baloot baloot;

    @BeforeEach
    void setup() {
        baloot = Baloot.getInstance();
    }

    @AfterEach
    public void teardown() {
        Baloot.removeInstance();
    }

    @Test
    public void testBuyList() {
        String username = "ali";
        int[] commodityIdToAdd = {1, 2};
        try {
            AddToBuyListPage.result(username, commodityIdToAdd[0]);
            AddToBuyListPage.result(username, commodityIdToAdd[1]);
            BuyList buyList = baloot.findUserByUsername("ali").getBuyList();
            ArrayList<Integer> commodityId = buyList.getList();
            assertEquals(1, commodityId.get(0));
            assertEquals(2, commodityId.get(1));
            RemoveFromBuyListPage.result(username, commodityIdToAdd[0]);
            assertEquals(2, commodityId.get(0));
            RemoveFromBuyListPage.result(username, commodityIdToAdd[1]);
            assertEquals(0, commodityId.size());
        } catch (IOException e) {
            System.out.println("IOException error");
            fail();
        }
    }

    private void callPriceRangeFilter(FindCommodityByPriceRangePage priceRangeFilter, int startPrice, int endPrice) {
        try {
            priceRangeFilter.result(startPrice, endPrice);
        } catch (IOException e) {
            System.out.println("IOException error");
            fail();
        }
    }

    @Test
    public void testCommodityByPriceRange() {

        int startPrice = 1000;
        int endPrice = 50000;
        FindCommodityByPriceRangePage priceRangeFilter = new FindCommodityByPriceRangePage();
        callPriceRangeFilter(priceRangeFilter, startPrice, endPrice);
        ArrayList<Commodity> filtered = priceRangeFilter.getFilteredByPriceRange();
        for (Commodity commodity : filtered) {
            assertTrue(commodity.getPrice() >= startPrice);
            assertTrue(commodity.getPrice() <= endPrice);
        }
        startPrice = 14000;
        endPrice = 14000;
        callPriceRangeFilter(priceRangeFilter, startPrice, endPrice);
        for (Commodity commodity : filtered) {
            assertTrue(commodity.getPrice() >= startPrice);
            assertTrue(commodity.getPrice() <= endPrice);
        }
        startPrice = 0;
        endPrice = 0;
        callPriceRangeFilter(priceRangeFilter, startPrice, endPrice);

        assertEquals(0, filtered.size());

        startPrice = -1;
        endPrice = 1000;
        callPriceRangeFilter(priceRangeFilter, startPrice, endPrice);

        assertEquals(0, filtered.size());
    }


    @Test
    public void testRateCommodity() {
        Commodity commodity = baloot.findCommodityById(2);
        Response response;

        baloot.rateCommodity("amir", 2, 7);
        assertEquals(commodity.getRating(), 7);
        baloot.rateCommodity("hamid", 2, 2);
        assertEquals(commodity.getRating(), 4.5);
        baloot.rateCommodity("amir", 2, 3);
        assertEquals(commodity.getRating(), 2.5);
        response = baloot.rateCommodity("SOME_INVALID_USERNAME", 2, 9);
        assertFalse(response.success);
        response = baloot.rateCommodity("amir", 2, 11);
        assertFalse(response.success);
    }

}