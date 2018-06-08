package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;
import ingsw.utilities.Tuple;

public class TapWheelRequest extends MoveToolCardRequest {
    public boolean doubleMove;
    public int phase;
    public Dice dice;
    public Tuple dicePosition;
    public Tuple position;

    public TapWheelRequest(Dice dice, int phase) {
        super(ToolCardType.TAP_WHEEL);
        this.dice = dice;
        this.phase = phase;
    }

    public TapWheelRequest(Tuple dicePosition, Tuple position, int phase, boolean doubleMove) {
        super(ToolCardType.TAP_WHEEL);
        this.dicePosition = dicePosition;
        this.position = position;
        this.phase = phase;
        this.doubleMove = doubleMove;
    }

    public TapWheelRequest(int endTapWheel) {
        super(ToolCardType.TAP_WHEEL);
        this.phase = endTapWheel;
    }
}
