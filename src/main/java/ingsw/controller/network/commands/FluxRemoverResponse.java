package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;
import java.util.Map;

public class FluxRemoverResponse extends UseToolCardResponse {
    public int phase;
    public Dice draftedDie;
    public Map<String,Boolean[][]> availablePositions;
    public List<Dice> draftedDice;

    public FluxRemoverResponse(List<Dice> draftedDice, Dice dice, Map<String,Boolean[][]> availablePositions) {
        super(ToolCardType.FLUX_REMOVER);
        this.availablePositions = availablePositions;
        this.draftedDice = draftedDice;
        draftedDie = dice;
        phase = 3;
    }

    public FluxRemoverResponse() {
        super(ToolCardType.FLUX_REMOVER);
        phase = 1;
    }

    public FluxRemoverResponse(Dice draftedDie) {
        super(ToolCardType.FLUX_REMOVER);
        this.draftedDie = draftedDie;
        phase = 2;
    }
}
