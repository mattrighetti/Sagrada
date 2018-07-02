package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;
import ingsw.utilities.Tuple;

public class LathekinRequest extends MoveToolCardRequest {
    public Tuple dicePosition;
    public Tuple position;
    public boolean doubleMove;

    public LathekinRequest(Tuple dicePosition, Tuple position, boolean doubleMove) {
        super(ToolCardType.LATHEKIN);
        this.dicePosition = dicePosition;
        this.position = position;
        this.doubleMove = doubleMove;
    }
}
