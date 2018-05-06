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
    Map<String, RequestHandler> serverSideControllers;
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
    }

    private List<UserObserver> playerToBroadcast(String username) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : playerList) {
            if (!player.getUser().getUsername().equals(username)) {
                playerListToBroadcast.add(player.getUser().getUserObserver());
            }
        }
        return playerListToBroadcast;
    }

    /**
     * Assigns PatternCard to specified Player
     * Triggered by Command(PatterCard, String)
     * @param patternCard
     * @param username
     */
    public synchronized PatternCard assignPatternCard(PatternCard patternCard, String username) {
        for (Player player: gameManager.getPlayerList()) {
            if (player.getUser().getUsername().equals(username)) {
                player.setPatternCard(patternCard);
                if (++generalCounter == 4) {
                    broadcastMessage(new Message(username,
                            "Tutti gli utenti hanno scelto la PatternCard. Utente "
                            + playerList.get(0).getUser().getUsername() + " deve pescare i dadi"));
                    // TODO gestisci invio a tutti tranne che all'ultimo player che ha scelto
                }
                return player.getPatternCard();
            }
        }
        return null;
    }

    public List<Dice> draftDice() {
        return gameManager.draftDiceFromBoard();
    }

    public void broadcastMessage(Message message) {
        for (UserObserver userObserver : playerToBroadcast(message.sender)) {
            userObserver.sendMessage(message);
        }
    }
}
