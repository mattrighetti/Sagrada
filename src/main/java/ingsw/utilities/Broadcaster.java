package ingsw.utilities;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.commands.DiceMoveResponse;
import ingsw.controller.network.commands.DiceNotification;
import ingsw.controller.network.commands.Response;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.User;

import javax.jws.soap.SOAPBinding;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Broadcaster {

    private Broadcaster() {

    }

    private static List<UserObserver> playerToBroadcast(List<Player> playerList, String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : playerList) {
            if (!player.getUser().getUsername().equals(usernameToExclude)) {
                playerListToBroadcast.add(player.getUser().getUserObserver());
            }
        }
        return playerListToBroadcast;
    }

    private static List<UserObserver> playerToBroadcast(Map<String, User> userMap, String usernameToExclude) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (User user : userMap.values()) {
            if (!user.getUsername().equals(usernameToExclude)) {
                playerListToBroadcast.add(user.getUserObserver());
            }
        }
        return playerListToBroadcast;
    }


    private static List<UserObserver> playerToBroadcast(List<Player> playerList) {
        List<UserObserver> playerListToBroadcast = new ArrayList<>();
        for (Player player : playerList) {
            playerListToBroadcast.add(player.getUser().getUserObserver());
        }
        return playerListToBroadcast;
    }

    private static List<UserObserver> userToBroadcast(List<User> userList, String usernameToExclude) {
        List<UserObserver> userListToBroadcast = new ArrayList<>();
        for (User user : userList) {
            if (!user.getUsername().equals(usernameToExclude)) {
                userListToBroadcast.add(user.getUserObserver());
            }
        }
        return userListToBroadcast;
    }

    public static void broadcastMessage(List<Player> playerList, Message message) {
        for (UserObserver userObserver : playerToBroadcast(playerList, message.sender)) {
            try {
                userObserver.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponse(List<Player> playerList, String usernameToExclude, List<Dice> dice) {
        for (UserObserver userObserver : playerToBroadcast(playerList, usernameToExclude)) {
            try {
                userObserver.sendResponse(new DiceNotification(dice));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, List<Dice> dice) {
        for (Player player : playerList) {
            try {
                player.getUser().getUserObserver().sendResponse(new DiceNotification(dice));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, DiceMoveResponse diceMoveResponse) {
        for (Player player : playerList) {
            try {
                player.getUserObserver().sendResponse(diceMoveResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponseToAll(Map<String, User> users, CreateMatchResponse createMatchResponse) {
        for (User user : users.values()) {
            try {
                user.getUserObserver().sendResponse(createMatchResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastResponse(Map<String, User> users, String usernameToExclude, CreateMatchResponse createMatchResponse) {
        for (UserObserver userObserver : playerToBroadcast(users, usernameToExclude)) {
            try {
                userObserver.sendResponse(createMatchResponse);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastMessageToAll(List<Player> playerList, Message message) {
        for (UserObserver userObserver : playerToBroadcast(playerList)) {
            try {
                userObserver.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
