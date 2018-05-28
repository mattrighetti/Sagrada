package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.model.Player;

public class DiceMoveResponse implements Response {
    public Player player;
    public Dice dice;
    public int rowIndex;
    public int columnIndex;

    public DiceMoveResponse(Player player, Dice dice, int rowIndex, int columnIndex) {
        this.player = player;
        this.dice = dice;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
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
