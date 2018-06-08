package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.EglomiseBrushResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class EglomiseBrush extends ToolCard {

    public EglomiseBrush() {
        super("EglomiseBrush");
    }

    /**
     * Move any one die your window ignoring the color restrictions.
     * You must obey all other placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new EglomiseBrushResponse(gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().computeAvailablePositionsNoColor()));
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

        gameManager.eglomiseBrushResponse();
        gameManager.getCurrentRound().hasMadeAMove();
    }
}
