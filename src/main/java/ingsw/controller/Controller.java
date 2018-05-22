package ingsw.controller;

import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private String matchName;
    private GameManager gameManager;
    private List<Player> playerList;
    private int generalCounter;

    public Controller(String matchName) throws RemoteException {
        super();
        this.matchName = matchName;
        playerList = new ArrayList<>();
    }

    public String getMatchName() {
        return matchName;
    }

    public int getConnectedUsers() {
        return playerList.size();
    }

    /**
     * Wait for new Users who connect and want to enter the match and build the list of the match players
     * When all the players are connected, start the match.
     * @param user the user wants to join the match
     * @param remoteView the user's remoteView, will be used to send messages to the player (client)
     */
    public void loginUser(User user) {
        playerList.add(new Player(user));
        if (playerList.size() == 4) createMatch();
    }

    /**
     *Set the match: create a new instance of gameManager (who will handle the match)
     * Start the first phase of the match, the PatternCards choice
     */
    private void createMatch() {
        gameManager = new GameManager(playerList);
        gameManager.waitForEveryPatternCard();
    }

    /**
     * Assigns PatternCard to specified Player
     * Triggered by Command(PatterCard, String)
     * @param patternCard the card choosen by the player
     * @param username the player's username who choose the PatternCard
     */
    public synchronized PatternCard assignPatternCard(String username, PatternCard patternCard) {
       return gameManager.setPatternCardForPlayer(username, patternCard);
    }

    /**
     * After the first player of the round chooses "Draft Dice" on the View
     * this method is triggered to draft the dice calling the gameManager method
     */
    public void draftDice() {
        gameManager.draftDiceFromBoard();
    }

    public void toolCardMove(Player player, ToolCard toolCard) {
        gameManager.useToolCard(player, toolCard);
    }


}