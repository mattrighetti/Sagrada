package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.utilities.ToolCardType;

import java.util.List;

public class DraftPoolResponse extends UseToolCardResponse {

    public List<Dice> draftedDice;

    public DraftPoolResponse(List<Dice> draftedDice) {
        super(ToolCardType.DRAFT_POOL);
        this.draftedDice = draftedDice;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        super.handle(responseHandler);
    }
}
