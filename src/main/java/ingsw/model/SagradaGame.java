package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.RemoteController;

import java.util.Map;

public class SagradaGame implements BoardUpdater, PlayerObserver {
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    public void createMatch(String matchName, int noOfPlayers) {
        /*Board board = controller.createNewMatch(, noOfPlayers);
        matchesByName.put(matchName, board);*/
    }

    public void joinMatch(String matchName, int noOfPlayers) {
        if (matchesByName.containsKey(matchName)) {

        } else {
            createMatch(matchName, noOfPlayers);
        }
    }

    // Chiamato premendo il tasto Login, forse andrà in un'interfaccia (?)
    public void joinSagradaGame(String username) {
        connectedUsers.put(username, new User(username));
    }

}
