package ingsw.model;

import ingsw.controller.Controller;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.Broadcaster;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Stream;

public class SagradaGame extends UnicastRemoteObject implements RemoteSagradaGame {
    private static SagradaGame sagradaGameSingleton;
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users

    private SagradaGame() throws RemoteException {
        super();
        connectedUsers = new HashMap<>();
        matchesByName = new HashMap<>();
    }

    public static SagradaGame get() throws RemoteException {
        if (sagradaGameSingleton == null) {
            sagradaGameSingleton = new SagradaGame();
        }

        return sagradaGameSingleton;
    }

    public void removeMatch(Controller controller) {
        matchesByName.remove(controller.getMatchName(), controller);
        Broadcaster.broadcastResponseToAll(connectedUsers, new CreateMatchResponse(createAvailableMatchesList()));
    }

    /* REMOTE SAGRADAGAME PART*/

    /**
     * Method that returns the number of users currently connected to SagradaGame
     *
     * @return Number of users currently connected to SagradaGame
     */
    @Override
    public int getConnectedUsers() {
        return connectedUsers.size();
    }

    @Override
    public List<DoubleString> createAvailableMatchesList() {
        DoubleString doubleString;
        List<DoubleString> availableMatchesDoubleString = new ArrayList<>();
        for (Controller match : matchesByName.values()) {
            doubleString = new DoubleString(match.getMatchName(), match.getConnectedUsers());
            availableMatchesDoubleString.add(doubleString);
        }

        return availableMatchesDoubleString;
    }

