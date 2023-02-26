package org.iespring1402;

import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;

import java.util.Scanner;

public class Main {
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

    public static void main(String[] args) {
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

            runCommand(command, jsonData);
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

    static Response runCommand(String command, String jsonData) {
        Response response = new FailedResponse();
        switch (command) {
            case ADD_USER:
                // TODO: Add User Command
                break;
            case ADD_PROVIDER:
                // TODO: Add Provider Command
                break;
            case ADD_COMMODITY:
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
        return response; // TODO: to be removed (we return in each case statements)
    }
}