package org.iespring1402.response;

public class SuccessfulResponse extends Response {

    public SuccessfulResponse() {
        super(true, "Your request was successfully done");
    }

    public SuccessfulResponse(Object data) {
        super(true, data);
    }
}
