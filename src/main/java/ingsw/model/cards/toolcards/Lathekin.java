package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.GrozingPliersResponse;
import ingsw.controller.network.commands.LathekinResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class Lathekin extends ToolCard {

    public Lathekin() {
        super("Lathekin");
    }

    /**
     * Move exactly two dice, obeying all the placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {
        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            synchronized (gameManager.toolCardLock) {
                try {
                    gameManager.toolCardLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        gameManager.LathekinResponse();
    }
}
