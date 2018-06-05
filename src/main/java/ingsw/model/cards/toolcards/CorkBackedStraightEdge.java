package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.CorkBackedStraightedgeResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class CorkBackedStraightEdge extends ToolCard {

    public CorkBackedStraightEdge() {
        super("CorkBackedStraightEdge");
    }

    /**
     * After drafting, place the dice in a spot that is not adjacent to another dice.
     * You must obey all other placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new CorkBackedStraightedgeResponse());
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

        gameManager.corkBackedStraightedgeResponse();
    }
}
