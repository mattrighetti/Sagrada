package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import static ingsw.utilities.ToolCardType.FLUX_REMOVER;

public class FluxRemoverRequest extends MoveToolCardRequest {

    public Dice selectedDice;
    public int chosenValue;
    public int phase;
    public int rowIndex;
    public int columnIndex;

    public FluxRemoverRequest(Dice selectedDice, int rowIndex, int columnIndex) {
        super(FLUX_REMOVER);
        this.selectedDice = selectedDice;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        phase = 3;
    }

    public FluxRemoverRequest() {
        super(FLUX_REMOVER);
        phase = 4;
    }

    public FluxRemoverRequest(Dice selectedDice) {
        super(ToolCardType.FLUX_REMOVER);
        this.selectedDice = selectedDice;
        phase = 1;
    }

    public FluxRemoverRequest(Dice dice, int chosenValue) {
        super(FLUX_REMOVER);
        this.selectedDice = dice;
        this.chosenValue = chosenValue;
        phase = 2;
    }

}
