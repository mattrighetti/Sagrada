package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;

public class FluxRemover extends ToolCard {

    public FluxRemover() {
        super("FluxRemover");
    }

    /**
     * After drafting,return the die to the Dice Bag and pull one die from the bag.
     * Choose a value and place the new dice, obeying all the placement restrictions, or return it to the Draft pool.
     */
    @Override
    public void action(GameManager gameManager) {

    }
}
