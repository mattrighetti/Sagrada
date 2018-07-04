package ingsw.controller.network.commands;

public class LogoutResponse implements Response {
    public final boolean logoutSuccessful;

    public LogoutResponse(boolean logoutSuccessful) {
        this.logoutSuccessful = logoutSuccessful;
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
