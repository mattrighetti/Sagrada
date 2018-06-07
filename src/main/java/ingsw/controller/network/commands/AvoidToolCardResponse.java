package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class AvoidToolCardResponse extends UseToolCardResponse {

    public AvoidToolCardResponse() {
        super(ToolCardType.AVOID_USE);
    }
}
