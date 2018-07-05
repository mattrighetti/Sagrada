package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.GrozingPliersResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class GrozingPliers extends ToolCard {

    private boolean allOne;
    private boolean allSix;

    /**
     * Creates a new GrozingPliers tool card
     */
    public GrozingPliers() {
        super("GrozingPliers");
    }

    /**
     * After drafting, increase or decrease the value of the drafted dice by one.
     * If in the drafted dice there are all "1" or all "6" the option not possible
     * is automatically excluded
     */
    @Override
    public void action(GameManager gameManager) {
        allOne = false;
        allSix = false;

        //Check if the drafted dice are all 6 or all 1
        for (Dice dice: gameManager.getDraftedDice()){
            if (dice.getFaceUpValue() == 6 && !allOne)
                allSix = true;
            else if (dice.getFaceUpValue() == 1 && !allSix)
                allOne = true;
            else {
                allOne = false;
                allSix = false;
                break;
            }
        }

        if (allOne && !allSix) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new GrozingPliersResponse("one"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (allSix && !allOne) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new GrozingPliersResponse("six"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new GrozingPliersResponse("none"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        waitForToolCardAction(gameManager);
        if (gameManager.getToolCardLock().get()) {
            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.grozingPliersResponse();
            gameManager.getToolCardLock().set(false);
            gameManager.getCurrentRound().toolCardMoveDone();
        }
    }
}
