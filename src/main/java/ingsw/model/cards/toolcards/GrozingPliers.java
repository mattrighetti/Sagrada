package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.GrozingPliersResponse;
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
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new GrozingPliersResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        synchronized (gameManager.toolCardLock) {
            try {
                gameManager.toolCardLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        gameManager.grozingPliersResponse();
    }
}
