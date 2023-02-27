package org.iespring1402;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.iespring1402.response.SuccessfulResponse;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Baloot baloot;
    public static final String ADD_USER = "addUser";
    public static final String ADD_PROVIDER = "addProvider";
    public static final String ADD_COMMODITY = "addCommodity";
    public static final String GET_COMMODITIES_LIST = "getCommoditiesList";
    public static final String RATE_COMMODITY = "rateCommodity";
    public static final String ADD_TO_BUY_LIST = "addToBuyList";
    public static final String REMOVE_FROM_BUY_LIST = "removeFromBuyList";
    public static final String GET_COMMODITY_BY_ID = "getCommodityById";
    public static final String GET_COMMODITIES_BY_CATEGORY = "getCommoditiesByCategory";
    public static final String GET_BUY_LIST = "getBuyList";
    public static final String EXIT = "exit";

    public static void main(String[] args) throws Exception {
        baloot = new Baloot();

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Enter a command:\n");
            input = scanner.nextLine();

            if (input.equals(EXIT)) {
                break;
            }

            String command, jsonData;
            String[] parseInputResult;

            parseInputResult = parseUserInput(input);
            command = parseInputResult[0];
            jsonData = parseInputResult[1];

            Response response = runCommand(command, jsonData);
            Response.printSerializeRes(response);
        }
    }

    static String[] parseUserInput(String input) {
        String[] result = new String[2];

        int firstSpace = input.indexOf(" ");
        String command, jsonData = null;

        if (firstSpace == -1) {
            command = input;
        } else {
            command = input.substring(0, firstSpace);
            jsonData = input.substring(firstSpace + 1);
        }

        result[0] = command;
        result[1] = jsonData;

        return result;
    }

    static Response runCommand(String command, String jsonData) throws Exception {
        Response response = new FailedResponse();
        ObjectMapper mapper = new ObjectMapper();

        switch (command) {
            case ADD_USER:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    User newUser = mapper.readValue(jsonData, User.class);
                    if (baloot.addUser(newUser))
                        return new SuccessfulResponse();
                    else
                        return new FailedResponse("Invalid username!");
                }
            case ADD_PROVIDER:
                // TODO: Add Provider Command
                break;
            case ADD_COMMODITY:
                mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                try {
                    Commodity commodity = mapper.readValue(jsonData, Commodity.class);
                    if (baloot.ifCommodityExist(commodity.getId())) {
                       Response failedResponse =new FailedResponse( "This commodity is duplicated.");
                        return failedResponse;
                    } else {
                        baloot.addCommodity(commodity);
                        Response successfulResponseresponse = new SuccessfulResponse();
                        return successfulResponseresponse;
                    }
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // TODO: Add Commodity Command
                break;
            case GET_COMMODITIES_LIST:
                // TODO: Get Commodities List Command
                break;
            case RATE_COMMODITY:
                // TODO: Rate Commodity Command
                break;
            case ADD_TO_BUY_LIST:
                // TODO: Add to Buy List Command
                break;
            case REMOVE_FROM_BUY_LIST:
                // TODO: Remove From Buy List Command
                break;
            case GET_COMMODITY_BY_ID:
                // TODO: Get Commodities By Id Command
                break;
            case GET_COMMODITIES_BY_CATEGORY:
                // TODO: Get Commodities By Category Command
                break;
            case GET_BUY_LIST:
                // TODO: Get Buy List Command
                break;
            default:
                break;
        }
        return null; // TODO: There is nothing to be  sent(each statement handle its return value)
    }
}