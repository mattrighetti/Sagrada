package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import java.util.ArrayList;
import java.util.Set;

public class Board {
    private String name;
    private ArrayList<User> players;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;

    public Board(String name) {
        this.name = name;
    }

    public Board(Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;

    }


    //-----METHODS-----

    //Get the Player No. index
    public User getPlayer(int index){ return players.get(index); }
}
