package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.FluxBrushResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class FluxBrush extends ToolCard {

    public FluxBrush() {
        super("FluxBrush");
    }

    /**
     * After drafting, re-roll the drafted dice.
     * if it cannot be placed, return it to the Draft pool.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new FluxBrushResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
