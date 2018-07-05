package ingsw.model;

import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.*;

/**
 * Class that contains all the static match element:
 * - Public objective cards
 * - Private objective cards
 * - Dicebag
 * - Drafted dice(the only fields that changes on every round)
 */
public class Board {

    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<Dice> diceBag;
    private List<Dice> draftedDice;

    /**
     * Creates a new Board
     * @param publicObjectiveCards Public objective cards to set
     * @param toolCards Tool cards to set
     */
    public Board(List<PublicObjectiveCard> publicObjectiveCards, List<ToolCard> toolCards) {
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
     *
     * @param noOfPlayers number of players
     * @return drafted dice
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

    /**
     * Returns the tool cards
     * @return Tool cards list
     */
    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * Updates the drafted dice
     * @param draftedDice New Drafted dice
     */
    void setDraftedDice(List<Dice> draftedDice){
        this.draftedDice = draftedDice;
    }

    /**
     * Draft one dice from the dice Bag
     * @return Dice drafted
     */
    Dice draftOneDice() {
        Collections.shuffle(diceBag);
        Dice dice = diceBag.get(0);
        diceBag.remove(0);
        dice.roll();
        return dice;
    }

    /**
     * Insert a dice into the dice bag
     * @param dice Dice to insert
     */
    void addDiceToBag(Dice dice) {
        diceBag.add(dice);
    }

    /**
     * Returns the drafted dice
     * @return Drafted dice
     */
    List<Dice> getDraftedDice() {
        return draftedDice;
    }

    /**
     * Returns the public objective cards
     * @return Public objective cards
     */
    public List<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }
}