    @Override
    public void sendSelectedMatchHistory(String username, String selectedMatchName) throws RemoteException {
        String matchFileName = selectedMatchName + ".txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/history/" + matchFileName))) {
            String movesJSON = bufferedReader.readLine();
            connectedUsers.get(username).getUserObserver().sendResponse(new HistoryResponse(movesJSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TripleString> createRankingsList() {
        TripleString tripleString;
        List<TripleString> ranking = new ArrayList<>();

        List<User> rankingList = new ArrayList<>(connectedUsers.values());
        rankingList.sort((user1, user2) -> {
            if (user1.getNoOfWins() > user2.getNoOfWins()) {
                return -1;
            } else if (user1.getNoOfWins() < user2.getNoOfWins()) {
                return 1;
            } else
                return 0;
        });

        for (int i = 0; i < rankingList.size(); i++) {
            rankingList.get(i).setPositionInRanking(i);
        }

        for (User user : connectedUsers.values()) {
            tripleString = new TripleString(String.valueOf(user.getPositionInRanking()),
                    user.getUsername(),
                    String.valueOf(user.getNoOfWins()));

            ranking.add(tripleString);
        }

        return ranking;
    }

    @Override
    public Map<String, TripleString> createUserStats(String username) {
        TripleString tripleString;
        Map<String, TripleString> userStats = new HashMap<>();
        tripleString = new TripleString(String.valueOf(connectedUsers.get(username).getNoOfWins()),
                String.valueOf(connectedUsers.get(username).getNoOfLose()),
                String.valueOf(connectedUsers.get(username).getActiveTime()));
        userStats.put(connectedUsers.get(username).getUsername(), tripleString);

        return userStats;
    }

    /**
     * Method that reads all .txt files in the history folder and sends them to the user who requested them
     *
     * @param username username who requested these datas
     * @throws RemoteException if something's wrong with the connection
     */
    @Override
    public synchronized void sendFinishedMatchesList(String username) throws RemoteException {
        List<String> stringList = new ArrayList<>();
        try (Stream<Path> pathStream = Files.walk(Paths.get("/Users/matt/Dev/IdeaProjects/Sagrada/src/main/resources/history/"))) {
            pathStream.filter(Files::isRegularFile).forEach(path -> stringList.add(path.getFileName().toString().replace(".txt", "")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectedUsers.get(username).getUserObserver().sendResponse(new FinishedMatchesResponse(stringList));
    }

    /**
     * Method that logs in the User to the main SagradaGame server
     *
     * @param username     Username of the username to log in
     * @param userObserver UserObserver of the User
     * @return Number of users currently connected to SagradaGame
     * @throws InvalidUsernameException if the username has already been taken
     * @throws RemoteException          if something with the network is wrong
     */
    @Override
    public synchronized User loginUser(String username, UserObserver userObserver) throws InvalidUsernameException, RemoteException {

        // Check if the username is present and inactive
        if (connectedUsers.containsKey(username) && !connectedUsers.get(username).isActive()) {
            // Update the UserObserver
            connectedUsers.get(username).addListener(userObserver);
            connectedUsers.get(username).setActive(true);
            //Check in which match the user was playing before disconnecting
            for (Controller controller : matchesByName.values()) {
                for (Player player : controller.getPlayerList()) {
                    if (player.getPlayerUsername().equals(username)) {
                        player.getUser().addListener(userObserver);
                        player.getUserObserver().sendResponse(new ReJoinResponse(controller.getMatchName(), player.getPlayerUsername()));
                    }
                }
            }
            // Return the same user with the updated UserObserver
            return connectedUsers.get(username);
        }

        // In case there is no username | the username is active
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            currentUser.addListener(userObserver);
            connectedUsers.put(username, currentUser);
            connectedUsers.get(username).getUserObserver().sendResponse(new LoginUserResponse(currentUser));
            broadcastUsersConnected(username);
            return connectedUsers.get(username);
        }

        throw new InvalidUsernameException("Username has been taken already");
    }

    @Override
    public void logoutUser(String username) throws RemoteException {
        connectedUsers.remove(username);
        broadcastUsersConnected(username);

        if (connectedUsers.get(username) != null) {
            throw new RemoteException("Logout unsuccessful");
        }
    }

    /**
     * Method that lets the user create a match
     *
     * @param matchName Name of the match to create
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized void createMatch(String matchName) throws RemoteException {
        Controller controller;
        if (!matchesByName.containsKey(matchName)) {
            controller = new Controller(matchName, this);
            matchesByName.put(matchName, controller);

            try {
                Naming.rebind("rmi://localhost:1099/" + matchName, controller);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Broadcaster.broadcastResponseToAll(connectedUsers, new CreateMatchResponse(createAvailableMatchesList()));
        } else
            throw new RemoteException("Match already exists");
    }

    @Override
    public synchronized void loginUserToController(String matchName, String username) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(username)) {
                matchesByName.get(matchName).loginUser(user);
                Broadcaster.broadcastResponse(connectedUsers, user.getUsername(), new CreateMatchResponse(createAvailableMatchesList()));
            }
        }
    }

    @Override
    public synchronized void loginPrexistentPlayer(String matchName, User newUser) throws RemoteException {
        boolean isMatchPresent = matchesByName.containsKey(matchName);
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(newUser.getUsername())) {
                connectedUsers.remove(user);
                connectedUsers.put(newUser.getUsername(), newUser);
            }
        }

        if (isMatchPresent) {
            for (Player player : matchesByName.get(matchName).getPlayerList()) {
                if (player.getPlayerUsername().equals(newUser.getUsername()) && !player.getUser().isActive()) {
                    System.out.println("SagradaGame: re-activating User " + newUser.getUsername());
                    player.updateUser(newUser);
                    System.out.println("Player has been updated, it's now back online");
                }
            }
        } else {
            throw new RemoteException("Match has already finished");
        }
    }

    public synchronized Controller getMatchController(String matchName) {
        return matchesByName.get(matchName);
    }

    /**
     * Method that broadcasts a simple string message to every connected user except the one with the
     * name passed by parameter
     *
     * @param username Username to exclude from the broadcast message
     */
    @Override
    public void broadcastUsersConnected(String username) {
        Broadcaster.broadcastResponseToAll(connectedUsers, connectedUsers.size());
        Broadcaster.broadcastResponseToAll(connectedUsers, createRankingsList());
    }

    @Override
    public void sendBundleData(String username) throws RemoteException {
        connectedUsers.get(username).getUserObserver().sendResponse(
                new BundleDataResponse(connectedUsers.size(), createRankingsList(), createAvailableMatchesList(), createUserStats(username)));
    }

    @Override
    public void deactivateUser(User disconnectedUser) {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(disconnectedUser.getUsername())) {
                user.setActive(false);
            }
        }
    }
}
