package ingsw.utilities;

import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to send Response to more than one Player.
 */
public class PlayerBroadcaster {
    private static final String ERROR_MESSAGE = "Broadcaster is not active";
    private boolean isBroadcasterActive;
    private List<Player> players;

    /**
     * Set the Users' List and activate the broadcaster
     * @param players List of Players
     */
    public PlayerBroadcaster(List<Player> players) {
        this.players = players;
        isBroadcasterActive = true;
    }

    /**
     * Enable the broadcaster
     */
    public void enableBroadcaster() {
        System.out.println("Activating playerBroadcaster");
        isBroadcasterActive = true;
    }

    /**
     * Disable the broadcaster
     */
    public void disableBroadcaster() {
        System.out.println("Shutting down playerBroadcaster");
        isBroadcasterActive = false;
    }


    /**
     * Creates a List of the active player by calling <code>player.getCurrentUser.getUserObserver().checkIfActive()</code> excluding the one passed as parameter
     * @param usernameToExclude User that won't receive the message
     * @return Active UserObserver
     */
    private List<UserObserver> playersToBroadcast(String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : players) {
            if (!player.getUser().getUsername().equals(usernameToExclude) && player.getUser().isActive()) {
                try {
                    player.getUserObserver().checkIfActive();
                    playerListToBroadcast.add(player.getUser().getUserObserver());
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + player.getPlayerUsername() + " is not active, deactivating user");
                    player.getUser().setActive(false);
                }
            }
        }
        return playerListToBroadcast;
    }

    /**
     * Creates a List of the active player by calling <code>player.getCurrentUser.getUserObserver().checkIfActive()</code> excluding the one passed as parameter
     * @return Active UserObserver
     */
    private List<UserObserver> playersToBroadcast() {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : players) {
            if (player.getUser().isActive()) {
                try {
                    player.getUserObserver().checkIfActive();
                    playerListToBroadcast.add(player.getUser().getUserObserver());
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + player.getPlayerUsername() + " is not active, deactivating user");
                }
            }
        }
        return playerListToBroadcast;
    }

    /**
     * Used when Dice are drafted
     * @param usernameToExclude Player to exclude
     * @param dice List of drafted dice
     */
    public void broadcastResponse(String usernameToExclude, List<Dice> dice) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast(usernameToExclude)) {
                try {
                    userObserver.sendResponse(new DraftedDiceResponse(dice));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }


    /**
     * Used when Dice are drafted
     * @param dice List of drafted dice
     */
    public void broadcastResponseToAll(List<Dice> dice) {
        if (isBroadcasterActive) {
            for (UserObserver player : playersToBroadcast()) {
                try {
                    player.sendResponse(new DraftedDiceResponse(dice));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }

    /**
     * Broadcast a generic response to all the active players
     * @param response Response to send
     */
    public void broadcastResponseToAll(Response response) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast()) {
                try {
                    userObserver.sendResponse(response);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }

    /**
     * Used when the histury are updated
     * @param movesHistory movehistory to send
     */
    public void updateMovesHistory(List<MoveStatus> movesHistory) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast()) {
                try {
                    userObserver.sendResponse(new MoveStatusNotification(movesHistory));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println(ERROR_MESSAGE);
    }
}
