package ingsw.utilities;

import ingsw.controller.network.Message;
import ingsw.controller.network.commands.CreateMatchResponse;
import ingsw.controller.network.commands.DraftedDiceResponse;
import ingsw.controller.network.socket.ClientHandler;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class PlayerBroadcasterTest {

    private ArrayList<Player> playerlist;
    private PlayerBroadcaster playerBroadcaster;

    @BeforeEach
    void setUp() {
        playerlist = new ArrayList<>();
        playerlist.add(new Player(new User("a")));
        playerlist.get(0).getUser().addListener(mock(ClientHandler.class));

        playerlist.add(new Player(new User("b")));
        playerlist.get(1).getUser().addListener(mock(ClientHandler.class));

        playerlist.add(new Player(new User("c")));
        playerlist.get(2).getUser().addListener(mock(ClientHandler.class));

        playerlist.add(new Player(new User("d")));
        playerlist.get(3).getUser().addListener(mock(ClientHandler.class));

        playerBroadcaster = new PlayerBroadcaster(playerlist);
    }

    @Test
    void broadcastMessage() {

        Message message = new Message("b","test");

        playerBroadcaster.broadcastMessage(message);
        try {
            Mockito.verify(playerlist.get(0).getUserObserver(),times(1)).sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(2).getUserObserver(),times(1)).sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Mockito.verify(playerlist.get(3).getUserObserver(),times(1)).sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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
        users.get("a").addListener(mock(ClientHandler.class));
        users.put("b",new User("b"));
        users.get("b").addListener(mock(ClientHandler.class));
        users.put("c",new User("c"));
        users.get("c").addListener(mock(ClientHandler.class));
        users.put("d",new User("d"));
        users.get("d").addListener(mock(ClientHandler.class));

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
}
