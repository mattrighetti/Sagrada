package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class GrozingPliersRequest extends MoveToolCardRequest {
    public Dice selectedDice;
    public Boolean increase;

    public GrozingPliersRequest(Dice selectedDice, Boolean increase) {
        super(ToolCardType.GROZING_PLIERS);
        this.selectedDice = selectedDice;
        this.increase = increase;
    }
}
