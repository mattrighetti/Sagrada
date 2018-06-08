package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.TapWheelResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class TapWheel extends ToolCard {

    public TapWheel() {
        super("TapWheel");
    }

    /**
     * Move up to two dice of the same color that match the color of a dice on the Round Track.
     * You must obey all the placement restrictions.
     */
    @Override
    public void action(GameManager gameManager) {

        try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new TapWheelResponse(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            if (!gameManager.getdoubleMove()) {
                synchronized (gameManager.toolCardLock) {
                    try {
                        gameManager.toolCardLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else break;
        }

        System.out.println("end tapwheel");
    }
}
