package ingsw.utilities;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.DiceNotification;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

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
            userObserver.sendMessage(message);
        }
    }

    public static void broadcastResponse(List<Player> playerList, String usernameToExclude, List<Dice> dice) {
        for (UserObserver userObserver : playerToBroadcast(playerList, usernameToExclude)) {
            userObserver.sendResponse(new DiceNotification(dice));
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, List<Dice> dice) {
        for (Player player : playerList) {
            player.getUser().getUserObserver().sendResponse(new DiceNotification(dice));
        }
    }
}
