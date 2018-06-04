package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.*;

public class Board {
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<Dice> diceBag;
    private List<Dice> draftedDice;

    public Board(List<PublicObjectiveCard> publicObjectiveCards, List<ToolCard> toolCards, List<Player> playerList) {
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

    /**
     * Method that drafts five dice from the diceBag
     * @return
     * @param noOfPlayers number of players
     */
    protected List<Dice> draftDice(int noOfPlayers) {
        draftedDice = new ArrayList<>();
        Collections.shuffle(diceBag);
        for (int i = 0; i < (noOfPlayers * 2) + 1; i++) {
            Dice dice = diceBag.get(0);
            dice.roll();
            draftedDice.add(dice);
            diceBag.remove(0);
            System.out.println("Drafted dice");
        }
        return draftedDice;
    }

    public Dice draftOneDice(){
        Collections.shuffle(diceBag);
        Dice dice = diceBag.get(0);
        diceBag.remove(0);
        dice.roll();
        draftedDice.add(dice);
        return dice;
    }

    public List<Dice> getDraftedDice() {
        return draftedDice;
    }
}
