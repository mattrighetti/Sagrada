package ingsw.controller;

import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.ControllerTimer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private String matchName;
    private GameManager gameManager;
    private List<Player> playerList;
    private ControllerTimer controllerTimer;

    public Controller(String matchName) throws RemoteException {
        super();
        this.matchName = matchName;
        this.controllerTimer = new ControllerTimer(this);
        playerList = new ArrayList<>();
    }

    public String getMatchName() {
        return matchName;
    }

    public int getConnectedUsers() {
        return playerList.size();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Wait for new Users who connect and want to enter the match and build the list of the match players
     * When all the players are connected, start the match.
     *
     * @param user the user wants to join the match
     */
    public void loginUser(User user) {
        playerList.add(new Player(user));
        if (playerList.size() == 1) {
            controllerTimer.startLoginTimer(2);
        }

        if (playerList.size() == 4) {
            controllerTimer.cancelTimer();
            createMatch();
        }
    }

    /**
     * Set the match: create a new instance of gameManager (who will handle the match)
     * Start the first phase of the match, the PatternCards choice
     */
    public void createMatch() {
        gameManager = new GameManager(playerList);
        gameManager.pickPatternCards();
        gameManager.waitForEveryPatternCard();
    }

    @Override
    public void endTurn() throws RemoteException{
        gameManager.endTurn();
    }

    /**
     * Assigns PatternCard to specified Player
     * Triggered by Command(PatterCard, String)
     *
     * @param username    the player's username who choose the PatternCard
     * @param patternCard the card choosen by the player
     */
    @Override
    public synchronized void assignPatternCard(String username, PatternCard patternCard) throws RemoteException {
        gameManager.setPatternCardForPlayer(username, patternCard);
        for (Player player : playerList) {
            if(player.getPlayerUsername().equals(username))
                if (player.getPatternCard() != null && !player.getPatternCard().equals(patternCard))
                    throw new RemoteException("Pattern card not assigned correctly");
        }
    }


    /**
     * After the first player of the round chooses "Draft Dice" on the View
     * this method is triggered to draft the dice calling the gameManager method
     */
    @Override
    public void draftDice(String username) throws RemoteException {
        gameManager.draftDiceFromBoard();
    }

    @Override
    public void sendAck() throws RemoteException {
        gameManager.receiveAck();
    }

    public void toolCardMove(Player player, ToolCard toolCard) {
        gameManager.useToolCard(player, toolCard);
    }


}