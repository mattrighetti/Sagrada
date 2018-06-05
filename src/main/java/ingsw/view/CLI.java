package ingsw.view;


import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.Box;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.DoubleString;
import ingsw.utilities.MoveStatus;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CLI implements SceneUpdater {
    private AtomicBoolean moveNext = new AtomicBoolean(false);
    private AtomicBoolean gamePhase = new AtomicBoolean(false);
    int you;
    private String username;
    private String ipAddress;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType currentConnectionType;
    private SceneUpdater currentScene;
    private Scanner scanner;
    private List<DoubleString> availableMatches = new ArrayList<>();
    private List<Player> players;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<String> toolCards;
    private List<Dice> draftedDice;
    private Map<String,Boolean[][]> availaiblePosition;
    private List<List<Dice>> roundTrack = new ArrayList<>();
    private List<MoveStatus> moveHistory = new ArrayList<>();


    CLI(String ipAddress) {
        AnsiConsole.systemInstall();
        this.scanner = new Scanner(System.in);
        this.ipAddress = ipAddress;
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

    private void flushScanner() {
        //scanner.next();
        System.out.flush();
    }

    /**
     * <h1>String input scanner </h1>
     * <p>Read from the System in what the user types
     * </p>
     *
     * @return the String typed by the user
     */
    private String userStringInput() {
        return scanner.nextLine();
    }

    /*
     * *
     * <h1>Integer input scanner </h1>
     * <p>Read from the System in what number the user types
     * </p>
     * @return the int typed by the user, otherwise if he insert an invalid type, return -1
     */
    private int userIntegerInput() {
        int input;
        try {
            input = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Type again");
            return -1;
        }
        return input;
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
    public void moveNext() {
        moveNext.set(true);
    }

    /**
     * <h1>Welcome view</h1>
     * <p>Ask the user to choose which connection wants to use  connect to the game
     * if RMI or Socket connection
     * </p>
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
     * <h1>Set Network Type</h1>
     * <p>
     * After the user has chosen the connection he wants to use
     * setNetworkType save to the attribute currentConnectionType the rmiController or the clientController
     * </p>
     *
     * @param currentConnectionType the instance of the subclass of networkType chosen by the user
     */
    @Override
    public void setNetworkType(NetworkType currentConnectionType) {
        this.currentConnectionType = currentConnectionType;
    }

    /**
     * <h1>Login view</h1>
     * <p>
     * Asks the user to insert the username to login.
     * Send the login request to the Server.
     * </p>
     */
    private void chooseUsernameAndLogin() {
        boolean rightUsername = false;
        System.out.print("We need to log you in now\n");
        do {
            String username;
            System.out.print("Username:\n");
            username = scanner.nextLine();
            if (!username.isEmpty()) {
                System.out.print("Ok! Your username is: " + username + "\n");
                rightUsername = true;
                currentConnectionType.loginUser(username);
            } else System.out.println("Wrong input");
        } while (!rightUsername);
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
     * <h1>Lobby View</h1>
     * showLobbyCommandsAndWait shows the commands that the user can choose before the match
     * Lets the user choose among create a new match, join a match or Show statistics and rankings
     */
    private void showLobbyCommandsAndWait() {
        new Thread(() -> {
            int selectedCommand;
            notMoveNext();
            do {
                System.out.println("You're finally logged to SagradaGame");
                flushScanner();


                System.out.println("Choose a command:\n" +
                        "1 - Create a match\n" +
                        "2 - Join an existing match\n" +
                        "3 - Show my statistics\n" +
                        "4 - Show Ranking");

                selectedCommand = userIntegerInput();

                switch (selectedCommand) {
                    case 1:
                        createMatch();
                        synchronized (gamePhase) {
                            try {
                                gamePhase.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        joinMatch();
                        moveNext();
                        break;
                    case 3:
                        showStatistics();
                        synchronized (gamePhase) {
                            try {
                                gamePhase.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 4:
                        showRanking();
                        synchronized (gamePhase) {
                            try {
                                gamePhase.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        System.err.println("Wrong input");
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
                    currentConnectionType.createMatch(matchName);
                    rightMatchName = true;
                } else {
                    System.err.println("Match name has already been taken, choose another one");
                }
            } else System.out.println("Wrong input");

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
                    currentConnectionType.joinExistingMatch(availableMatches.get(selectedMatch - 1).getFirstField());
                    System.out.println("Confirmed\nYou logged in successfully!\nWait for other players...");
                    moveNext();
                } else System.out.println("Not valid Match selected, choose another match");
            }
        } else {
            System.out.println("There are no matches. Please create a new one");
        }
    }

    private void showStatistics() {
        flushScanner();
        // TODO da implementare
        synchronized (gamePhase) {
            gamePhase.notify();
        }
    }

    private void showRanking() {
        flushScanner();
        // TODO da implementare
        synchronized (gamePhase) {
            gamePhase.notify();
        }
    }

    /**
     * <h1>Deploy Socket Client</h1>
     * <p>Method that creates a client connection to the previously opened server socket
     * </p>
     *
     * @throws IOException
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
        int chosenPatternCard;
        notMoveNext();
        while (!moveNext.get()) {
            System.out.println("Now select your Pattern Card:");


            for (int i = 0; i < patternCardNotification.patternCards.size(); i++) {
                System.out.println((i + 1) + " - "
                        + patternCardNotification.patternCards.get(i).toString());
            }
            chosenPatternCard = userIntegerInput();

            if (0 < chosenPatternCard && chosenPatternCard < (patternCardNotification.patternCards.size() + 1)) {
                currentConnectionType.choosePatternCard(patternCardNotification.patternCards.get(chosenPatternCard - 1));
                moveNext();
            } else System.out.println("Not valid Pattern Card selected, choose another one");

        }
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
        System.out.println("You're now in the game");
        loadData(boardDataResponse);

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
     * <h1>Draft notification</h1>
     * popUpDraftNotification asks the player to draft dice at the beginning of th round
     * Triggered by the server
     */
    @Override
    public void popUpDraftNotification() {
        String ack;
        notMoveNext();

        while (!moveNext.get()) {
            System.out.println("It's time to draft the dice: press Enter to draft");
            ack = userStringInput();
            currentConnectionType.draftDice();
            moveNext();
        }
    }

    @Override
    public void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        roundTrack.add(roundTrackNotification.roundTrack);
    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        availaiblePosition = startTurnNotification.booleanMapGrid;
        chooseMove();
    }

    /**
     * <h1>Turn manager</h1>
     * <p>chooseMove show to the player the moves he can do, call the moves methods
     * and and manage the turn progress
     * </p>
     */
    private void chooseMove() {
        new Thread(() -> {
            boolean placeDiceMove = false;
            boolean toolCardMove = false;

            notMoveNext();
            do {
                int selectedMove;
                displayPatternCards();
                System.out.println("It's your turn!\nChoose what move you want to do:\n");

                if (!placeDiceMove)
                    System.out.println("1 - Place dice");

                if (!toolCardMove)
                    System.out.println("2 - Use tool card");

                System.out.println("3 - Show Match Story"
                );

                System.out.println("4 - End turn");


                selectedMove = userIntegerInput();

                switch (selectedMove) {
                    case 1:
                        if (!placeDiceMove) {
                            placeDiceMove = placeDice();
                            if (placeDiceMove) {
                                synchronized (gamePhase) {
                                    try {
                                        gamePhase.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else System.out.println("You have already done this move in this turn");
                        break;
                    case 2:
                        if (!toolCardMove) {
                            toolCardMove();
                            toolCardMove = true;
                            synchronized (gamePhase) {
                                try {
                                    gamePhase.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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
                        System.err.println("Wrong input");
                }
            } while (!moveNext.get());
        }).start();
    }

    /**
     * Update the list of the moves history
     * Triggered by the server
     * @param notification the notification that contains the updated list of moves status
     */
    @Override
    public void updateMovesHistory(MoveStatusNotification notification) {
        moveHistory = notification.moveStatuses;
    }

    /**
     * Method that show to the user the moves history
     */
    private void showMoveHistory() {
        for (MoveStatus move : moveHistory) {
            System.out.println(move.toString());
        }
        String backConferm;
        System.out.println("\n Press Enter to go back to the match");
        backConferm = userStringInput();
    }


    /**
     * <h1>Display pattern card</h1>
     * <p>displayPatternCards prints the Pattern cards of each player who is in the match
     * </p>
     */
    private void displayPatternCards() {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerUsername().equals(username)) {
                you = i;
                System.out.print("\t\tYou\t\t");
            } else {
                System.out.print("\t\t" + players.get(i).getPlayerUsername() + "\t\t");
            }
        }

        System.out.print("\n\n");

        for (int i = 0; i < 4; i++) {
            for (Player player : players) {
                for (int j = 0; j < 5; j++) {
                    System.out.print(player.getPatternCard().getGrid().get(i).get(j).toString() + "\t");
                }
                System.out.print("\t\t");
            }
            System.out.print("\n");
        }

    }

    /**
     * <h1>Display pattern card</h1>
     * <p>displayPatternCards prints the Pattern cards of the player selected
     * </p>
     *
     * @param player the pattern card that has to be shown belongs to this player
     */
    private void displayPatternCardPlayer(Player player) {
        System.out.println("\tplayer: " + player.getPlayerUsername());
        for (int i = 0; i < player.getPatternCard().getGrid().size(); i++) {
            for (Box box : player.getPatternCard().getGrid().get(i)) {
                System.out.print(box.toString() + "\t");
            }
            System.out.print("\n\n");
        }
    }

    /**
     * <h1>Place dice move</h1>
     * <p>placeDice manage the placing dice move
     * Show the drafted dice and let the player to choose one
     * Show the pattern card and the available positions in which the player can place the dice
     * Finally asks the player where he wants to place the dice
     * </p>
     *
     * @return true if the dice has been placed, false if not
     */
    private boolean placeDice() {
        int selectedDice;
        do {
            selectedDice = 0;
            System.out.println("Select a dice");
            showDraftedDice();
            System.out.println("\n" + (draftedDice.size() + 1) + " - exit");
            selectedDice = userIntegerInput();

            if (selectedDice == (draftedDice.size() + 1)) return false;

            if (0 < selectedDice && selectedDice < (draftedDice.size() + 1)) {
                System.out.println("Select a position in the pattern card: \n");
                displayPatternCardPlayer(players.get(you));
                int selectedColumn;
                int selectedRow;
                System.out.println("These are the positions where you can place the dice selected:");

                if (checkAndShowAvailablePositions(selectedDice)) {

                    do {
                        System.out.println("Insert row index:");
                        selectedRow = userIntegerInput();
                        if (selectedRow < 0 || selectedRow >= 4) System.out.println("Wrong input\n");
                    } while (selectedRow < 0 || selectedRow >= 4);
                    do {
                        System.out.println("Insert column index:");
                        selectedColumn = userIntegerInput();
                        if (selectedColumn < 0 || selectedColumn >= 5) System.out.println("Wrong input\n");
                    } while (selectedColumn < 0 || selectedColumn >= 5);

                    if (availaiblePosition.get(selectedDice - 1)[selectedRow][selectedColumn].equals(true)) {
                        currentConnectionType.placeDice(draftedDice.get(selectedDice - 1), selectedColumn, selectedRow);
                        return true;
                    } else System.out.println("Wrong position input");

                } else System.out.println("There are not available positions to place this dice\n Choose another move");

            } else System.out.println("Wrong dice input\n");

        } while (true);
    }

    private void showDraftedDice() {
        for (int i = 0; i < draftedDice.size(); i++) {
            System.out.print((i + 1) + " - " + draftedDice.get(i).toString() + "\n");
        }
    }

    private boolean checkAndShowAvailablePositions(int selectedDice) {
        boolean anyAvailablePosition = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (availaiblePosition.get(selectedDice - 1)[i][j].equals(true)) {
                    System.out.println("[" + i + "," + j + "]");
                    anyAvailablePosition = true;
                }
            }
        }
        return anyAvailablePosition;
    }

    /**
     * <h1>End turn move</h1>
     * endTurnMove calls the endTurn method that send the endTurn Request to the server
     */
    private void endTurnMove() {

        currentConnectionType.endTurn();
        System.out.println("\nNext turn...\n");
    }

    /**
     * Show available Tool Cards and makes the user choose one to use
     */
    private void toolCardMove() {
        int chosenToolCard;
        do {
            System.out.println("Choose a Pattern Card:\n");
            for (int i = 0; i < toolCards.size(); i++) {
                System.out.println(i + " - " + toolCards.get(i) + "\n");
            }
            chosenToolCard = userIntegerInput();

            if (chosenToolCard > 0 && chosenToolCard < toolCards.size())
                currentConnectionType.useToolCard(toolCards.get(chosenToolCard));
            else if (chosenToolCard == 0) {
                synchronized (gamePhase) {
                    gamePhase.notify();
                }
            }

        } while (chosenToolCard < 0 || chosenToolCard > 6);
    }

    @Override
    public void setDraftedDice(List<Dice> dice) {
        this.draftedDice = dice;
        currentConnectionType.sendAck();
    }

    private void setDraftedDiceAndShow(List<Dice> dice){
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
        for (Player player : players) {
            if (player.equals(updateViewResponse.player)) {
                //TODO update pattern card
                System.out.println(player.getPlayerUsername() + " has placed a dice:\n");
                displayPatternCardPlayer(player);
            }
        }
        synchronized (gamePhase) {
            gamePhase.notify();
        }
    }

    @Override
    public void updateConnectedUsers(int usersConnected) {
        System.out.println("Connected Users: " + usersConnected);
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
        availableMatches = matches;
        synchronized (gamePhase) {
            gamePhase.notify();
        }
    }

    @Override
    public void toolCardAction(DraftPoolResponse draftPoolResponse) {
        System.out.println("Here the new drafted pool:\n");
        setDraftedDiceAndShow(draftPoolResponse.draftedDice);
    }

    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {

    }

    @Override
    public void toolCardAction(FluxBrushResponse useToolCardResponse) {

    }

    @Override
    public void toolCardAction(FluxRemoverResponse useToolCardResponse) {

    }
}
