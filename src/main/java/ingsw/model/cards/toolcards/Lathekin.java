package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
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

        if (gameManager.getCurrentRound().getCurrentPlayer().getPatternCard().getNoOfDice() < 2) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse(gameManager.getCurrentRound().getCurrentPlayer().getFavourTokens()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            gameManager.getCurrentRound().toolCardMoveDone();
            return;
        }


        if (!gameManager.getdoubleMove()) {
            try {
                PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(patternCard, patternCard.computeAvailablePositionsLathekin()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            waitForToolCardAction(gameManager);
            gameManager.lathekinResponse();
        } else {
            gameManager.lathekinResponse();
            return;
        }

        try {
            PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(patternCard, patternCard.computeAvailablePositions()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        waitForToolCardAction(gameManager);
        gameManager.lathekinResponse();


        gameManager.getCurrentRound().toolCardMoveDone();
        gameManager.setDoubleMove(false);
    }
}
