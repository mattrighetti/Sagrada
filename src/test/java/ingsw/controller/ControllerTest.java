package ingsw.controller;

import ingsw.model.GameManager;
import ingsw.model.Player;
import ingsw.model.SagradaGame;
import ingsw.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() throws RemoteException {
        controller = new Controller("Test",10, 10, SagradaGame.get());
    }

    @Test
    void loginUser() throws NoSuchFieldException, IllegalAccessException, RemoteException {
        Field field = controller.getClass().getDeclaredField("playerList");
        field.setAccessible(true);
        for (int i = 0; i < 3; i++) {
            controller.loginUser(new User("i"));
            List<Player> playerList = (List<Player>) field.get(controller);
            assertEquals(playerList.size(), i+1);
        }
        field.setAccessible(false);
    }

    @Test
    void createMatch() throws NoSuchFieldException, IllegalAccessException {
        Field field = controller.getClass().getDeclaredField("gameManager");
        field.setAccessible(true);

        GameManager gameManager = (GameManager) field.get(controller);
        assertTrue(gameManager == null);

        controller.createMatch();
        gameManager = (GameManager) field.get(controller);
        assertTrue(gameManager != null);
    }

}