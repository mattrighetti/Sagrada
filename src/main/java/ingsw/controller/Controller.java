package ingsw.controller;

import ingsw.model.Board;
import ingsw.model.GameManager;
import ingsw.model.User;

import java.util.List;

public class Controller implements RemoteController {
    private final GameManager gameManager;

    public Controller(List<User> users) {
        gameManager = new GameManager(users);
    }

    @Override
    public Board createNewMatch(List<User> users) {
        return null;
    }
}
