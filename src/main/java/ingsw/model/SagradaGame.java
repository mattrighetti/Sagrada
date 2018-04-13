package ingsw.model;

import java.util.Map;

public class SagradaGame implements BoardUpdater, PlayerObserver {
    Map<String, Board> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    private void createMatch(String matchName) {
        matchesByName.put(matchName, new Board(matchName));
    }

    private void joinMatch() {

    }

    // Chiamato premendo il tasto Login, forse andrà in un'interfaccia (?)
    private void joinSagradaGame(String username) {
        connectedUsers.put(username, new User(username));
    }

}
