package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.rmi.RMIUserObserver;
import ingsw.controller.network.socket.ClientHandler;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;
import ingsw.utilities.UserBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SagradaGameTest {
    private SagradaGame sagradaGame;
    private RMIUserObserver rmiUserObserver;
    private ClientHandler clientHandler;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        sagradaGame = SagradaGame.get();
        Field instance = SagradaGame.class.getDeclaredField("sagradaGameSingleton");
        instance.setAccessible(true);
        instance.set(null, null);
        rmiUserObserver = mock(RMIUserObserver.class);
        clientHandler = mock(ClientHandler.class);
    }

    @Test
    void getter() {
        sagradaGame.getMatchController("Test");
    }

    @Test
    void setter() {
        sagradaGame.setMaxJoinMatchSeconds(10);
        sagradaGame.setMaxTurnSeconds(10);
    }

    @Test
    void getConnectedUsers() throws RemoteException, InvalidUsernameException {
        assertEquals(new User("FirstUser").getUsername(), sagradaGame.loginUser("FirstUser", rmiUserObserver).getUsername());
        assertEquals(1, sagradaGame.getConnectedUsers());
        assertEquals(new User("SecondUser").getUsername(), sagradaGame.loginUser("SecondUser", rmiUserObserver).getUsername());
        assertEquals(2, sagradaGame.getConnectedUsers());
    }

    @Test
    void loginUserTest() throws RemoteException, InvalidUsernameException {
        sagradaGame.loginUser("First", clientHandler);
        sagradaGame.loginUser("Second", clientHandler);
        sagradaGame.loginUser("Third", clientHandler);
        assertTrue(sagradaGame.connectedUsers.containsKey("First"));
        assertTrue(sagradaGame.connectedUsers.containsKey("Second"));
        assertTrue(sagradaGame.connectedUsers.containsKey("Third"));
        assertThrows(InvalidUsernameException.class, () -> sagradaGame.loginUser("First", clientHandler));
        assertThrows(InvalidUsernameException.class, () -> sagradaGame.loginUser("Second", clientHandler));
        assertThrows(InvalidUsernameException.class, () -> sagradaGame.loginUser("Third", clientHandler));
        sagradaGame.deactivateUser("First");
        assertFalse(sagradaGame.connectedUsers.get("First").isActive());
        assertFalse(sagradaGame.connectedUsers.get("First").isReady());
        sagradaGame.loginUser("First", clientHandler);
        assertTrue(sagradaGame.connectedUsers.get("First").isActive());
        assertFalse(sagradaGame.connectedUsers.get("First").isReady());
        assertThrows(InvalidUsernameException.class, () -> sagradaGame.loginUser("First", clientHandler));
        //TODO testa chiamata a metodo privato
    }

    @Test
    void logoutUserTest() throws RemoteException {
        sagradaGame.connectedUsers.put("First", mock(User.class));
        sagradaGame.connectedUsers.put("Second", mock(User.class));
        sagradaGame.logoutUser("First");
        assertFalse(sagradaGame.connectedUsers.containsKey("First"));
        assertEquals(1, sagradaGame.connectedUsers.size());
        sagradaGame.logoutUser("First");
        assertEquals(1, sagradaGame.connectedUsers.size());
        sagradaGame.logoutUser("Second");
        assertTrue(sagradaGame.connectedUsers.isEmpty());
    }

    /*
     * This test catches the exception thrown by the Naming.rebind() in case it couldn't find the rmiregistry which is
     * not loaded at test time
     */
    @Test
    void createMatch() throws RemoteException {
        sagradaGame.createMatch("Match1");
        sagradaGame.createMatch("Match2");
        sagradaGame.createMatch("Match3");
        assertTrue(sagradaGame.matchesByName.containsKey("Match1"));
        assertTrue(sagradaGame.matchesByName.containsKey("Match2"));
        assertTrue(sagradaGame.matchesByName.containsKey("Match3"));
        assertFalse(sagradaGame.matchesByName.containsKey("Match5"));
        assertThrows(RemoteException.class, () -> sagradaGame.createMatch("Match1"));
        assertThrows(RemoteException.class, () -> sagradaGame.createMatch("Match2"));
        assertThrows(RemoteException.class, () -> sagradaGame.createMatch("Match3"));
    }

    @Test
    void removeMatchTest() throws RemoteException {
        sagradaGame.createMatch("Match1");
        sagradaGame.createMatch("Match2");
        sagradaGame.createMatch("Match3");
        assertFalse(sagradaGame.matchesByName.isEmpty());
        sagradaGame.removeMatch(sagradaGame.matchesByName.get("Match1"));
        sagradaGame.removeMatch(sagradaGame.matchesByName.get("Match2"));
        sagradaGame.removeMatch(sagradaGame.matchesByName.get("Match3"));
        assertTrue(!sagradaGame.matchesByName.containsKey("Match1"));
        assertTrue(!sagradaGame.matchesByName.containsKey("Match2"));
        assertTrue(!sagradaGame.matchesByName.containsKey("Match3"));
        assertTrue(sagradaGame.matchesByName.isEmpty());
    }

    @Test
    void createAvailableMatchesListTest() throws RemoteException {
        List<DoubleString> expectedAvailableMatches = new ArrayList<>();
        expectedAvailableMatches.add(new DoubleString("Match3", 0));
        expectedAvailableMatches.add(new DoubleString("Match2", 0));
        expectedAvailableMatches.add(new DoubleString("Match1", 0));
        sagradaGame.createMatch("Match1");
        sagradaGame.createMatch("Match2");
        sagradaGame.createMatch("Match3");
        assertTrue(sagradaGame.createAvailableMatchesList().contains(expectedAvailableMatches.get(0)));
        assertTrue(sagradaGame.createAvailableMatchesList().contains(expectedAvailableMatches.get(1)));
        assertTrue(sagradaGame.createAvailableMatchesList().contains(expectedAvailableMatches.get(2)));
    }

    @Test
    void createRankingListTest() {
        List<TripleString> expectedList = new ArrayList<>();
        List<TripleString> actualList;
        User first = new User("First");
        User second = new User("Second");
        User third = new User("Third");
        User fourth = new User("Fourth");
        User fifth = new User("Fifth");
        first.incrementNoOfWins();
        first.incrementNoOfWins();
        second.incrementNoOfLose();
        second.incrementNoOfWins();
        third.incrementNoOfLose();
        sagradaGame.connectedUsers.put("First", first);
        sagradaGame.connectedUsers.put("Second", second);
        sagradaGame.connectedUsers.put("Third", third);
        sagradaGame.connectedUsers.put("Fifth", fifth);
        sagradaGame.connectedUsers.put("Fourth", fourth);
        expectedList.add(new TripleString("1", "First", "2"));
        expectedList.add(new TripleString("2", "Second", "1"));
        expectedList.add(new TripleString("3", "Third", "0"));
        expectedList.add(new TripleString("3", "Fifth", "0"));
        expectedList.add(new TripleString("3", "Fourth", "0"));
        actualList = sagradaGame.createRankingsList();
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getFirstField(),
                         actualList.get(i).getFirstField());
            assertEquals(expectedList.get(i).getSecondField(),
                         actualList.get(i).getSecondField());
            assertEquals(expectedList.get(i).getThirdField(),
                         actualList.get(i).getThirdField());
        }
    }

    @Test
    void createUserStatsTest() {
        Map<String, TripleString> expectedList = new HashMap<>();
        User first = new User("First");
        User second = new User("Second");
        User third = new User("Third");
        User fourth = new User("Fourth");
        User fifth = new User("Fifth");
        first.incrementNoOfWins();
        first.incrementNoOfWins();
        second.incrementNoOfLose();
        second.incrementNoOfWins();
        third.incrementNoOfLose();
        sagradaGame.connectedUsers.put("First", first);
        sagradaGame.connectedUsers.put("Second", second);
        sagradaGame.connectedUsers.put("Third", third);
        sagradaGame.connectedUsers.put("Fifth", fifth);
        sagradaGame.connectedUsers.put("Fourth", fourth);
        expectedList.put("First", new TripleString("2", "0", "0"));
        expectedList.put("Second", new TripleString("1", "1", "0"));
        expectedList.put("Third", new TripleString("0", "1", "0"));
        expectedList.put("Fourth", new TripleString("0", "0", "0"));
        expectedList.put("Fifth", new TripleString("0", "0", "0"));
        Map<String, TripleString> actualList = sagradaGame.createUserStats("First");
        assertEquals(expectedList.get("First").getFirstField(), actualList.get("First").getFirstField());
    }

    @Test
    void sendFinishedMatchesListTest() {
        //TODO test file reading
    }

    @Test
    void loginUserToControllerTest() throws RemoteException, InvalidUsernameException {
        Controller controller = new Controller("Match", 10, 10, sagradaGame);
        sagradaGame.matchesByName.put("Match", controller);
        sagradaGame.loginUser("First", clientHandler);
        assertEquals(1, sagradaGame.connectedUsers.size());
        sagradaGame.loginUserToController("Match", "First");
        assertEquals(1, controller.getPlayerList().size());
        assertEquals("First", controller.getPlayerList().get(0).getPlayerUsername());
        //TODO testa chiamate a metodo
    }

    @Test
    void loginPrexistentPlayerTest() throws RemoteException, InvalidUsernameException {
        sagradaGame.loginUser("First", rmiUserObserver);
        sagradaGame.loginUser("Second", rmiUserObserver);
        sagradaGame.loginUser("Third", rmiUserObserver);
        sagradaGame.loginUser("Fourth", rmiUserObserver);
        sagradaGame.createMatch("Match");
        sagradaGame.loginUserToController("Match", "First");
        sagradaGame.deactivateUser("First");
        assertFalse(sagradaGame.connectedUsers.get("First").isActive());
        assertFalse(sagradaGame.connectedUsers.get("First").isReady());
        sagradaGame.connectedUsers.get("First").setActive(true);
        sagradaGame.loginPrexistentPlayer("Match", "First");
        assertTrue(sagradaGame.connectedUsers.get("First").isReady());
        assertTrue(sagradaGame.connectedUsers.get("First").isActive());
    }

    @Test
    void broadcastUsersConnectedTest() {
        UserBroadcaster userBroadcaster = mock(UserBroadcaster.class);
        Whitebox.setInternalState(sagradaGame, "userBroadcaster", userBroadcaster);
        sagradaGame.broadcastUsersConnected("First");
        verify(userBroadcaster).broadcastResponseToAll(0);
        verify(userBroadcaster).broadcastResponseToAll(new ArrayList<>());
    }

    @Test
    void sendBundleDataTest() throws RemoteException {

    }
}