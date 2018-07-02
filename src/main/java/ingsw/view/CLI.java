package ingsw.view;


import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.User;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.*;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CLI implements SceneUpdater {
    private final AtomicBoolean moveNext;
    private final AtomicBoolean gamePhase;
    private final AtomicBoolean stoppedTurn;
    private AtomicInteger integerInput;
    private AtomicReference<String> stringInput;
    private StoppableScanner stoppableScanner;
    private int currentPlayerIndex;
    private String username;
    private String ipAddress;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType networkType;
    private Scanner scanner;

    private List<TripleString> statistics;
    private List<TripleString> ranking;
    private List<String> matchesPlayed;
    private List<MoveStatus> moveStatusList;
    private List<DoubleString> availableMatches;
    private List<Player> players;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<String> toolCards;
    private List<Dice> draftedDice;
    private Map<String, Boolean[][]> availablePosition;
    private List<List<Dice>> roundTrack;
    private List<MoveStatus> moveHistory;
    private AtomicBoolean toolCardUsed;
    private Thread moveThread;
    private Color selectedDiceColorTapWheel;
    private static final String wrongInputMessage = "Wrong Input";


    CLI(String ipAddress) {
        AnsiConsole.systemInstall();
        this.scanner = new Scanner(System.in);
        this.ipAddress = ipAddress;
        toolCardUsed = new AtomicBoolean(false);
        moveThread = new Thread();
        stoppableScanner = new StoppableScanner();
        statistics = new ArrayList<>();
        ranking = new ArrayList<>();
        matchesPlayed = new ArrayList<>();
        moveStatusList = new ArrayList<>();
        availableMatches = new ArrayList<>();
        toolCards = new ArrayList<>();
        moveHistory = new ArrayList<>();
        roundTrack = new ArrayList<>();
        toolCardUsed = new AtomicBoolean();
        gamePhase = new AtomicBoolean(false);
        moveNext = new AtomicBoolean(false);
        integerInput = new AtomicInteger();
        stoppedTurn = new AtomicBoolean(false);
    }

    void startCLI() {
        System.out.println("Deploying Socket & RMI");

        try {
            deploySocketClient(ipAddress);
            deployRMIClient(ipAddress);
            rmiController.setSceneUpdater(this);
            clientController.setSceneUpdater(this);
        } catch (IOException e) {
            System.err.println("Error during deployment of networkTypes");
            e.printStackTrace();
        }

        askForTypeOfConnection();

    }

    /**
     * <h1>String input scanner </h1>
     * <p>Read from the System in what the user types
     * </p>
     *
     * @return the String typed by the user
     */
    private String userStringInput() {
        stringInput = new AtomicReference<>();
        new Thread(
                () -> {
                    stringInput.set(stoppableScanner.readLine());

                    synchronized (stringInput) {
                        stringInput.notifyAll();
                    }
                }
        ).start();

        synchronized (stringInput) {
            if (stringInput.get() == null) {
                try {
                    stringInput.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
        stoppableScanner.cancel();
        return stringInput.get();
    }

    /*
     * *
     * <h1>Integer input scanner </h1>
     * <p>Read from the System in what number the user types
     * </p>
     * @return the int typed by the user, otherwise if he insert an invalid type, return -1
     */
    private int userIntegerInput() {
        integerInput = new AtomicInteger(-1);
        stoppableScanner = new StoppableScanner();
        new Thread(
                () -> {
                    integerInput.set(stoppableScanner.readInt());


                    synchronized (integerInput) {
                        integerInput.notifyAll();
                    }
                }
        ).start();

        synchronized (integerInput) {
            if (integerInput.get() == -1) {
                try {
                    integerInput.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
        stoppableScanner.cancel();
        return integerInput.get();
    }

    /**
     * Set the AtomicBoolean moveNext to false
     */
    private void notMoveNext() {
        moveNext.set(false);
    }

    /**
     * Set the AtomicBoolean moveNext to true
     */
    private void moveNext() {
        moveNext.set(true);
    }

    /**
     * Welcome view
     * <p>
     * Ask the user to choose which connection wants to use  connect to the game
     * if RMI or Socket connection
     */
    private void askForTypeOfConnection() {
        int selectedConnection;
        notMoveNext();
        System.out.println("\n" +
                                   "                                _       \n" +
                                   "                               | |      \n" +
                                   "  ___  __ _  __ _ _ __ __ _  __| | __ _ \n" +
                                   " / __|/ _` |/ _` | '__/ _` |/ _` |/ _` |\n" +
                                   " \\__ \\ (_| | (_| | | | (_| | (_| | (_| |\n" +
                                   " |___/\\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_|\n" +
                                   "             __/ |                      \n" +
                                   "            |___/                       \n");

        System.out.print("You're now connected!\nChoose a type of connection: \n");

        do {
            System.out.print("1 - RMI\n2 - Socket\n");
            selectedConnection = userIntegerInput();

            if (selectedConnection == 1) {
                moveNext();
                setNetworkType(rmiController);
                System.out.print("Alright! You selected RMI\n");
            } else if (selectedConnection == 2) {
                moveNext();
                setNetworkType(clientController);
                System.out.print("Alright! You selected Socket\n");
            } else {
                System.out.print("Incorrect input, try again\n");
            }

        } while (!moveNext.get());

        chooseUsernameAndLogin();
    }

    /**
     * Set Network Type
     * <p>
     * After the user has chosen the connection he wants to use
     * setNetworkType save to the attribute networkType the rmiController or the clientController
     *
     * @param currentConnectionType the instance of the subclass of networkType chosen by the user
     */
    @Override
    public void setNetworkType(NetworkType currentConnectionType) {
        this.networkType = currentConnectionType;
    }

    /**
     * Login view
     * <p>
     * Asks the user to insert the username to login.
     * Send the login request to the Server.
     */
    private void chooseUsernameAndLogin() {
        boolean rightUsername = false;
        System.out.print("We need to log currentPlayerIndex in now\n");
        do {
            String chosenUsername;
            System.out.print("Username:\n");
            chosenUsername = scanner.nextLine();
            if (!chosenUsername.isEmpty()) {
                System.out.print("Ok! Your username is: " + chosenUsername + "\n");
                rightUsername = true;
                networkType.loginUser(chosenUsername);
            } else System.out.println(wrongInputMessage);
        } while (!rightUsername);
    }

    /**
     * <h1>Loader of data from the model</h1>
     * <p>loadData save the data sent from the server
     * </p>
     *
     * @param boardDataResponse the response that contains the public cards, the tool cards and the players
     */
    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;
        this.publicObjectiveCards = boardDataResponse.publicObjectiveCards;
        for (ToolCard toolCard : boardDataResponse.toolCards) {
            toolCards.add((toolCard.getName()));
        }
    }

    /**
     * <h1>Launch lobby view</h1>
     * <p>Triggered from the Server
     * Launch the lobby view after the login
     * </p>
     *
     * @param username the username of the user who logged in
     */
    @Override
    public void launchSecondGui(String username) {
        this.username = username;

        showLobbyCommandsAndWait();
    }

    /**
     * Update Ranking State TableView
     *
     * @param tripleStringList list players in the Ranking
     */
    @Override
    public void updateRankingStatsTableView(List<TripleString> tripleStringList) {
        ranking.clear();
        ranking.addAll(tripleStringList);
    }

    @Override
    public void updateConnectedUsers(int usersConnected) {
        System.out.println("Connected Users: " + usersConnected);
    }

    /**
     * Update the list of the moves history
     * Triggered by the server
     *
     * @param notification the notification that contains the updated list of moves status
     */
    @Override
    public void updateMovesHistory(MoveStatusNotification notification) {
        moveHistory = notification.moveStatuses;
    }


    /**
     * Lobby View
     * <p>
     * showLobbyCommandsAndWait shows the commands that the user can choose before the match
     * Lets the user choose among create a new match, join a match or Show statistics and rankings
     */
    private void showLobbyCommandsAndWait() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            networkType.requestBundleData();
            int selectedCommand;
            notMoveNext();

            do {
                System.out.println("You're finally logged to SagradaGame");

                System.out.println("Choose a command:\n" +
                                           "1 - Create a match\n" +
                                           "2 - Join an existing match\n" +
                                           "3 - Join and watch an old match\n" +
                                           "4 - Show my statistics\n" +
                                           "5 - Show Ranking");

                selectedCommand = userIntegerInput();

                switch (selectedCommand) {
                    case 1:
                        createMatch();
                        //wait
                        break;
                    case 2:
                        joinMatch();
                        //wait
                        break;
                    case 3:
                        showFinishedMatches();
                        //wait
                        break;
                    case 4:
                        showStatistics();
                        //wait
                        break;
                    case 5:
                        showRanking();
                        //wait
                        break;
                    default:
                        System.err.println(wrongInputMessage);
                        break;
                }

            } while (!moveNext.get());

        }).start();

    }

    /**
     * Create a new Match
     * Ask the user to type the name of the match that wants to create
     * Sends a request to the server to create the new match
     */
    public void createMatch() {
        boolean rightMatchName = false;

        do {
            String matchName;
            System.out.println("Insert Match Name: ");
            matchName = userStringInput();
            if (!matchName.isEmpty()) {
                boolean existingMatch = false;
                for (DoubleString doubleString : availableMatches) {
                    if (doubleString.getFirstField().equals(matchName)) {
                        existingMatch = true;
                    }
                }
                if (!existingMatch) {
                    networkType.createMatch(matchName);
                    rightMatchName = true;
                } else {
                    System.err.println("Match name has already been taken, choose another one");
                }
            } else System.out.println(wrongInputMessage);

        } while (!rightMatchName);
    }

    /**
     * <h1>Join match request</h1>
     * <p>
     * joinMatch shows the available matches and let the user to choose one
     * Send the join match request for the selected match to the server
     * Notify the user if there are not available matches
     * </p>
     */
    private void joinMatch() {
        int selectedMatch;
        notMoveNext();

        if (!availableMatches.isEmpty()) {
            while (!moveNext.get()) {
                System.out.println("Available matches:\n");
                for (int i = 0; i < availableMatches.size(); i++) {
                    System.out.println(i + 1 + " - "
                                               + availableMatches.get(i).getFirstField() + "\t players:"
                                               + availableMatches.get(i).getSecondField() + "\n");
                }
                selectedMatch = userIntegerInput();

                if (0 < selectedMatch && selectedMatch < (availableMatches.size() + 1)) {
                    networkType.joinExistingMatch(availableMatches.get(selectedMatch - 1).getFirstField());
                    System.out.println("Confirmed\nYou logged in successfully!\nWait for other players...");
                    moveNext();
                } else System.out.println("Not valid Match selected, choose another match");
            }
        } else {
            System.out.println("There are no matches. Please create a new one");
        }
    }

    /**
     * Show Finished Matches
     * <p>
     * showFinishedMatches shows the list of all finished matches and let the user to choose what he wants to replay
     * Then show all the history moves of the chosen match
     */
    private void showFinishedMatches() {
        requestFinishedMatchesList();
        //Check if there is at least an old match to show
        if (!matchesPlayed.isEmpty()) {

            System.out.println("The list of all the played matches: ");
            for (int i = 0; i < matchesPlayed.size(); i++) {
                System.out.println((i + 1) + " - " + matchesPlayed.get(i));
            }
            System.out.println("Choose what you want to watch: \nInsert the index of the match or insert 0 to exit");

            int selectedMatch;
            do {
                selectedMatch = userIntegerInput();
            } while (0 < selectedMatch && selectedMatch < matchesPlayed.size());

            if (selectedMatch != 0) {

                networkType.requestHistory(matchesPlayed.get(selectedMatch - 1));

                if (!moveStatusList.isEmpty()) {
                    for (MoveStatus move : moveStatusList) {
                        System.out.println(move);
                    }
                }
            }

        } else {
            System.out.println("There are no played matches");
        }
    }

    /**
     * Notify Game Phase
     * Notifies the thread locked on gamePhase wait
     */
    private void notifyGamePhase() {
        synchronized (gamePhase) {
            gamePhase.notifyAll();
        }
    }

    private void requestFinishedMatchesList() {
        networkType.requestFinishedMatches();
    }

    /**
     * ShowStatistics
     * <p>
     * Method that prints the user statistics
     */
    private void showStatistics() {

        System.out.println("Your Statistic:\n");
        for (TripleString statistic : statistics) {
            System.out.println(statistic.toString());
        }

    }

    /**
     * showRanking prints the Ranking:
     * the ordered list of the players who played matches and their points
     */
    private void showRanking() {
        System.out.println("Ranking: ");
        for (TripleString rankingList : ranking) {
            System.out.println(rankingList.toString());
        }

    }

    /**
     * <h1>Deploy Socket Client</h1>
     * <p>Method that creates a client connection to the previously opened server socket
     * </p>
     *
     * @throws IOException exception produced by failed or
     *                     interrupted I/O operations.
     */
    private void deploySocketClient(String ipAddress) throws IOException {
        Client client = new Client(ipAddress, 8000);
        client.connect();
        this.clientController = new ClientController(client);
    }

    /**
     * <h1>Deploy RMI Client</h1>
     * <p>Method that creates a RMI connection to SagradaGame which resides in the RMIHandler
     * </p>
     *
     * @throws RemoteException if the RMI connection is not established correctly
     */
    private void deployRMIClient(String ipAddress) throws RemoteException {
        rmiController = new RMIController();
        rmiController.connect(ipAddress);
    }

    /**
     * Method that creates a RMI connection to SagradaGame which resides in the RMIHandler
     *
     * @param patternCardNotification Notification containing the four pattern cards sent by the Server
     */
    @Override
    public void launchThirdGui(PatternCardNotification patternCardNotification) {
        new Thread(() -> {
            int chosenPatternCard;
            notMoveNext();
            do {
                System.out.println("Now select your Pattern Card:");


                for (int i = 0; i < patternCardNotification.patternCards.size(); i++) {
                    System.out.println((i + 1) + " - "
                                               + patternCardNotification.patternCards.get(i).toString());
                }
                chosenPatternCard = userIntegerInput();

                if (0 < chosenPatternCard && chosenPatternCard < (patternCardNotification.patternCards.size() + 1)) {
                    networkType.choosePatternCard(patternCardNotification.patternCards.get(chosenPatternCard - 1));
                    moveNext();
                }
            } while (!moveNext.get());
        }).start();

    }

    /**
     * <h1>Game View</h1>
     * <p> call the method loadData that save the data sent by the server
     * after every player has chosen his pattern card
     * </p>
     *
     * @param boardDataResponse the response that contains the public cards, the tool cards and the players
     */
    @Override
    public void launchFourthGui(BoardDataResponse boardDataResponse) {
        if (!stoppableScanner.isReaderCancelled()) {
            moveNext();
            stoppableScanner.cancel();
        }
        System.out.println("You're now in the game");
        loadData(boardDataResponse);

    }

    /**
     * <h1>Draft notification</h1>
     * popUpDraftNotification asks the player to draft dice at the beginning of th round
     * Triggered by the server
     */
    @Override
    public void popUpDraftNotification() {
        notMoveNext();
        new Thread(() -> {
            while (!moveNext.get()) {
                System.out.println("It's time to draft the dice: insert 'd' to draft");
                if (!userStringInput().equals("interrupted"))
                    networkType.draftDice();
                moveNext();
            }
        }).start();
    }

    /**
     * RoundTrackNotification
     * <p>
     * Method that adds to the list of roundTrack the list of the dice of the last round
     *
     * @param roundTrackNotification class that contains the last Round Track dice list
     */
    @Override
    public void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        roundTrack.add(roundTrackNotification.roundTrack);
    }


    /**
     * Set Available Positions
     * <p>
     * Method that saves the updated Available Positions
     *
     * @param availablePositions Map of matrixes of available positions
     */
    @Override
    public void setAvailablePositions(Map<String, Boolean[][]> availablePositions) {
        this.availablePosition = availablePositions;
    }

    /**
     * The method that start the turn of player in the view
     * it saves the new available positions and launch the method to start the turn and to make moves
     *
     * @param startTurnNotification start turn notification
     */
    @Override
    public void startTurn(StartTurnNotification startTurnNotification) {
        stoppedTurn.set(false);
        availablePosition = startTurnNotification.booleanMapGrid;
        chooseMove();
    }

    /**
     * Turn manager
     * chooseMove shows to the player the available moves he can choose
     * it calls the moves methods and and manage the turn progress
     */
    private void chooseMove() {
        moveThread = new Thread(() -> {
            boolean placeDiceMove = false;

            notMoveNext();
            do {
                if (!(placeDiceMove && toolCardUsed.get())) {
                    int selectedMove;
                    showPatternCards();
                    System.out.println("It's your turn!\nChoose what move currentPlayerIndex want to do:\n");

                    if (!placeDiceMove)
                        System.out.println("1 - Place dice");

                    if (!toolCardUsed.get())
                        System.out.println("2 - Use tool card");

                    System.out.println("3 - Show Match Story");

                    System.out.println("4 - End turn");


                    selectedMove = userIntegerInput();

                    switch (selectedMove) {
                        case 1:
                            if (!placeDiceMove) {
                                placeDiceMove = placeDice();
                                if (placeDiceMove) {
                                    System.out.println("Dice place correctly");
                                }
                            } else System.out.println("You have already done this move in this turn");
                            break;
                        case 2:
                            if (!toolCardUsed.get()) {
                                if (toolCardMove()) {
                                    //method in wait
                                    gamePhaseWait();
                                }
                            } else System.out.println("You have already done this move in the turn");
                            break;
                        case 3:
                            showMoveHistory();

                            break;
                        case 4:
                            endTurnMove();
                            moveNext();
                            break;
                        default:
                            System.err.println(wrongInputMessage);
                    }
                } else moveNext();

            } while (!moveNext.get());
        });
        moveThread.start();
    }

    private void gamePhaseWait() {
        synchronized (gamePhase) {
            try {
                gamePhase.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that show to the user the moves history
     */
    private void showMoveHistory() {
        for (MoveStatus move : moveHistory) {
            System.out.println(move.toString());
        }

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    /**
     * Show Round Track
     * <p>
     * Method that prints the dice of the Round Track
     */
    private void showRoundTrack() {
        if (!roundTrack.isEmpty()) {
            for (int i = 0; i < roundTrack.size(); i++) {
                System.out.println("Round " + (i + 1) + ":\t");
                for (int j = 0; j < roundTrack.get(i).size(); j++) {
                    System.out.println((j + 1) + " - " + roundTrack.get(i).get(j).toString() + "\t");
                }
                System.out.println("\n");
            }
        } else System.out.println("RoundTrack is empty\n");
    }


    /**
     * Display pattern card
     * <p>
     * showPatternCards prints the Pattern cards of each player who is in the match
     */
    private void showPatternCards() {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerUsername().equals(username)) {
                currentPlayerIndex = i;
                System.out.print("\t\tYou\t\t\t\t\t\t");
            } else {
                System.out.print("\t\t" + players.get(i).getPlayerUsername() + "\t\t\t\t\t\t");
            }
        }

        System.out.print("\n\n");

        for (int i = 0; i < 4; i++) {
            for (Player player : players) {
                for (int j = 0; j < 5; j++) {
                    System.out.print(player.getPatternCard().getGrid().get(i).get(j).toString() + " ");
                }
                System.out.print("\t\t");
            }
            System.out.print("\n");
        }

    }

    /**
     * Display pattern card
     * <p>
     * showPatternCards prints the Pattern cards of the player selected
     *
     * @param player the pattern card that has to be shown belongs to this player
     */
    private void showPatternCardPlayer(Player player) {
        System.out.println("\tplayer: " + player.getPlayerUsername());
        for (int i = 0; i < player.getPatternCard().getGrid().size(); i++) {
            for (Box box : player.getPatternCard().getGrid().get(i)) {
                System.out.print(box.toString() + " ");
            }
            System.out.print("\n\n");
        }
    }

    /**
     * Show Drafted Dice
     * <p>
     * Method that prints the dice from Draft Pool
     */
    private void showDraftedDice() {
        for (int i = 0; i < draftedDice.size(); i++) {
            System.out.print((i + 1) + " - " + draftedDice.get(i).toString() + "\n");
        }
    }

    /**
     * Place dice move
     * <p>
     * PlaceDice manage the placing dice move
     * Show the drafted dice and let the player to choose one
     * Show the pattern card and the available positions in which the player can place the dice
     * Finally asks the player where he wants to place the dice
     *
     * @return true if the dice has been placed, false if not
     */
    private boolean placeDice() {
        int selectedDice;
        do {
            System.out.println("Select a dice");
            showDraftedDice();
            System.out.println("\n" + (draftedDice.size() + 1) + " - exit");
            selectedDice = userIntegerInput() - 1;
            //stop turn
            if (stoppedTurn.get()) return false;

            // in case the player choose 'exit'
            if (selectedDice == (draftedDice.size())) return false;

            if (0 <= selectedDice && selectedDice < draftedDice.size()) {
                System.out.println("Select a position in the pattern card: \n");
                showPatternCardPlayer(players.get(currentPlayerIndex));

                if (choosePositionAndPlaceDice(selectedDice)) {
                    return true;
                } else return false;

            } else System.out.println("Wrong dice input\n");

        } while (true);
    }

    /**
     * Choose Position and Place a Die
     * <p>
     * Method that prints the available positions in the Pattern Card for the selected Die, if there is at least one
     * Lets the player choose where he wants to place the die
     * Then it sends a PlaceDice Request to the server with the die and the position
     *
     * @param selectedDice the selected die the player wants to place
     * @return true if the dice is placed, false if not
     */
    private boolean choosePositionAndPlaceDice(int selectedDice) {

        System.out.println("These are the positions where currentPlayerIndex can place the dice selected:");

        if (checkAndShowAvailablePositions(draftedDice.get(selectedDice).toString())) {

            int selectedRow = chooseRowIndex();
            //stop turn
            if (stoppedTurn.get()) return false;

            int selectedColumn = chooseColumnIndex();
            //stop turn
            if (stoppedTurn.get()) return false;

            if (availablePosition.get(draftedDice.get(selectedDice).toString())[selectedRow][selectedColumn].equals(true)) {
                networkType.placeDice(draftedDice.get(selectedDice), selectedColumn, selectedRow);
                return true;
            } else System.out.println("Wrong position input");

        } else System.out.println("There are not available positions to place this dice\n Choose another move");

        return false;
    }


    /**
     * Check and Show Available Positions
     *
     * @param selectedDice the selected die whose available positions have to be checked
     * @return true if there is at least a position where the selected die can be placed, false if there is not
     */
    private boolean checkAndShowAvailablePositions(String selectedDice) {
        boolean anyAvailablePosition = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (availablePosition.containsKey(selectedDice)) {
                    if (availablePosition.get(selectedDice)[i][j]) {
                        System.out.println("[" + i + "," + j + "]");
                        anyAvailablePosition = true;
                    }
                } else System.out.println("There are not available positions for" + selectedDice);
            }
        }
        return anyAvailablePosition;
    }

    /**
     * Update Available Positions
     * <p>
     * Method that receives the updated available positions from the server and saves them in the attribute
     *
     * @param availablePosition Map of matrix of available positions
     */
    private void updateAvailablePositions(Map<String, Boolean[][]> availablePosition) {
        this.availablePosition = availablePosition;
    }

    /**
     * Time Out
     * <p>
     * Method that notify the player and stops the turn
     * Triggered from the server
     *
     * @param timeOutResponse
     */
    @Override
    public void timeOut(TimeOutResponse timeOutResponse) {
        stoppedTurn.set(true);
        //if the timer is ended during a toolcard move: reset the data
        if (timeOutResponse.toolCardMoveActive) {
            roundTrack = timeOutResponse.roundTrack;
            updatePatternCard(timeOutResponse.currentPlayer);
            draftedDice = timeOutResponse.draftedDice;
        }
        //stops the input reader
        stoppableScanner.cancel();
        //notify and unlock the Moves thread
        notifyGamePhase();
        moveNext.set(true);
        //reset toolcard used flag
        toolCardUsed.set(false);
        System.out.println("\nTIME OUT!\nThe time is ended");
    }

    /**
     * Method that after login load all the lobby data: available matches, ranking and statistics
     *
     * @param bundleDataResponse contains available matches, ranking and statistics
     */
    @Override
    public void loadLobbyData(BundleDataResponse bundleDataResponse) {
        availableMatches.clear();
        availableMatches.addAll(bundleDataResponse.matches);
        ranking.clear();
        ranking.addAll(bundleDataResponse.rankings);
        statistics.clear();
        statistics.addAll(bundleDataResponse.userStatistics.values());
    }

    @Override
    public void endedTurn() {
        moveNext.set(true);
        stoppableScanner.cancel();
        toolCardUsed.set(false);
        System.out.println("Your Turn is ended!");
    }

    /**
     * End Turn Move
     * <p>
     * endTurnMove calls the endTurn method that send the endTurn Request to the server
     */
    private void endTurnMove() {
        toolCardUsed.set(false);
        networkType.endTurn(username);
        System.out.println("\nNext turn...\n");
    }

    /**
     * Tool Card Move
     * <p>
     * Show available Tool Cards and makes the user choose one to use
     */
    private boolean toolCardMove() {
        int chosenToolCard;
        do {
            System.out.println("Choose a Tool Card:\n");
            System.out.println("0 - Exit\n");
            for (int i = 1; i <= toolCards.size(); i++) {
                System.out.println(i + " - " + toolCards.get(i - 1) + "\n");
            }
            chosenToolCard = userIntegerInput() - 1;
            //stop turn
            if (stoppedTurn.get()) return false;

            if (chosenToolCard >= 0 && chosenToolCard < toolCards.size()) {
                networkType.useToolCard(toolCards.get(chosenToolCard));
                return true;
            } else if ((chosenToolCard + 1) == 0) {
                return false;
            }

        } while (!(-1 <= chosenToolCard && chosenToolCard < toolCards.size()));
        return false;
    }

    /**
     * Set Drafted Dice
     * <p>
     * Method that saves the drafted dice received from the server and send the received dice ack
     *
     * @param dice list of drafted dice
     */
    @Override
    public void setDraftedDice(List<Dice> dice) {

        if (!stoppableScanner.isReaderCancelled()) stoppableScanner.cancel();

        this.draftedDice = dice;
        networkType.sendAck();
    }

    private void setDraftedDiceAndShow(List<Dice> dice) {
        this.draftedDice = dice;
        showDraftedDice();

    }

    /**
     * <h1>Update the pattern cards</h1>
     * <p>
     * Triggered from the server
     * After every turn the server sends the updateViewResponse to update the pattern cards in every client
     * and show the news
     * </p>
     *
     * @param updateViewResponse the response that contains the updated pattern card
     */
    @Override
    public void updateView(UpdateViewResponse updateViewResponse) {
        updatePatternCard(updateViewResponse.player);
        updateAvailablePositions(updateViewResponse.availablePositions);
        System.out.println(updateViewResponse.player.getPlayerUsername() + " placed a die:");
        showPatternCardPlayer(updateViewResponse.player);

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    /**
     * Update Pattern Card
     * <p>
     * Method that update the pattern card of a player
     *
     * @param playerToUpload the player that contains his pattern card
     */
    private void updatePatternCard(Player playerToUpload) {
        for (Player player : players) {
            if (player.getPlayerUsername().equals(playerToUpload.getPlayerUsername()))
                player.getPatternCard().setGrid(playerToUpload.getPatternCard().getGrid());
        }
    }

    /**
     * Show Selected Match History
     * <p>
     * Method that saves in moveStatusList attribute the list of move history of a selected match
     *
     * @param history List of moves of a specific match
     */
    @Override
    public void showSelectedMatchHistory(List<MoveStatus> history) {
        moveStatusList.clear();
        moveStatusList.addAll(history);
    }


    /**
     * Show Finished Matches
     * <p>
     * Method that saves the list of finished matches
     *
     * @param finishedMatches the list of finished matches
     */
    @Override
    public void showFinishedMatches(List<String> finishedMatches) {
        matchesPlayed.clear();
        matchesPlayed.addAll(finishedMatches);
    }

    /**
     * <h1>List of matches updater</h1>
     * <p>
     * Triggered by the server.
     * Update the list of the matches in the lobby
     * </p>
     *
     * @param matches the list of the updated matches sent by the server
     */
    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        availableMatches.clear();
        availableMatches.addAll(matches);

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    /**
     * Show Lost Notification
     * <p>
     * Method that notify the player that has lost the match and shows the player's score
     *
     * @param totalScore the score collected from the player in the current match
     */
    @Override
    public void showLostNotification(int totalScore) {
        System.out.println("Match Ended\nYou Lose!\nYour total score is: " + totalScore + "\n\nType a key to exit.");
        userStringInput();
        showLobbyCommandsAndWait();
    }

    /**
     * Show Winner Notification
     * <p>
     * Method that notify the player that has won the match and shows the player's score
     *
     * @param totalScore the score collected from the player in the current match
     */
    @Override
    public void showWinnerNotification(int totalScore) {
        System.out.println("Match Ended\nYou Win!\nYour total score is: " + totalScore + "\n\nInsert a key to exit.");
        showLobbyCommandsAndWait();

    }

    /* -------------------------------------------------------- */



    /* -------------------TOOL CARDS METHODS------------------- */

    /**
     * TOOL CARD RESPONSE
     * Data update and Confirmation
     *
     * @param draftedDiceToolCardResponse contains the new drafted dice list, after a player used a tool card
     */
    @Override
    public void toolCardAction(DraftedDiceToolCardResponse draftedDiceToolCardResponse) {
        System.out.println("This is the new drafted pool:\n");
        setDraftedDiceAndShow(draftedDiceToolCardResponse.draftedDice);

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    /**
     * Round Track Updater for ToolCards
     * <p>
     * Method that receives the updated Round Track after the usages of some tool card
     *
     * @param useToolCardResponse response that contains the updated RoundTrack
     */
    @Override
    public void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        roundTrack = useToolCardResponse.roundTrack;

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    /**
     * Pattern Card Updater for ToolCards
     * <p>
     * Method that receives the updated Pattern Card and available positions after the usages of some tool card
     *
     * @param useToolCardResponse response that contains the updated pattern card and the updated available positions
     */
    @Override
    public void toolCardAction(PatternCardToolCardResponse useToolCardResponse) {
        updatePatternCard(useToolCardResponse.player);
        updateAvailablePositions(useToolCardResponse.availablePositions);

        //Notify lock on gamePhase
        notifyGamePhase();
    }

    @Override
    public void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        System.out.println("Pay Attention\nYou can't use this tool card now\n");

        //Notify thread locked on GamePhase
        notifyGamePhase();
    }

    /**
     * GROZING PLIERS ToolCard
     *
     * @param useToolCardResponse Grozing pliers tool card response
     */
    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        new Thread(() -> {

            int chosenInput;
            do {
                System.out.println("Grozing Pliers\n\nChoose a die:");
                showDraftedDice();
                chosenInput = userIntegerInput();
                //stop turn
                if (stoppedTurn.get()) return;

            } while (!(0 < chosenInput && chosenInput <= draftedDice.size()));

            Dice selectedDice = draftedDice.get(chosenInput - 1);
            boolean goOn = false;

            do {
                System.out.println("Do you want to increase or decrease its face up value?\n");
                if (selectedDice.getFaceUpValue() < 6) System.out.println("1 - Increase\n");
                if (selectedDice.getFaceUpValue() > 1) System.out.println("2 - Decrease\n");

                chosenInput = userIntegerInput();
                //stop turn
                if (stoppedTurn.get()) return;

                if (chosenInput == 1 && selectedDice.getFaceUpValue() < 6) {
                    networkType.grozingPliersMove(selectedDice, true);
                    goOn = true;
                }
                if (chosenInput == 2 && selectedDice.getFaceUpValue() > 1) {
                    networkType.grozingPliersMove(selectedDice, false);
                    goOn = true;
                }
            } while (!goOn);

            toolCardUsed.set(true);
        }).start();
    }

    /**
     * FLUX BRUSH ToolCard
     * This method is invoked two times for the two different phases of the tool card move.
     * <p>
     * 1 - Lets the player a dice that has to be rolled again and send to the server the request
     * 2 - Receives the updated data, it shows the new die and lets the player to  place it in the pattern card
     *
     * @param useToolCardResponse contains the identifier "phase" that switch the right part of code,
     *                            the updated available positions, the dice selected from the player,
     *                            and the new rolled dice
     */
    @Override
    public void toolCardAction(FluxBrushResponse useToolCardResponse) {

        new Thread(() -> {

            switch (useToolCardResponse.phase) {

                //phase 1 of the tool card move
                case 1:

                    System.out.println("Flux Brush\n\nChoose which dice has to be rolled again:\n");
                    int selecteDice = selectDiceFromDrafted();
                    //stop turn
                    if (stoppedTurn.get() || selecteDice < 0) return;

                    networkType.fluxBrushMove(draftedDice.get(selecteDice));
                    break;
                //phase 2 of the tool card move
                case 2:

                    System.out.println("This is the new rolled value: " + useToolCardResponse.selectedDice.toString() + "\n");
                    updateAvailablePositions(useToolCardResponse.availablePositions);
                    draftedDice = useToolCardResponse.draftedDice;
                    boolean dicePlaced = false;

                    do {
                        //place die
                        showPatternCardPlayer(players.get(currentPlayerIndex));

                        if (checkAndShowAvailablePositions(useToolCardResponse.selectedDice.toString())) {

                            int selectedRow = chooseRowIndex();
                            //stop turn
                            if (stoppedTurn.get()) return;

                            int selectedColumn = chooseColumnIndex();
                            //stop turn
                            if (stoppedTurn.get()) return;

                            if (availablePosition.get(useToolCardResponse.selectedDice.toString())[selectedRow][selectedColumn]) {
                                dicePlaced = true;
                                networkType.fluxBrushMove(useToolCardResponse.selectedDice, selectedRow, selectedColumn);
                            } else
                                System.out.println("You can't place this dice in this position, choose another one\n");

                        } else {
                            System.out.println("This die can't be placed\n");
                            networkType.fluxBrushMove();
                            dicePlaced = true;
                        }
                    } while (!dicePlaced);
                    break;
                default:
                    System.out.println("Error in ToolCard move");
            }
            toolCardUsed.set(true);
        }).start();
    }

    /**
     * FLUX REMOVER ToolCard
     * This method is invoked three times for the three different phases of the tool card move.
     * <p>
     * 1 - It makes the player choose which die has to be removed and sends the request to the server
     * 2 - Received the new die, it asks to choose its value to the player
     * 3 - Finally it makes place the die in the pattern card, if it is possible
     *
     * @param useToolCardResponse contains the identifier "phase" that switch the right part of code,
     *                            the new drafted die, the available positions, and the new drafted pool
     */
    @Override
    public void toolCardAction(FluxRemoverResponse useToolCardResponse) {

        new Thread(() -> {
            switch (useToolCardResponse.phase) {
                case 1:
                    System.out.println("Flux Remover\n\nChoose which dice has to be removed:\n");
                    networkType.fluxRemoverMove(draftedDice.get(selectDiceFromDrafted()));
                    break;
                case 2:
                    System.out.println("The new drafted die has color " + useToolCardResponse.draftedDie.getDiceColor() + "\n");
                    int selectedValue;
                    do {
                        System.out.println("Choose its face up value\nType 1,2,3,4,5 or 6:\n");
                        selectedValue = userIntegerInput();
                        //stop turn
                        if (stoppedTurn.get()) return;

                    } while (selectedValue < 1 || selectedValue > 6);
                    networkType.fluxRemoverMove(useToolCardResponse.draftedDie, selectedValue);
                    break;
                case 3:
                    updateAvailablePositions(useToolCardResponse.availablePositions);
                    draftedDice = useToolCardResponse.draftedDice;
                    showPatternCardPlayer(players.get(currentPlayerIndex));
                    boolean dicePlaced = false;

                    do {
                        if (checkAndShowAvailablePositions(useToolCardResponse.draftedDie.toString())) {
                            int selectedRow;
                            int selectedColumn;

                            selectedRow = chooseRowIndex();
                            //stop turn
                            if (stoppedTurn.get()) return;

                            selectedColumn = chooseColumnIndex();
                            //stop turn
                            if (stoppedTurn.get()) return;

                            if (availablePosition.get(useToolCardResponse.draftedDie.toString())[selectedRow][selectedColumn]) {
                                networkType.fluxRemoverMove(useToolCardResponse.draftedDie, selectedRow, selectedColumn);
                                dicePlaced = true;
                            } else System.out.println("Position not available, choose another one\n");

                        } else {
                            System.out.println("You can't place this die");
                            networkType.fluxRemoverMove();
                        }
                    } while (!dicePlaced);
                    break;
                default:
                    System.out.println("Error in Flux Remover moves phase");
            }

            toolCardUsed.set(true);
        }).start();
    }

    /**
     * Select Dice from DraftedDice
     * Method that show to the user the dice from drafted dice pool and makes him choose one
     *
     * @return the index of the die selected from the player
     */

    private int selectDiceFromDrafted() {
        int selectedDice;
        do {
            System.out.println("Choose a die:\n");
            showDraftedDice();
            selectedDice = userIntegerInput();

            if (stoppedTurn.get() && selectedDice == -1) return -1;

            selectedDice--;

        } while (!(0 <= selectedDice && selectedDice < draftedDice.size()));
        return selectedDice;
    }

    /**
     * GRINDING STONE ToolCard
     * Method that makes choose a die that has to be flipped to the opposite site (value change)
     * and sends the request to the server
     *
     * @param useToolCardResponse response for the specific tool card, it does not contain data
     */
    @Override
    public void toolCardAction(GrindingStoneResponse useToolCardResponse) {
        new Thread(() -> {
            int chosenInput;
            do {

                System.out.println("Grinding Stone\n\nChoose which dice has to be flipped to the opposite side:");
                showDraftedDice();
                chosenInput = userIntegerInput();
                //stop turn
                if (stoppedTurn.get()) return;

            } while (!(0 < chosenInput && chosenInput <= draftedDice.size()));

            Dice selectedDice = draftedDice.get(chosenInput - 1);

            networkType.grindingStoneMove(selectedDice);
            toolCardUsed.set(true);
        }).start();
    }

    private int chooseRowIndex() {
        int rowIndex;
        do {
            System.out.println("Choose the row index:\n");
            rowIndex = userIntegerInput();
            if (!(0 <= rowIndex && rowIndex < 4)) System.out.println(wrongInputMessage);
            if (rowIndex == -1 && stoppedTurn.get()) return 0;

        } while (!(0 <= rowIndex && rowIndex < 4));
        return rowIndex;
    }

    private int chooseColumnIndex() {
        int columnIndex;
        do {
            System.out.println("Choose the column index:\n");
            columnIndex = userIntegerInput();
            if (!(0 <= columnIndex && columnIndex < 5)) System.out.println(wrongInputMessage);
            if (columnIndex == -1 && stoppedTurn.get()) return 0;

        } while (!(0 <= columnIndex && columnIndex < 5));
        return columnIndex;
    }

    /**
     * EGLOMISE BRUSH ToolCard
     * Method that makes place a dice in the pattern card without color restrictions
     *
     * @param useToolCardResponse response for the specific tool card, it contains available positions
     */
    @Override
    public void toolCardAction(EglomiseBrushResponse useToolCardResponse) {
        new Thread(() -> {
            updateAvailablePositions(useToolCardResponse.availablePositions);
            System.out.println("Eglomise Brush\n\nMove a dice in the Pattern Card ignoring color restrictions\n");
            int rowOne;
            int columnOne;
            boolean dicePlaced;

            do {
                System.out.println("Choose which die you want to move\n");
                showPatternCardPlayer(players.get(currentPlayerIndex));

                rowOne = chooseRowIndex();
                //stop turn
                if (stoppedTurn.get()) return;

                columnOne = chooseColumnIndex();
                //stop turn
                if (stoppedTurn.get()) return;

                dicePlaced = moveDiceInPatternCard(rowOne, columnOne);
                //stop turn
                if (stoppedTurn.get()) return;

            } while (!dicePlaced);
            toolCardUsed.set(true);
        }).start();
    }


    /**
     * COPPER FOIL BURNISHER ToolCard
     * Method that makes place a dice in the pattern card without value restrictions
     *
     * @param useToolCardResponse response for the specific tool card, it contains available positions
     */
    @Override
    public void toolCardAction(CopperFoilBurnisherResponse useToolCardResponse) {
        new Thread(() -> {
            updateAvailablePositions(useToolCardResponse.availablePositions);
            System.out.println("Copper Foil Burnisher\n\nMove a dice in the Pattern Card ignoring shade restrictions\n");
            int rowOne;
            int columnOne;
            boolean dicePlaced;

            do {

                System.out.println("Choose which die you want to move\n");
                showPatternCardPlayer(players.get(currentPlayerIndex));

                System.out.println("Select index from 0 to 4 for rows and from 0 to 5 for columns");
                rowOne = chooseRowIndex();
                //stop turn
                if (stoppedTurn.get()) return;

                columnOne = chooseColumnIndex();
                //stop turn
                if (stoppedTurn.get()) return;

                dicePlaced = moveDiceInPatternCard(rowOne, columnOne);
                //stop turn
                if (stoppedTurn.get()) return;

            } while (!dicePlaced);
            toolCardUsed.set(true);
        }).start();
    }

    private boolean moveDiceLathekin(int rowOne, int columnOne) {
        int rowTwo;
        int columnTwo;
        if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice() != null) {

            System.out.println("You choose " + players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + "\n");
            System.out.println("These are the position in which you can place the die:\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            if (checkAndShowAvailablePositions(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)) {

                System.out.println("Choose were you want to place the die\n");

                rowTwo = chooseRowIndex();
                //stop turn
                if (stoppedTurn.get()) return false;

                columnTwo = chooseColumnIndex();
                //stop turn
                if (stoppedTurn.get()) return false;


                if (availablePosition.get(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)[rowTwo][columnTwo]) {
                    Tuple diceToMoveIndex = new Tuple(rowOne, columnOne);
                    Tuple positionIndex = new Tuple(rowTwo, columnTwo);

                    if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowTwo).get(columnTwo).getDice() == null)
                        networkType.lathekinMove(diceToMoveIndex, positionIndex, false);
                    else
                        networkType.lathekinMove(diceToMoveIndex, positionIndex, true);

                    return true;
                } else System.out.println("Wrong position input");

            } else System.out.println("You can't place this dice, choose another one\n");

        }
        return false;

    }

    /**
     * After the player has chosen a die in the pattern card, this method makes move the die in another position
     * in the patter card
     *
     * @param rowOne    the row index of the position of the selected die, the one chosen to move
     * @param columnOne the column index of the position of the selected die,the one chosen to move
     * @return true if the die is placed, otherwise false
     */
    private boolean moveDiceInPatternCard(int rowOne, int columnOne) {

        if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice() != null) {

            System.out.println("You chose " + players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + "\n");
            System.out.println("These are the position in which you can place the die:\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            do {
                if (checkAndShowAvailablePositions(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)) {

                    System.out.println("Choose were you want to place the die\n");

                    int rowTwo;

                    rowTwo = chooseRowIndex();
                    //stop turn
                    if (stoppedTurn.get()) return false;

                    int columnTwo;

                    columnTwo = chooseColumnIndex();
                    //stop turn
                    if (stoppedTurn.get()) return false;

                    if (availablePosition.get(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)[rowTwo][columnTwo]) {
                        Tuple diceToMoveIndex = new Tuple(rowOne, columnOne);
                        Tuple positionIndex = new Tuple(rowTwo, columnTwo);
                        networkType.copperFoilBurnisherMove(diceToMoveIndex, positionIndex);
                        return true;
                    } else System.out.println("Wrong position input\n");

                } else {
                    System.out.println("You can't place this dice,\n");
                    return true;
                }

            } while (true);
        }
        return false;
    }

    /**
     * LATHEKIN ToolCard
     *
     * @param useToolCardResponse response for the specific tool card, it contains available positions and the pattern card
     */
    @Override
    public void toolCardAction(LathekinResponse useToolCardResponse) {
        new Thread(() -> {
            updateAvailablePositions(useToolCardResponse.availablePositions);
            Player player = new Player(new User(useToolCardResponse.playerUsername));
            player.setPatternCard(useToolCardResponse.patternCard);
            updatePatternCard(player);
            System.out.println("Lathekin\nMove the first dice. You must pay attention to all restrictions");

            placeDiceLathekin();
            //stop turn
            if (stoppedTurn.get()) return;

            if (useToolCardResponse.secondMove)
                toolCardUsed.set(true);
        }).start();
    }

    private void placeDiceLathekin() {
        int rowOne;
        int columnOne;
        boolean dicePlaced;
        do {
            System.out.println("Choose which die you want to move\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            rowOne = chooseRowIndex();
            //stop turn
            if (stoppedTurn.get()) return;

            columnOne = chooseColumnIndex();
            //stop turn
            if (stoppedTurn.get()) return;

            dicePlaced = moveDiceLathekin(rowOne, columnOne);
            //stop turn
            if (stoppedTurn.get()) return;
        } while (!dicePlaced);
    }


    /**
     * CORK BACKED STRAIGHTEDGE ToolCard
     *
     * @param useToolCardResponse response for the specific tool card, it contains available positions
     */
    @Override
    public void toolCardAction(CorkBackedStraightedgeResponse useToolCardResponse) {
        new Thread(() -> {
            System.out.println("Cork Backed Straightedge\nPlace a dice in a spot that is not adjacent to another die ");
            int selectedDice;
            int selectedRow;
            int selectedColumn;
            boolean dicePlaced = false;
            updateAvailablePositions(useToolCardResponse.availablePositions);
            do {

                selectedDice = selectDiceFromDrafted();
                if (selectedDice < 0) return;

                System.out.println("You chose " + draftedDice.get(selectedDice).toString() + "\n");
                System.out.println("These are the position in which you can place the die:\n");
                showPatternCardPlayer(players.get(currentPlayerIndex));

                if (checkAndShowAvailablePositions(draftedDice.get(selectedDice).toString())) {

                    System.out.println("Choose were you want to place the die\n");

                    selectedRow = chooseRowIndex();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    selectedColumn = chooseColumnIndex();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    if (availablePosition.get(draftedDice.get(selectedDice).toString())[selectedRow][selectedColumn].equals(true)) {
                        networkType.corkBackedStraightedgeMove(draftedDice.get(selectedDice), selectedRow, selectedColumn);
                        dicePlaced = true;
                    } else System.out.println("Wrong position input\n");

                } else System.out.println("You can't place this dice, choose another one\n");

            } while (!dicePlaced);

            toolCardUsed.set(true);
        }).start();
    }

    /**
     * LENS CUTTER ToolCard
     *
     * @param useToolCardResponse response for the specific tool card, it does not contain data
     */
    @Override
    public void toolCardAction(LensCutterResponse useToolCardResponse) {
        new Thread(() -> {
            System.out.println("Lens Cutter\nSwipe a die from the drafted die with another in the Round Track\n");

            int selectedDraftedDice = selectDiceFromDrafted();
            if (selectedDraftedDice < 0) return;

            int selectedRound;

            System.out.println("Select a die from the Round Track");
            showRoundTrack();
            if (!roundTrack.isEmpty()) {
                do {
                    System.out.println("Select the round:\n");

                    selectedRound = userIntegerInput() - 1;
                    //stop turn
                    if (stoppedTurn.get()) return;

                } while (!(0 <= selectedRound && selectedRound < roundTrack.size()));

                int selectedTrackDice;
                do {
                    System.out.println("Choose the die you want to swipe with:\n");
                    selectedTrackDice = userIntegerInput() - 1;
                    //stop turn
                    if (stoppedTurn.get()) return;

                } while (!(0 <= selectedTrackDice && selectedTrackDice < roundTrack.get(selectedRound).size()));

                networkType.lensCutter(selectedRound + 1, roundTrack.get(selectedRound).get(selectedTrackDice).toString(), draftedDice.get(selectedDraftedDice).toString());

            } else System.out.println("RoundTrack is empty, you can't swipe dice");

            toolCardUsed.set(true);
        }).start();
    }


    /**
     * RUNNING PLIERS ToolCard
     *
     * @param useToolCardResponse response for the specific tool card, it does not contain data
     */
    @Override
    public void toolCardAction(RunningPliersResponse useToolCardResponse) {
        new Thread(() -> {
            System.out.println("Running Pliers\n Draft another die\n");
            boolean dicePlaced = false;

            do {
                int selectedDice = selectDiceFromDrafted();
                if (selectedDice < 0) return;

                showPatternCardPlayer(players.get(currentPlayerIndex));
                System.out.println("These are the available position in wich you can place the selected die\n");

                if (checkAndShowAvailablePositions(draftedDice.get(selectedDice).toString())) {

                    int selectedRow = chooseRowIndex();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    int selectedColumn = chooseColumnIndex();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    if (availablePosition.get(draftedDice.get(selectedDice).toString())[selectedRow][selectedColumn].equals(true)) {
                        networkType.runningPliersMove(draftedDice.get(selectedDice), selectedRow, selectedColumn);
                        dicePlaced = true;
                    } else System.out.println("Wrong position input\n");

                } else System.out.println("You cannot place this die\n");

            } while (!dicePlaced);

            toolCardUsed.set(true);
        }).start();
    }

    /**
     * TAP WHEEL ToolCard
     *
     * @param useToolCardResponse response for the specific tool card,
     *                            contains the identifier "phase" that switch the right part of code,
     *                            the updated available positions and the pattern card
     */
    @Override
    public void toolCardAction(TapWheelResponse useToolCardResponse) {

        new Thread(() -> {
            switch (useToolCardResponse.phase) {
                case 0:
                    int selectedRound;
                    System.out.println("Tap Wheel\nChoose a dice from the round track and move at most two dice in the pattern with the same color");
                    showRoundTrack();
                    if (!roundTrack.isEmpty()) {
                        do {
                            System.out.println("Select the round:\n");

                            selectedRound = userIntegerInput() - 1;
                            //stop turn
                            if (stoppedTurn.get()) return;

                        } while (!(0 <= selectedRound && selectedRound < roundTrack.size()));

                        int selectedTrackDice;
                        do {
                            System.out.println("Choose the die you want to swipe with:\n");
                            selectedTrackDice = userIntegerInput() - 1;
                            //stop turn
                            if (stoppedTurn.get()) return;

                        } while (!(0 <= selectedTrackDice && selectedTrackDice < roundTrack.get(selectedRound).size()));

                        selectedDiceColorTapWheel = roundTrack.get(selectedRound).get(selectedTrackDice).getDiceColor();
                        networkType.tapWheelMove(roundTrack.get(selectedRound).get(selectedTrackDice), 0);

                    } else {
                        System.out.println("RoundTrack is empty");
                        networkType.tapWheelMove(-1);
                    }
                    break;
                case 1:
                    if (useToolCardResponse.availablePositions.size() > 0) {
                        updateAvailablePositions(useToolCardResponse.availablePositions);
                        updatePatternCard(useToolCardResponse.player);
                        placeDiceTapWheel(selectedDiceColorTapWheel, 1);
                    } else {
                        System.out.println("There are no dice in the pattern card with the same color of the selected dice");
                        networkType.tapWheelMove(-1);
                    }

                    break;
                case 2:
                    System.out.println("Do you want to place another dice or end the move?");
                    System.out.print("1 - Place a dice\n2 - End the move");
                    updateAvailablePositions(useToolCardResponse.availablePositions);
                    updatePatternCard(useToolCardResponse.player);
                    int choice = userIntegerInput();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    if (choice == 1) {
                        placeDiceTapWheel(selectedDiceColorTapWheel, 2);
                    }
                    if (choice == 2) {
                        System.out.println("end the turn");
                        networkType.tapWheelMove(-1);
                    }
                    selectedDiceColorTapWheel = null;
                    break;
                default:
            }

            toolCardUsed.set(true);
        }).start();
    }

    private void placeDiceTapWheel(Color selectedDiceColor, int phase) {
        int rowOne;
        int columnOne;
        boolean dicePlaced = false;
        do {
            System.out.println("Choose which die you want to move\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            rowOne = chooseRowIndex();
            //stop turn
            if (stoppedTurn.get()) return;

            columnOne = chooseColumnIndex();
            //stop turn
            if (stoppedTurn.get()) return;

            if ((players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne) != null) && selectedDiceColor.equals(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().getDiceColor())) {
                dicePlaced = placeDiceTapWheelToolCard(rowOne, columnOne, selectedDiceColor, phase);

                //stop turn
                if (stoppedTurn.get()) return;

            } else {
                int choice;
                do {
                    System.out.println("the chosen dice color doesn't match with the one chosen in the round track\nDo you want to choose another one or exit from the tool card?\n1 - Choose dice\n2 - exit");
                    choice = userIntegerInput();
                    //stop turn
                    if (stoppedTurn.get()) return;

                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            networkType.tapWheelMove(-1);
                            return;
                        case -1:
                            networkType.tapWheelMove(-1);
                    }
                } while (choice != 1 && choice != -1);
            }
        }
        while (!dicePlaced);
    }

    private boolean placeDiceTapWheelToolCard(int rowOne, int columnOne, Color selectedDiceColor, int phase) {

        int rowTwo;
        if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice() != null) {

            System.out.println("You chose " + players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + "\n");
            System.out.println("These are the position in which you can place the die:\n");


            showPatternCardPlayer(players.get(currentPlayerIndex));

            if (checkAndShowAvailablePositions(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)) {

                System.out.println("Choose were you want to place the die\n");

                rowTwo = chooseRowIndex();
                //stop turn
                if (stoppedTurn.get()) return false;

                int columnTwo = chooseColumnIndex();
                //stop turn
                if (stoppedTurn.get()) return false;

                if (availablePosition.get(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + rowOne + columnOne)[rowTwo][columnTwo].equals(true)) {
                    Tuple diceToMoveIndex = new Tuple(rowOne, columnOne);
                    Tuple positionIndex = new Tuple(rowTwo, columnTwo);
                    if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowTwo).get(columnTwo).getDice() == null)
                        networkType.tapWheelMove(diceToMoveIndex, positionIndex, phase, false);
                    else if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowTwo).get(columnTwo).getDice().getDiceColor().equals(selectedDiceColor)) {
                        networkType.tapWheelMove(diceToMoveIndex, positionIndex, phase, true);
                    } else {
                        System.out.println("The dice has not the same color of the one chosen before");
                        return false;
                    }
                    return true;
                } else {
                    System.out.println("You can't place this dice, ending the toolcard\n");
                    return false;
                }
            } else {
                System.out.println("Wrong position input\n");
                return false;

            }
        } else {
            System.out.println("There is no dice in the selected box\n");
        }
        return false;
    }
}
