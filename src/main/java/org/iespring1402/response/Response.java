package org.iespring1402.response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    public boolean success;
    public Object data;

    public Response(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public static void printSerializedRes(Response response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(response);
        System.out.println(jsonResponse);
    }
}
