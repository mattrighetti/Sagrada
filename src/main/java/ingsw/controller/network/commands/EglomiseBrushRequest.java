package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;
import ingsw.utilities.Tuple;

public class EglomiseBrushRequest extends MoveToolCardRequest {
    public Tuple dicePosition;
    public Tuple position;

    public EglomiseBrushRequest(Tuple dicePosition, Tuple position) {
        super(ToolCardType.EGLOMISE_BRUSH);
        this.dicePosition = dicePosition;
        this.position = position;
    }
}
