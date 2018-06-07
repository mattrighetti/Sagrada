package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;

public class DraftedDiceToolCardResponse extends UseToolCardResponse {

    public List<Dice> draftedDice;
    public boolean endTurn;

    public DraftedDiceToolCardResponse(List<Dice> draftedDice, boolean endTurn) {
        super(ToolCardType.DRAFT_POOL);
        this.draftedDice = draftedDice;
        this.endTurn = endTurn;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        super.handle(responseHandler);
    }
}
