package org.iespring1402.response;

public class FailedResponse extends Response {

    public FailedResponse() {
        super(false, "An error Occurred!");
    }

    public FailedResponse(String message) {
        super(false, message);
    }
}
