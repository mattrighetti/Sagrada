package ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ingsw.controller.Controller;
import ingsw.controller.RemoteController;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.socket.ClientHandler;
import ingsw.controller.network.socket.UserObserver;
import ingsw.exceptions.InvalidUsernameException;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;
import ingsw.utilities.UserBroadcaster;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Class the manages the game lobby. It has all the connected users reference, the ranking,
 * the statistics for every player and the Matches histories
 */
public class SagradaGame implements RemoteSagradaGame {
    private static SagradaGame sagradaGameSingleton;
    Map<String, Controller> matchesByName; // List of all open matches
    Map<String, User> connectedUsers; // List of connected users
    private boolean stop;

    private int maxTurnSeconds;
    private int maxJoinMatchSeconds;

    private UserBroadcaster userBroadcaster;

    /**
     * Create a SagradaGame instance with instantiating the userBroadcaster, connectedUsers, list of match
     * and the time bound for joining a match and for doing an entire turn
     */
    private SagradaGame() {
        connectedUsers = new HashMap<>();
        matchesByName = new HashMap<>();
        maxJoinMatchSeconds = 40;
        maxTurnSeconds = 120;
        stop = false;
        readUserStatsFromFile();
        rmiUsersListener();
    }

    /**
     * Returns the current instance of SagradaGame.
     * It is a Singleton.
     *
     * @return SagradaGame
     */
    public static SagradaGame get() {
        if (sagradaGameSingleton == null) {
            sagradaGameSingleton = new SagradaGame();
        }

        return sagradaGameSingleton;
    }

    /**
     * Remove a match from the map in which there are stored all the active matches and removes it
     * from the RMIRegistry.
     *
     * @param controller Controller to remove, it is the match itself because it manages it.
     */
    public void removeMatch(Controller controller) {
        matchesByName.remove(controller.getMatchName(), controller);

        try {
            Naming.unbind(controller.getMatchName());
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }

        userBroadcaster.broadcastResponseToAll(new CreateMatchResponse(createAvailableMatchesList()));
    }

    private void checkUserDisconnection() {
        Set<User> activeUsers = new HashSet<>();
        for (User user : connectedUsers.values()) {
            try {
                user.getUserObserver();
            } catch (RemoteException e) {
                try {
                    deactivateUser(user.getUsername());
                } catch (RemoteException e1) {
                    System.err.println("Couldn't deactivate " + user.getUsername());
                }
            }
        }

        for (User user : connectedUsers.values()) {
            if (user.isActive()) {
                activeUsers.add(user);
            }
        }

        System.out.println(activeUsers);
    }

