package ingsw.controller.network.commands;

public class JoinedMatchResponse implements Response {
    public boolean isLoginSuccessful;

    public JoinedMatchResponse(boolean isLoginSuccessful) {
        this.isLoginSuccessful = isLoginSuccessful;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
