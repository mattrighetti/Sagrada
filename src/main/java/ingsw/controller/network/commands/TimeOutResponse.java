package ingsw.controller.network.commands;


import ingsw.model.Dice;
import ingsw.model.Player;

import java.util.List;

public class TimeOutResponse implements Response {

    public boolean toolCardMoveActive;
    public List<Dice> draftedDice;
    public List<List<Dice>> roundTrack;
    public Player currentPlayer;

    public TimeOutResponse() {
        toolCardMoveActive = false;
    }

    public TimeOutResponse(List<Dice> draftedDice, List<List<Dice>> roundTrack, Player currentPlayer) {
        toolCardMoveActive = true;

        this.draftedDice = draftedDice;
        this.roundTrack = roundTrack;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
