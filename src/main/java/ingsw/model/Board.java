package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Board {
    private String name;
    private ArrayList<Player> players;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;

    public Board(Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards, ArrayList<Player> playerList) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.players = playerList;

    }


    //-----METHODS-----

    //Get the Player No. index
    public User getPlayer(int index){ return players.get(index); }
}
