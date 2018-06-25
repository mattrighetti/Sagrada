package ingsw.utilities;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PlayerBroadcaster {
    private boolean isBroadcasterActive;
    private List<Player> players;

    public PlayerBroadcaster(List<Player> players) {
        this.players = players;
        isBroadcasterActive = true;
    }

    public void enableBroadcaster() {
        System.out.println("Activating playerBroadcaster");
        isBroadcasterActive = true;
    }

    public void disableBroadcaster() {
        System.out.println("Shutting down playerBroadcaster");
        isBroadcasterActive = false;
    }


    private List<UserObserver> playersToBroadcast(String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : players) {
            if (!player.getUser().getUsername().equals(usernameToExclude) && player.getUser().isActive()) {
                try {
                    player.getUserObserver().checkIfActive();
                } catch (RemoteException e) {
                    System.err.println("RMI Player " + player.getPlayerUsername() + " is not active, deactivating user");
                }
                playerListToBroadcast.add(player.getUser().getUserObserver());
            }
        }
        return playerListToBroadcast;
    }

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

    public void broadcastResponse(String usernameToExclude, List<Dice> dice) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast(usernameToExclude)) {
                try {
                    userObserver.sendResponse(new DraftedDiceResponse(dice));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println("Broadcaster is not active");
    }

    public void broadcastResponseToAll(List<Dice> dice) {
        if (isBroadcasterActive) {
            for (UserObserver player : playersToBroadcast()) {
                try {
                    player.sendResponse(new DraftedDiceResponse(dice));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println("Broadcaster is not active");
    }

    public void broadcastResponseToAll(Response response) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast()) {
                try {
                    userObserver.sendResponse(response);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println("Broadcaster is not active");
    }


    public void updateMovesHistory(List<MoveStatus> movesHistory) {
        if (isBroadcasterActive) {
            for (UserObserver userObserver : playersToBroadcast()) {
                try {
                    userObserver.sendResponse(new MoveStatusNotification(movesHistory));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println("Broadcaster is not active");
    }
}
