package ingsw.controller.network.commands;

import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.List;

public class BoardDataResponse implements Response {
    public List<Player> players;
    public List<PublicObjectiveCard> publicObjectiveCards;
    public List<ToolCard> toolCards;
    public List<List<Dice>> roundTrack;

    public BoardDataResponse(List<Player> players,
                             List<PublicObjectiveCard> publicObjectiveCards,
                             List<ToolCard> toolCards,
                             List<List<Dice>> roundTrack) {
        this.players = players;
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.roundTrack = roundTrack;
    }

    /**
     * Method that declares by which this response should be handled
     * @param responseHandler class that handles the Request
     */
    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.handle(this);
    }
}
