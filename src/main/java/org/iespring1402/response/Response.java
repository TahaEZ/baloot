package org.iespring1402.response;

public class Response {
    public boolean success;
    public String data;

    public Response(boolean success, String data) {
        this.success = success;
        this.data = data;
    }
}
