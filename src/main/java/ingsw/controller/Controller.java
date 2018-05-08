package ingsw.controller;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.RequestHandler;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private GameManager gameManager;
    private List<Player> playerList;
    private int generalCounter;

    public Controller() throws RemoteException {
        super();
        playerList = new ArrayList<>();
    }

    public void loginUser(User user, RemoteView remoteView) {
        playerList.add(new Player(user, remoteView));
        if (playerList.size() == 4) createMatch();
    }

    private void createMatch() {
        gameManager = new GameManager(playerList);
        gameManager.waitForEveryPatternCard();
    }

    /**
     * Assigns PatternCard to specified Player
     * Triggered by Command(PatterCard, String)
     * @param patternCard
     * @param username
     */
    public synchronized PatternCard assignPatternCard(String username, PatternCard patternCard) {
       return gameManager.setPatternCardForPlayer(username, patternCard);
    }

    public void draftDice() {
        gameManager.draftDiceFromBoard();
    }


}
