package ingsw.utilities;

import ingsw.controller.network.socket.ClientHandler;
import ingsw.model.Player;
import ingsw.model.User;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

class BroadcasterTest {

    private ArrayList<Player> playerlist;
    private PlayerBroadcaster playerBroadcaster;

    @BeforeEach
    void setUp() {
        playerlist = new ArrayList<>();
        playerlist.add(new Player(new User("a")));
        playerlist.get(0).getUser().attachUserObserver(mock(ClientHandler.class));

        playerlist.add(new Player(new User("b")));
        playerlist.get(1).getUser().attachUserObserver(mock(ClientHandler.class));

        playerlist.add(new Player(new User("c")));
        playerlist.get(2).getUser().attachUserObserver(mock(ClientHandler.class));

        playerlist.add(new Player(new User("d")));
        playerlist.get(3).getUser().attachUserObserver(mock(ClientHandler.class));

        playerBroadcaster = new PlayerBroadcaster(playerlist);
    }

    /*
    @Test
    void broadcastResponse() {
        List<Dice> dice = new ArrayList<>();

        playerBroadcaster.broadcastResponse("c",dice);
        try {
            Mockito.verify(playerlist.get(0).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(1).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(3).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void broadcastResponseToAll() {
        List<Dice> dice = new ArrayList<>();

        playerBroadcaster.broadcastResponseToAll(dice);

        try {
            Mockito.verify(playerlist.get(0).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(1).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(2).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(3).getUserObserver(),times(1)).sendResponse(Mockito.any(DraftedDiceResponse.class));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void broadcastResponseToAll1() {
        Map<String,User> users = new HashMap<>();
        CreateMatchResponse createMatchResponse = mock(CreateMatchResponse.class);
        users.put("a",new User("a"));
        users.get("a").attachUserObserver(mock(ClientHandler.class));
        users.put("b",new User("b"));
        users.get("b").attachUserObserver(mock(ClientHandler.class));
        users.put("c",new User("c"));
        users.get("c").attachUserObserver(mock(ClientHandler.class));
        users.put("d",new User("d"));
        users.get("d").attachUserObserver(mock(ClientHandler.class));

        playerBroadcaster.broadcastResponseToAll(createMatchResponse);

        try {
            Mockito.verify(users.get("a").getUserObserver(),times(1)).sendResponse(createMatchResponse);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(users.get("b").getUserObserver(),times(1)).sendResponse(createMatchResponse);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(users.get("c").getUserObserver(),times(1)).sendResponse(createMatchResponse);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(users.get("d").getUserObserver(),times(1)).sendResponse(createMatchResponse);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

 */
}
