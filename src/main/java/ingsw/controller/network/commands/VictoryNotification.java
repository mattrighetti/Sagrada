package ingsw.controller.network.commands;

public class VictoryNotification implements Response {
    public int totalScore;

    public VictoryNotification(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
