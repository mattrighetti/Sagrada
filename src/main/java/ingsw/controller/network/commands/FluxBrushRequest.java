package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

public class FluxBrushRequest extends MoveToolCardRequest {

    public Dice selectedDice;

    public int row;
    public int column;
    public  int phase;

    public FluxBrushRequest(){
        super((ToolCardType.FLUX_BRUSH));
        phase = 3;
    }

    public FluxBrushRequest(Dice selectedDice) {
        super(ToolCardType.FLUX_BRUSH);
        this.selectedDice = selectedDice;
        phase = 1;
    }

    public FluxBrushRequest(Dice selectedDice, int row, int column) {
        super(ToolCardType.FLUX_BRUSH);
        this.selectedDice = selectedDice;
        this.row = row;
        this.column = column;
        phase = 2;
    }
}
