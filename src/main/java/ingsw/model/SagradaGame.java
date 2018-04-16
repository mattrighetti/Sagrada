package ingsw.model;

import ingsw.controller.RemoteController;

import java.util.Map;

public class SagradaGame implements BoardUpdater, PlayerObserver {
    Map<String, Board> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users
    RemoteController remoteController;

    private void createMatch(String matchName) {
        // Controller starts, takes the matchName just created
    }

    private void joinMatch() {

    }

    // Chiamato premendo il tasto Login, forse andrà in un'interfaccia (?)
    private void joinSagradaGame(String username) {
        connectedUsers.put(username, new User(username));
    }

}
