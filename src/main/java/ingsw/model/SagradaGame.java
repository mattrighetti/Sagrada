package ingsw.model;

import ingsw.controller.Controller;

import java.util.Map;

public class SagradaGame implements BoardUpdater, PlayerObserver {
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    public void createMatch(String matchName, int noOfPlayers) {

    }

    public void joinMatch(String matchName, int noOfPlayers) {
        // Mostra solo le partite con matchesByName.joinedUser < 4
    }

    public void joinSagradaGame(String username) {
        connectedUsers.put(username, new User(username));
    }

}
