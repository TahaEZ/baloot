package org.iespring1402.response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class Response {
    public boolean success;
    public Object data;

    public Response(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }


    public static void printSerializeRes(Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
