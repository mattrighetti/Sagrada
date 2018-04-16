package ingsw.controller;

import ingsw.model.GameManager;

public class Controller implements RemoteController {
    public final transient GameManager gameManager;

    public Controller(String matchName) {
        this.gameManager = new GameManager(matchName);
    }

}
