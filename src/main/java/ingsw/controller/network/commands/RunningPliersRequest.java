package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class RunningPliersRequest extends MoveToolCardRequest {
    public Dice selectedDice;
    public int rowIndex;
    public int ColumnIndex;

    public RunningPliersRequest(Dice selectedDice, int rowIndex, int columnIndex) {
        super(ToolCardType.RUNNING_PLIERS);
        this.selectedDice = selectedDice;
        this.rowIndex = rowIndex;
        ColumnIndex = columnIndex;
    }
}
