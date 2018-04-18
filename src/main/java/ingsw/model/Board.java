package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private String name;
    private List<Player> players;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;
    private static Set<Dice> diceBag;

    public Board(Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards, List<Player> playerList) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        this.players = playerList;
        setupDiceBag();
    }

    /**
     * Sets up the dice bag with 18 dice per color
     */
    private void setupDiceBag() {
        diceBag = new HashSet<>();
        EnumSet.allOf(Color.class).stream().filter(x -> x != Color.BLANK).forEach(x -> {
            for (int i = 0; i < 18; i++) {
                diceBag.add(new Dice(x));
            }
        });
    }


    //-----METHODS-----

    //Get the Player No. index
    public Player getPlayer(int index) {
        return players.get(index);
    }
}
