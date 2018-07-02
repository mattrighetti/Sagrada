package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class AvoidToolCardResponse extends UseToolCardResponse {
    public int playerTokens;

    public AvoidToolCardResponse() {
        super(ToolCardType.AVOID_USE);
        playerTokens =  - 1;
    }

    public AvoidToolCardResponse( int playerTokens) {
        super(ToolCardType.AVOID_USE);
        this.playerTokens = playerTokens;
    }
}
