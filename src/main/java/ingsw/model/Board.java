package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import java.util.ArrayList;
import java.util.Set;

public class Board {
    //TODO think how the Board should setup the match and decide which collection to use for sets

    private ArrayList<User> players;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;

    //-----METHODS-----

    //Get the Player No index
    public Player getPlayer(int index){ return players.get(index); }

    //Get a Public Card from the board
    //public PublicObjectiveCard getPublicObjectiveCard(){ return publicObjectiveCards.get(); }

}
