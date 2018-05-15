package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.socket.ClientHandler;
import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SagradaGameTest {

    private static SagradaGame sagradaGame;

    @BeforeEach
    void setUp() throws RemoteException {
        sagradaGame = SagradaGame.get();
    }

    @Test
    void get() throws RemoteException {
        assertNotEquals( null, sagradaGame); }

    @Test
    void getConnectedUsers() {}

    @Test
    void loginUser() throws RemoteException, InvalidUsernameException {
        int currentConnectedUser = sagradaGame.getConnectedUsers();
        UserObserver userObserver = mock(ClientHandler.class);
        User user = sagradaGame.loginUser( "Pippo", userObserver);
        assertEquals( "Pippo", user.getUsername() );
        assertEquals( currentConnectedUser + 1, sagradaGame.getConnectedUsers());
        assertEquals( userObserver, user.getUserObserver());
        UserObserver secondUserObserver = mock(ClientHandler.class);
        assertThrows(InvalidUsernameException.class, () -> { sagradaGame.loginUser("Pippo", secondUserObserver);});
    }

    @Test
    void createMatch() throws RemoteException {
        int currentMatchesByNameSize = sagradaGame.matchesByName.size();
        Controller controller = sagradaGame.createMatch("firstMatch");
        assertNotEquals( null, controller);
        assertEquals( true, sagradaGame.matchesByName.containsKey("firstMatch"));
        assertEquals( true, sagradaGame.matchesByName.containsValue(controller));
        assertEquals( currentMatchesByNameSize + 1, sagradaGame.matchesByName.size());
        assertThrows(RemoteException.class, () -> { sagradaGame.createMatch("firstMatch");}, "Match already exists" );
    }

    @Test
    void broadcastUsersConnected() throws RemoteException, InvalidUsernameException {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        UserObserver userObserver = mock(ClientHandler.class);
        User user = sagradaGame.loginUser( "X", userObserver);
        String outputResult = sagradaGame.connectedUsers.values().stream()
                .filter(x -> !x.getUsername().equals("X"))
                .map( x -> x.getUsername()).collect(Collectors.joining("\n"));
        assertEquals( new StringBuilder(outputResult + "\n").toString() , outContent.toString());
    }
}