    public void rmiUsersListener() {
        new Thread(() -> {
            do {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                checkUserDisconnection();
            } while (!stop);
        }).start();
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

    /**
     * Creates a List of all active matches with the number of player connected to a match
     */
    public void readUserStatsFromFile() {
        new Thread(() -> {
            Gson gson = new Gson();

            File jarPath = new File(SagradaGame.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String jarParentFolderPath = jarPath.getParentFile().getAbsolutePath();
            File jarParentFolder = new File(jarParentFolderPath + "/stats");

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jarParentFolder + "/userstats.txt"))) {
                String movesJSON = bufferedReader.readLine();
                connectedUsers = gson.fromJson(movesJSON,
                                               new TypeToken<HashMap<String, User>>() {
                                               }.getType());
                userBroadcaster = new UserBroadcaster(connectedUsers);
            } catch (IOException e) {
                System.err.println("File non trovato, carico Sagrada");
                jarParentFolder.mkdir();
                userBroadcaster = new UserBroadcaster(connectedUsers);
            }
        }).start();
        System.out.println("EXITED");
    }

    @Override
    public void writeUsersStatsToFile() {
        Map<String, User> clonedConnectedUsers = new HashMap<>();
        User clonedUser;
        for (User user : connectedUsers.values()) {
            clonedUser = new User(user);
            clonedConnectedUsers.put(clonedUser.getUsername(), clonedUser);
        }

        Gson gson = new Gson();
        String usersStatsJSON = gson.toJson(clonedConnectedUsers);

        File jarPath = new File(SagradaGame.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String jarParentFolderPath = jarPath.getParentFile().getAbsolutePath();
        File jarParentFolder = new File(jarParentFolderPath + "/stats");

        if (!jarParentFolder.exists()) {
            if (jarParentFolder.mkdir()) System.out.println("User stats created successfully");
        }

        try (FileWriter file = new FileWriter(jarParentFolder + "/userstats.txt")) {
            file.write(usersStatsJSON);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("There was an error writing the file! Could not complete.");
        }
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

    /**
     * Search and send a history of selected match readed from file.
     *
     * @param username          User that requested the history
     * @param selectedMatchName Match name
     * @throws RemoteException
     */
    @Override
    public void sendSelectedMatchHistory(String username, String selectedMatchName) throws RemoteException {
        String matchFileName = selectedMatchName + ".txt";

        File jarPath = new File(SagradaGame.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String jarParentFolderPath = jarPath.getParentFile().getAbsolutePath();
        File jarParentFolder = new File(jarParentFolderPath + "/histories");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jarParentFolder + "/" + matchFileName))) {
            String movesJSON = bufferedReader.readLine();
            connectedUsers.get(username).getUserObserver().sendResponse(new HistoryResponse(movesJSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the maximum turn duration
     *
     * @param maxTurnSeconds
     */
    @Override
    public void setMaxTurnSeconds(int maxTurnSeconds) {
        this.maxTurnSeconds = maxTurnSeconds;
    }

    /**
     * Set the maximum login duration
     *
     * @param maxJoinMatchSeconds
     */
    @Override
    public void setMaxJoinMatchSeconds(int maxJoinMatchSeconds) {
        this.maxJoinMatchSeconds = maxJoinMatchSeconds;
    }

    /**
     * Creates an ordered List with the Ranking of all connected users
     * counting the number of victories.
     *
     * @return List containing the ranking
     */
    @Override
    public List<TripleString> createRankingsList() {
        TripleString tripleString;
        List<TripleString> ranking = new ArrayList<>();

        List<User> rankingList = new ArrayList<>(connectedUsers.values());
        rankingList.sort((user1, user2) -> Integer.compare(user2.getNoOfWins(), user1.getNoOfWins()));

        int positionInRanking = 1;
        int i = 1;
        while (i < rankingList.size()) {
            if (rankingList.get(i).getNoOfWins() == rankingList.get(i - 1).getNoOfWins()) {
                rankingList.get(i - 1).setPositionInRanking(positionInRanking);
                rankingList.get(i).setPositionInRanking(positionInRanking);
                i++;
            } else {
                rankingList.get(i - 1).setPositionInRanking(positionInRanking);
                positionInRanking++;
                rankingList.get(i).setPositionInRanking(positionInRanking);
                i++;
            }
        }

        for (User user : connectedUsers.values()) {
            tripleString = new TripleString(String.valueOf(user.getPositionInRanking()),
                                            user.getUsername(),
                                            String.valueOf(user.getNoOfWins()));

            ranking.add(tripleString);
        }

        ranking.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getFirstField())));

        return ranking;
    }

    /**
     * Create custom statistics for the user:
     * 1 - number of wins
     * 2 - number of lose
     * 3 - time played
     *
     * @param username user that requested the ranking
     * @return A Map containing the three statistics fields
     */
    @Override
    public Map<String, TripleString> createUserStats(String username) {
        TripleString tripleString;
        Map<String, TripleString> userStats = new HashMap<>();
        tripleString = new TripleString(String.valueOf(connectedUsers.get(username).getNoOfWins()),
                                        String.valueOf(connectedUsers.get(username).getNoOfLose()),
                                        connectedUsers.get(username).getFormattedTime());
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

        File jarPath = new File(SagradaGame.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String jarParentFolderPath = jarPath.getParentFile().getAbsolutePath();
        File jarParentFolder = new File(jarParentFolderPath + "/histories");

        try (Stream<Path> pathStream = Files.walk(Paths.get(jarParentFolder.getPath()))) {
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
            connectedUsers.get(username).attachUserObserver(userObserver);
            connectedUsers.get(username).setActive(true);
            //Check in which match the user was playing before disconnecting
            Executors.newSingleThreadExecutor().execute(() -> sendRejoinResponse(userObserver, username));
            // Return the same user with the updated UserObserver
            connectedUsers.get(username).getUserObserver().activatePinger();
            return connectedUsers.get(username);
        }

        // In case there is no username | the username is active
        User currentUser = new User(username);
        if (!connectedUsers.containsKey(username)) {
            currentUser.attachUserObserver(userObserver);
            connectedUsers.put(username, currentUser);
            connectedUsers.get(username).getUserObserver().sendResponse(new LoginUserResponse(currentUser));
            if (connectedUsers.get(username).getUserObserver() instanceof ClientHandler) {
                connectedUsers.get(username).getUserObserver().activatePinger();
            }
            broadcastUsersConnected(username);
            return connectedUsers.get(username);
        }

        throw new InvalidUsernameException("Username has been taken already");
    }

    /**
     * Send a ReJoinResponse to a player that wants to join again a match due to disconnection.
     * It sends the controller and the player username.
     *
     * @param userObserver The current instance of the player User Observer
     * @param username     Player Username
     */
    private void sendRejoinResponse(UserObserver userObserver, String username) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Controller controller : matchesByName.values()) {
            for (Player player : controller.getPlayerList()) {
                if (player.getPlayerUsername().equals(username)) {
                    player.getUser().attachUserObserver(userObserver);
                    try {
                        player.getUserObserver().sendResponse(new ReJoinResponse(controller.getMatchName(), player.getPlayerUsername()));
                        return;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            connectedUsers.get(username).attachUserObserver(userObserver);
            connectedUsers.get(username).getUserObserver().sendResponse(new LoginUserResponse(connectedUsers.get(username)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void logoutUser(String username) throws RemoteException {

    }

    /**
     * Method that lets the user create a match
     *
     * @param matchName Name of the match to create
     * @throws RemoteException if the match name has already been taken
     */
    @Override
    public synchronized void createMatch(String matchName) throws RemoteException {
        Controller controller;
        if (!matchesByName.containsKey(matchName)) {
            controller = new Controller(matchName, maxTurnSeconds, maxJoinMatchSeconds, this);
            RemoteController remoteController = (RemoteController) UnicastRemoteObject.exportObject(controller, 1100);
            matchesByName.put(matchName, controller);

            try {
                Naming.rebind("rmi://localhost:1099/" + matchName, remoteController);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            userBroadcaster.broadcastResponseToAll(new CreateMatchResponse(createAvailableMatchesList()));
        } else {
            throw new RemoteException("Match already exists");
        }
    }

    /**
     * Login a User to a match(and so to its controller). It also broadcasts a CreateMatchResponse
     *
     * @param matchName Controller name
     * @param username  Player username
     * @throws RemoteException
     */
    @Override
    public synchronized void loginUserToController(String matchName, String username) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(username)) {
                matchesByName.get(matchName).loginUser(user);
                userBroadcaster.broadcastResponse(user.getUsername(), new CreateMatchResponse(createAvailableMatchesList()));
            }
        }
    }

    /**
     * Login a User to a match that he was playing before the disconnection.
     *
     * @param matchName Controller name
     * @param username  Player username
     * @throws RemoteException
     */
    @Override
    public synchronized void loginPrexistentPlayer(String matchName, String username) throws RemoteException {
        boolean isMatchPresent = matchesByName.containsKey(matchName);
        System.out.println(isMatchPresent);
        if (isMatchPresent) {
            for (Player player : matchesByName.get(matchName).getPlayerList()) {
                if (player.getPlayerUsername().equals(username) && player.getUser().isActive()) {
                    System.out.println("SagradaGame: re-activating User " + username);
                    connectedUsers.get(username).setActive(true);
                    connectedUsers.get(username).setReady(true);
                    System.out.println("Player has been updated, it's now back online");
                }
            }
        }
    }

    /**
     * Returns the controller of a certain match
     *
     * @param matchName Controller name
     * @return Controller of a specified match
     */
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
        userBroadcaster.broadcastResponseToAll(connectedUsers.size());
        userBroadcaster.broadcastResponseToAll(createRankingsList());
    }

    /**
     * Sends to the player lobby all the data like:
     * - Number of connected users
     * - The ranking
     * - The available matches list
     * - The player statistics
     *
     * @param username Player username
     * @throws RemoteException
     */
    @Override
    public void sendBundleData(String username) throws RemoteException {
        connectedUsers.get(username).getUserObserver().sendResponse(
                new BundleDataResponse(connectedUsers.size(),
                                       createRankingsList(),
                                       createAvailableMatchesList(),
                                       createUserStats(username)));
    }

    /**
     * Method that deactivates a user, giving him the possibility to rejoin the match if it still exists
     * when he reconnects
     */
    @Override
    public synchronized void deactivateUser(String disconnectedUsername) throws RemoteException {
        for (User user : connectedUsers.values()) {
            if (user.getUsername().equals(disconnectedUsername)) {
                if (user.isActive()) {
                    user.setActive(false);
                    user.setReady(false);
                    return;
                }
            }
        }

        throw new RemoteException("User not in list :\\");
    }
}
