package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.LathekinResponse;
import ingsw.model.GameManager;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.patterncard.PatternCard;

import java.rmi.RemoteException;
import java.util.List;

public class Lathekin extends ToolCard {

    private List<List<Box>> oldGrid;
    private List<List<Box>> newGrid;

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
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new LathekinResponse(gameManager.getCurrentRound().getCurrentPlayer().getPlayerUsername(), gameManager.getCurrentRound().getCurrentPlayer().getPatternCard(), patternCard.computeAvailablePositions(), false));
            System.out.println("sending data for the first lathekin move");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("thread goes in wait 1");
        waitForToolCardAction(gameManager);

        if (resetValues(gameManager)) return;

        System.out.println("thread is now awake 1");


        if (!gameManager.getdoubleMove()) {
            System.out.println("thread goes in wait 2");
            waitForToolCardAction(gameManager);

            if (resetValues(gameManager)) return;

            System.out.println("thread is now awake 2");

            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.lathekinResponse();

        } else {
            System.out.println("Double move done");
            gameManager.getCurrentRound().getCurrentPlayer().decreaseFavorTokens(getPrice());
            gameManager.lathekinResponse();
            gameManager.setDoubleMove(false);
            gameManager.getToolCardLock().set(false);
            setNewGrid(null);
            setOldGrid(null);
            return;
        }

        gameManager.getCurrentRound().toolCardMoveDone();
        gameManager.setDoubleMove(false);
        gameManager.getToolCardLock().set(false);
        setNewGrid(null);
        setOldGrid(null);
        System.out.println("end Lathekin");
    }

    private boolean resetValues(GameManager gameManager) {
        if (!gameManager.getToolCardLock().get()) {
            gameManager.setDoubleMove(false);
            gameManager.getToolCardLock().set(false);
            setNewGrid(null);
            setOldGrid(null);
            return true;
        }
        return false;
    }

    public List<List<Box>> getOldGrid() {
        return oldGrid;
    }

    public void setOldGrid(List<List<Box>> oldGrid) {
        this.oldGrid = oldGrid;
    }

    public List<List<Box>> getNewGrid() {
        return newGrid;
    }

    public void setNewGrid(List<List<Box>> newGrid) {
        this.newGrid = newGrid;
    }
}
