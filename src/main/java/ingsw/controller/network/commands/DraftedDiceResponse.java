package ingsw.controller.network.commands;

import ingsw.model.Dice;

import java.util.List;

public class DraftedDiceResponse implements Response {
    public List<Dice> dice;

    public DraftedDiceResponse(List<Dice> dice) {
        this.dice = dice;
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
