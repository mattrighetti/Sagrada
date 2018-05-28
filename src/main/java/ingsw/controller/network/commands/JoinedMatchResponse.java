package ingsw.controller.network.commands;

public class JoinedMatchResponse implements Response {
    public boolean isLoginSuccessful;

    public JoinedMatchResponse(boolean isLoginSuccessful) {
        this.isLoginSuccessful = isLoginSuccessful;
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
