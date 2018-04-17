package ingsw.controller;

import ingsw.model.Board;
import ingsw.model.User;

import java.util.List;

/* INTERFACCIA DELLE CHIAMATE AL CONTROLLER CHE VERRANNO EFFETTUATE DA SAGRADAGAME */
public interface RemoteController {

    public Board createNewMatch(List<User> users);

}
