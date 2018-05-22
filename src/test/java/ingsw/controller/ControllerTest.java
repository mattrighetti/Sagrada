package ingsw.controller;

import ingsw.controller.network.socket.ClientHandler;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.RemoteView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() throws RemoteException {
        controller = new Controller("Test");
    }

    @Test
    void loginUser() throws NoSuchFieldException, IllegalAccessException {
        Field field = controller.getClass().getDeclaredField("playerList");
        field.setAccessible(true);
        for (int i = 0; i < 4; i++) {
            controller.loginUser(new User("i"), mock(RemoteView.class));
            ArrayList<Player> playerList = (ArrayList<Player>) field.get(controller);
            assertEquals(playerList.size(), i+1);
        }
        field.setAccessible(false);
    }

}