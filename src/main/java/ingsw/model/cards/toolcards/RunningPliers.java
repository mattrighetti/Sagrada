package ingsw.model.cards.toolcards;

import ingsw.model.GameManager;

public class RunningPliers extends ToolCard {

    public RunningPliers() {
        super("RunningPliers");
    }

    /**
     * After your first turn, immediately draft a dice.
     * Skip your next turn this round.
     */
    @Override
    public void action(GameManager gameManager) {

    }
}
