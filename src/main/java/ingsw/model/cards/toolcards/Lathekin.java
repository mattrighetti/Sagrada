package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.LathekinResponse;
import ingsw.model.GameManager;
import ingsw.model.cards.patterncard.PatternCard;

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

        for (int i = 0; i < 2; i++) {
            if(!gameManager.getdoubleMove()) {
                try {
                    PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
                    gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(patternCard.computeAvailablePositionsLathekin()));
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
                gameManager.lathekinResponse();
            } else
                gameManager.lathekinResponse();
        }

        gameManager.setDoubleMove(false);
        gameManager.getCurrentRound().hasMadeAMove();



    }
}
