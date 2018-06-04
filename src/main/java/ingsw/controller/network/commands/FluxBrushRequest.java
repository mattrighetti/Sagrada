package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class FluxBrushRequest extends MoveToolCardRequest {

    public Dice selectedDice;

    public FluxBrushRequest(Dice selectedDice) {
        super(ToolCardType.FLUX_BRUSH);
        this.selectedDice = selectedDice;
    }
}
