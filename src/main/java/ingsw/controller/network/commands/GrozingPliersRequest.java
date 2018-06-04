package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class GrozingPliersRequest extends MoveToolCardRequest {

    public Dice selectedDice;
    public Boolean increase;

    public GrozingPliersRequest(Dice selectedDice, Boolean increase) {
        super(ToolCardType.FLUX_BRUSH);
        this.selectedDice = selectedDice;
        this.increase = increase;
    }
}
