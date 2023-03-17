package org.iespring1402;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;
import org.iespring1402.views.CommodityNoInStock;
import org.iespring1402.views.CommodityByIdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
            Response.printSerializedRes(response);
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
        Response response;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);


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
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    Provider newProvider = mapper.readValue(jsonData, Provider.class);
                    baloot.addProvider(newProvider);
                    return new SuccessfulResponse();
                }
            case ADD_COMMODITY:
                if (jsonData == null || jsonData.isEmpty()) {
                    response = new FailedResponse("Please enter the user JSON data.");
                } else {
                    mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                    Commodity commodity = mapper.readValue(jsonData, Commodity.class);
                    if (baloot.commodityExist(commodity.getId())) {
                        baloot.updateCommodity(commodity.getId(), commodity);
                        response = new SuccessfulResponse("The commodity was updated.");
                    } else {
                        baloot.addCommodity(commodity);
                        response = new SuccessfulResponse();
                    }
                }
                return response;
            case GET_COMMODITIES_LIST:
                if (baloot.getCommodities().isEmpty()) {
                    return new FailedResponse();
                } else {
                    Map commoditiesList = new HashMap();
                    commoditiesList.put("commoditiesList", baloot.getCommodities());
                    return new SuccessfulResponse(commoditiesList);
                }
            case RATE_COMMODITY:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    Map<String, Object> parsedJsonData = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
                    });
                    if (parsedJsonData.get("username") instanceof String) {
                        String username = (String) parsedJsonData.get("username");
                        if (parsedJsonData.get("commodityId") instanceof Integer) {
                            int commodityId = (Integer) parsedJsonData.get("commodityId");
                            if (parsedJsonData.get("score") instanceof Integer) {
                                int score = (Integer) parsedJsonData.get("score");
                                if (score >= 1 && score <= 10) {
                                    baloot.rateCommodity(username, commodityId, score);
                                } else
                                    return new FailedResponse("Score must be an integer from 1 to 10!");
                            } else
                                return new FailedResponse("Score must be an integer!");
                        } else
                            return new FailedResponse("Commodity ID must be an integer!");
                    } else
                        return new FailedResponse("Username must be a string!");
                }
            case ADD_TO_BUY_LIST:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    Map<String, Object> parsedJsonData = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
                    });
                    String username = (String) parsedJsonData.get("username");
                    int commodityId = (Integer) parsedJsonData.get("commodityId");
                    return baloot.addToBuyList(username, commodityId);
                }
            case REMOVE_FROM_BUY_LIST:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    Map<String, Object> parsedJsonData = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
                    });
                    String username = (String) parsedJsonData.get("username");
                    int commodityId = (Integer) parsedJsonData.get("commodityId");
                    return baloot.removeFromBuyList(username, commodityId);
                }
            case GET_COMMODITY_BY_ID:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    Map<String, Integer> parsedJsonData = mapper.readValue(jsonData, new TypeReference<Map<String, Integer>>() {
                    });
                    int commodityId = parsedJsonData.get("id");
                    Commodity commodity = baloot.findCommodityById(commodityId);
                    CommodityByIdView result;
                    if (commodity != null) {
                        Provider provider = baloot.findProviderByProviderId(commodity.getProviderId());
                        if (provider == null)
                            return new FailedResponse("No provider found with this provider id!");
                        else {
                            result = new CommodityByIdView(commodity.getId(), commodity.getName(), provider.getName(),
                                    commodity.getPrice(), commodity.getCategories(), commodity.getRating());
                        }
                        return new SuccessfulResponse(result);
                    } else
                        return new FailedResponse("No commodity found with this commodity id!");
                }
            case GET_COMMODITIES_BY_CATEGORY:
                if (jsonData == null || jsonData.isEmpty()) {
                    return new FailedResponse("Please enter the user JSON data.");
                } else {
                    mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                    CategoryFilter filter = mapper.readValue(jsonData, CategoryFilter.class);
                    Map filteredCommoditiesList = new HashMap();
                    ArrayList<Commodity> filteredWithStock = filter.applyFilter(baloot.getCommodities());
                    ArrayList<CommodityNoInStock> filteredWithoutInStock = new ArrayList<CommodityNoInStock>();
                    for (Commodity commodity : filteredWithStock) {
                        CommodityNoInStock oneCommodityWithNoInStock = new CommodityNoInStock(commodity.getId(), commodity.getName(),
                                commodity.getProviderId(), commodity.getPrice(), commodity.getCategories(), commodity.getRating());
                        filteredWithoutInStock.add(oneCommodityWithNoInStock);
                    }
                    filteredCommoditiesList.put("commoditiesListByCategory", filteredWithoutInStock);
                    return new SuccessfulResponse(filteredCommoditiesList);
                }
            case GET_BUY_LIST:
                Map<String, String> parsedJsonData = mapper.readValue(jsonData, new TypeReference<Map<String, String>>() {
                });
                String username = parsedJsonData.get("username");
                ArrayList<CommodityNoInStock> userBuyList = baloot.getBuyList(username);
                if (userBuyList != null) {
                    Map result = new HashMap();
                    result.put("buyList", userBuyList);
                    return new SuccessfulResponse(result);
                } else
                    return new FailedResponse("No user found with this username!");
            default:
                return new FailedResponse("Wrong Command");
        }
    }
}