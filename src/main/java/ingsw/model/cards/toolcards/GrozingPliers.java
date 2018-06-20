package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.GrozingPliersResponse;
import ingsw.model.Dice;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class GrozingPliers extends ToolCard {

    public GrozingPliers() {
        super("GrozingPliers");
    }

    /**
     * After drafting, increase or decrease the value of the drafted dice by one
     */
    @Override
    public void action(GameManager gameManager) {
        boolean allOne = false;
        boolean allSix = false;

        for (Dice dice: gameManager.getDraftedDice()){
            if (dice.getFaceUpValue() == 6)
                allSix = true;
            else if (dice.getFaceUpValue() == 1)
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
        gameManager.grozingPliersResponse();
    }
}
