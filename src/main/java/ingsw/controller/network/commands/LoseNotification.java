package ingsw.controller.network.commands;

public class LoseNotification implements Response {
    public int totalScore;

    public LoseNotification(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
