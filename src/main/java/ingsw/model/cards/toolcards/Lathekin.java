package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.LathekinResponse;
import ingsw.model.GameManager;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.RemoteException;
import java.util.List;

public class Lathekin extends ToolCard {

    private List<List<Box>> patternCardGrid;

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


        try {
            PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(gameManager.getCurrentRound().getCurrentPlayer(), patternCard.computeAvailablePositions(), false));
            System.out.println("sending data for the first lathekin move");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("thread goes in wait 1");
        waitForToolCardAction(gameManager);

        if (!gameManager.toolCardLock.get())
            return;

        System.out.println("thread is now awake 1");


        if (!gameManager.getdoubleMove()) {
            try {
                PatternCard patternCard = gameManager.getCurrentRound().getCurrentPlayer().getPatternCard();
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(gameManager.getCurrentRound().getCurrentPlayer(), patternCard.computeAvailablePositionsLathekin(), true));
                System.out.println("sending data for the second lathekin move");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("thread goes in wait 2");
            waitForToolCardAction(gameManager);

            if (!gameManager.toolCardLock.get())
                return;

            System.out.println("thread is now awake 2");
            gameManager.lathekinResponse();
        } else {
            System.out.println("Double move done");
            gameManager.lathekinResponse();
            return;
        }

        gameManager.getCurrentRound().toolCardMoveDone();
        gameManager.setDoubleMove(false);
        gameManager.toolCardLock.set(false);
    }

    public List<List<Box>> getPatternCardGrid() {
        return patternCardGrid;
    }

    public void setPatternCardGrid(List<List<Box>> patternCardGrid) {
        this.patternCardGrid = patternCardGrid;
    }
}
