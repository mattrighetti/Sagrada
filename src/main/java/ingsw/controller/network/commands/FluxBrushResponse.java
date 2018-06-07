package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;
import java.util.Map;

public class FluxBrushResponse extends UseToolCardResponse {

    public Map<String, Boolean[][]> availablePositions;
    public List<Dice> draftedDice;
    public Dice selectedDice;
    public int phase;

    public FluxBrushResponse(List<Dice> draftedDice, Dice selectedDice, Map<String, Boolean[][]> availablePositions) {
        super(ToolCardType.FLUX_BRUSH);
        this.selectedDice = selectedDice;
        this.availablePositions = availablePositions;
        this.draftedDice = draftedDice;
        phase = 2;
    }

    public FluxBrushResponse() {
        super(ToolCardType.FLUX_BRUSH);
        phase = 1;
    }
}
