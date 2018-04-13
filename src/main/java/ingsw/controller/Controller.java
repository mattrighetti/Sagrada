package ingsw.controller;

import ingsw.model.GameManager;

public class Controller implements RemoteController {

    public final transient GameManager gameManager = GameManager.get();

}
