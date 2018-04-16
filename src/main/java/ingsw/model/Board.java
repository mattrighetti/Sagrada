package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import java.util.List;
import java.util.Set;

public class Board {
    private String name;
    private List<Player> players;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;

    public Board(Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards, List<Player> playerList) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.players = playerList;
    }


    //-----METHODS-----

    //Get the Player No. index
    public Player getPlayer(int index){
        return players.get(index);
    }
}
