package ingsw.controller.network.commands;

import ingsw.utilities.ToolCardType;

public class UseToolCardResponse implements Response {

    public ToolCardType toolCardType;

    public UseToolCardResponse(ToolCardType toolCardType) {
        this.toolCardType = toolCardType;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
