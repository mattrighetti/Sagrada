package ingsw.controller.network.commands;

import ingsw.model.Dice;

import java.util.List;

public class RoundTrackNotification implements Response {
    public List<Dice> roundTrack;

    public RoundTrackNotification(List<Dice> roundTrack) {
        this.roundTrack = roundTrack;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
