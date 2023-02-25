package org.iespring1402;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iespring1402.response.FailedResponse;
import org.iespring1402.response.Response;
import org.iespring1402.response.SuccessfulResponse;

public class Main {
    public static void main(String[] args) {
        Response response = new FailedResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String stringResponse = mapper.writeValueAsString(response);
            System.out.println(stringResponse);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}