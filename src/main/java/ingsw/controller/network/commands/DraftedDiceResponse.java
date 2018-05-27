package ingsw.controller.network.commands;

import ingsw.model.Dice;

import java.util.List;

public class DraftedDiceResponse implements Response {
    public List<Dice> dice;

    public DraftedDiceResponse(List<Dice> dice) {
        this.dice = dice;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
