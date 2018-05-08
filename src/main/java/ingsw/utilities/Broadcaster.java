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
        for (User user: userList) {
            if (!user.getUsername().equals(usernameToExclude)) {
                userListToBroadcast.add(user.getUserObserver());
            }
        }
        return userListToBroadcast;
    }

    public static void broadcastMessage(List<Player> playerList, String username, Message message){
        for (UserObserver userObserver : playerToBroadcast(playerList, message.sender)) {
            userObserver.sendMessage(message);
            //TODO send messages directly from player (?)
        }
    }

    public static void broadcastResponse(List<Player> playerList, String username, List<Dice> dice){
        for (UserObserver userObserver : playerToBroadcast(playerList, username)) {
            userObserver.sendResponse(new DiceNotification(dice));
            //TODO send messages directly from player (?)
        }
    }

    public static void broadcastResponseToAll(List<Player> playerList, List<Dice> dice) {
        for(Player player : playerList ){
            player.getUser().getUserObserver().sendResponse(new DiceNotification(dice));
        }
    }
}
