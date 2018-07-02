package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class CorkBackedStraightedgeRequest extends MoveToolCardRequest {
    public Dice selectedDice;
    public int row;
    public int column;

    public CorkBackedStraightedgeRequest(Dice selectedDice, int row, int column) {
        super(ToolCardType.CORK_BACKED_STRAIGHT_EDGE);
        this.selectedDice = selectedDice;
        this.row = row;
        this.column = column;
    }
}
