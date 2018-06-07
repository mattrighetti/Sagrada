package ingsw.model.cards.toolcards;

import com.sun.org.apache.xpath.internal.operations.Bool;
import ingsw.controller.network.commands.CorkBackedStraightedgeResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;
import java.util.Map;

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
            Map<String,Boolean[][]> availablePositions = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsNoDiceAround(gameManager.getDraftedDice());
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new CorkBackedStraightedgeResponse(availablePositions));
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
