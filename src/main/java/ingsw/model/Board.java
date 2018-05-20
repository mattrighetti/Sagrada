package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.*;

public class Board {
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;
    private List<Dice> diceBag;
    private List<Dice> draftedDice;

    public Board(Set<PublicObjectiveCard> publicObjectiveCards, Set<ToolCard> toolCards, List<Player> playerList) {
        this.publicObjectiveCards = publicObjectiveCards;
        this.toolCards = toolCards;
        setupDiceBag();
        draftedDice = new ArrayList<>();
    }

    /**
     * Sets up the dice bag with 18 dice per color
     */
    private void setupDiceBag() {
        diceBag = new ArrayList<>();
        EnumSet.allOf(Color.class).stream().filter(color -> color != Color.BLANK).forEach(x -> {
            for (int i = 0; i < 18; i++) {
                diceBag.add(new Dice(x));
            }
        });
    }

    protected List<Dice> draftDice() {
        Collections.shuffle(diceBag);
        for (int i = 0; i < 5; i++) {
            diceBag.get(i).roll();
            draftedDice.add(diceBag.get(i));
            diceBag.remove(i);
        }
        return draftedDice;
    }

    public List<Dice> getDraftedDice() {
        return draftedDice;
    }
}
