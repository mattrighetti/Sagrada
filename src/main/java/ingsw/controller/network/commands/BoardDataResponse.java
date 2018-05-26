package ingsw.controller.network.commands;

import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.List;
import java.util.Set;

public class BoardDataResponse implements Response {
    public List<Player> players;
    public Set<PublicObjectiveCard> publicObjectiveCards;
    public Set<ToolCard> toolCards;

    public BoardDataResponse(List<Player> players, Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards) {
        this.players = players;
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
    }

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
