import io.javalin.Javalin;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import org.iespring1402.Baloot;

import org.iespring1402.BuyList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebServerTest {

   private Baloot baloot;
   private  Javalin app;

    @BeforeEach
    void setup() {
        baloot = Baloot.getInstance();
        app = Javalin.create().start(7000);
    }

    @AfterEach
    public void teardown(){
        app.stop();
    }

    @Test
    public  void testAddTobuyList() {
        String username = "ali";
        int commodityIdToAdd = 1;
        given().get("http://localhost:7000/addToBuyList/{username}/{commodity-id}",username,commodityIdToAdd );
        BuyList buyList =baloot.findUserByUsername("ali").getBuyList();
        ArrayList <Integer> commodityId = buyList.getList();
        assertEquals(1, commodityId.get(0));
    }
}
