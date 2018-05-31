package ingsw.controller.network.commands;

import ingsw.model.cards.toolcards.ToolCard;

public class UseToolCardRequest implements Request {
    public ToolCard toolCard;

    public UseToolCardRequest(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handle(this);
    }
}
