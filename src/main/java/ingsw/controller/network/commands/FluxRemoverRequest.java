package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import static ingsw.utilities.ToolCardType.FLUX_REMOVER;

public class FluxRemoverRequest extends MoveToolCardRequest {

    public Dice selectedDice;

    public FluxRemoverRequest(Dice selectedDice) {
        super(ToolCardType.FLUX_REMOVER);
        this.selectedDice = selectedDice;
    }
}
