package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class GrindingStoneRequest extends MoveToolCardRequest {

    public Dice selectedDice;

    public GrindingStoneRequest(Dice selectedDice) {
        super(ToolCardType.GRINDING_STONE);
        this.selectedDice = selectedDice;
    }
}
