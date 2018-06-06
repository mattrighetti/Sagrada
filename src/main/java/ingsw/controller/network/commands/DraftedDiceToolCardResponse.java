package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;

public class DraftedDiceToolCardResponse extends UseToolCardResponse {

    public List<Dice> draftedDice;

    public DraftedDiceToolCardResponse(List<Dice> draftedDice) {
        super(ToolCardType.DRAFT_POOL);
        this.draftedDice = draftedDice;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        super.handle(responseHandler);
    }
}
