package ingsw.model.cards.toolcards;

import ingsw.controller.network.commands.AvoidToolCardResponse;
import ingsw.controller.network.commands.RunningPliersResponse;
import ingsw.model.GameManager;

import java.rmi.RemoteException;

public class RunningPliers extends ToolCard {

    public RunningPliers() {
        super("RunningPliers");
    }

    /**
     * After your first turn, immediately draft a dice.
     * Skip your next turn this round.
     */
    @Override
    public void action(GameManager gameManager) {
        if (gameManager.getTurnInRound() == 1) {
            try {
                gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new RunningPliersResponse());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            waitForToolCardAction(gameManager);

            if (gameManager.toolCardLock.get()) {
                gameManager.getCurrentRound().blockedTurnPlayers.add(gameManager.getCurrentRound().getCurrentPlayer().getPlayerUsername());
                gameManager.runningPliersResponse();
                gameManager.getCurrentRound().toolCardMoveDone();
                gameManager.toolCardLock.set(false);
            }
        } else try {
            gameManager.getCurrentRound().getCurrentPlayer().getUserObserver().sendResponse(new AvoidToolCardResponse());
            gameManager.toolCardLock.set(false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
