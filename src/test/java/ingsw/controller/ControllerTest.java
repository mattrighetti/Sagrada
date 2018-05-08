package ingsw.controller;

import ingsw.controller.network.socket.ClientHandler;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private List<Player> playerList;

    @BeforeEach
    void setUp(){
        playerList = new ArrayList<>();
    }

}