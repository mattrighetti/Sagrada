package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.FluxRemoverResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

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
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxRemoverResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        waitForToolCardAction(gameManager);

        gameManager.fluxRemoverResponse();
        gameManager.getCurrentRound().hasMadeAMove();
    }
}
