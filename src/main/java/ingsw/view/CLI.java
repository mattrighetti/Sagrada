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
import ingsw.utilities.Tuple;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CLI implements SceneUpdater {
    private AtomicBoolean moveNext = new AtomicBoolean(false);
    private AtomicBoolean gamePhase = new AtomicBoolean(false);
    int currentPlayerIndex;
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
    private List<String> toolCards = new ArrayList<>();
    private List<Dice> draftedDice;
    private Map<String,Boolean[][]> availablePosition;
    private List<List<Dice>> roundTrack = new ArrayList<>();
    private List<MoveStatus> moveHistory = new ArrayList<>();
    private AtomicBoolean toolCardUsed = new AtomicBoolean();

    CLI(String ipAddress) {
        AnsiConsole.systemInstall();
        this.scanner = new Scanner(System.in);
        this.ipAddress = ipAddress;
        toolCardUsed.set(false);
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
        System.out.print("We need to log currentPlayerIndex in now\n");
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
        availablePosition = startTurnNotification.booleanMapGrid;
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

            notMoveNext();
            do {
                int selectedMove;
                showPatternCards();
                System.out.println("It's your turn!\nChoose what move currentPlayerIndex want to do:\n");

                if (!placeDiceMove)
                    System.out.println("1 - Place dice");

                if (!toolCardUsed.get())
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
                        if (!toolCardUsed.get()) {
                            toolCardMove();
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

    private void showRoundTrack(){
        if (!roundTrack.isEmpty()) {
            for (int i = 1; i <= roundTrack.size(); i++) {
                System.out.println("Round " + i + ":\t");
                for (int j = 1; j <= roundTrack.get(i).size(); j++) {
                    System.out.println(j + " - " + roundTrack.get(i).get(j).toString() + "\t");
                }
                System.out.println("\n");
            }
        } else System.out.println("RoundTrack is empty\n");
    }


    /**
     * <h1>Display pattern card</h1>
     * <p>showPatternCards prints the Pattern cards of each player who is in the match
     * </p>
     */
    private void showPatternCards() {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerUsername().equals(username)) {
                currentPlayerIndex = i;
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
     * <p>showPatternCards prints the Pattern cards of the player selected
     * </p>
     *
     * @param player the pattern card that has to be shown belongs to this player
     */
    private void showPatternCardPlayer(Player player) {
        System.out.println("\tplayer: " + player.getPlayerUsername());
        for (int i = 0; i < player.getPatternCard().getGrid().size(); i++) {
            for (Box box : player.getPatternCard().getGrid().get(i)) {
                System.out.print(box.toString() + "\t");
            }
            System.out.print("\n\n");
        }
    }

    private void showDraftedDice() {
        for (int i = 0; i < draftedDice.size(); i++) {
            System.out.print((i + 1) + " - " + draftedDice.get(i).toString() + "\n");
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
                showPatternCardPlayer(players.get(currentPlayerIndex));
                int selectedColumn;
                int selectedRow;
                System.out.println("These are the positions where currentPlayerIndex can place the dice selected:");

                if (checkAndShowAvailablePositions(draftedDice.get(selectedDice))) {

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

                    if (availablePosition.get(draftedDice.get(selectedDice - 1).toString())[selectedRow][selectedColumn].equals(true)) {
                        currentConnectionType.placeDice(draftedDice.get(selectedDice - 1), selectedColumn, selectedRow);
                        return true;
                    } else System.out.println("Wrong position input");

                } else System.out.println("There are not available positions to place this dice\n Choose another move");

            } else System.out.println("Wrong dice input\n");

        } while (true);
    }


    private boolean checkAndShowAvailablePositions(Dice selectedDice) {
        boolean anyAvailablePosition = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (availablePosition.get(selectedDice.toString())[i][j].equals(true)) {
                    System.out.println("[" + i + "," + j + "]");
                    anyAvailablePosition = true;
                }
            }
        }
        return anyAvailablePosition;
    }

    private void updateAvailablePositions(Map<String,Boolean[][]> availablePosition){
        this.availablePosition= availablePosition;
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
            System.out.println("Choose a Tool Card:\n");
            System.out.println("0 - Exit\n");
            for (int i = 1; i <= toolCards.size(); i++) {
                System.out.println(i + " - " + toolCards.get(i-1) + "\n");
            }
            chosenToolCard = userIntegerInput() -1;

            if (chosenToolCard >= 0 && chosenToolCard < toolCards.size())
                currentConnectionType.useToolCard(toolCards.get(chosenToolCard));
            else if (chosenToolCard == 0) {
                synchronized (gamePhase) {
                    gamePhase.notify();
                }
            }

        } while (!(0 <= chosenToolCard && chosenToolCard < toolCards.size()));
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
        updatePatternCard(updateViewResponse.player);
        System.out.println(updateViewResponse.player.getPlayerUsername() + " placed a die");
        showPatternCardPlayer(updateViewResponse.player);
        synchronized (gamePhase) {
            gamePhase.notify();
        }
    }

    private void updatePatternCard(Player playerToUpload){
        for (Player player : players) {
            if(player.getPlayerUsername().equals(playerToUpload.getPlayerUsername()))
                player.getPatternCard().setGrid(playerToUpload.getPatternCard().getGrid());
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

    /* *******************TOOL CARDS METHODS****************** */

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
    }

    @Override
    public void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        roundTrack = useToolCardResponse.roundTrack;
        toolCardUsed.set(true);
        synchronized (gamePhase){
            gamePhase.notify();
        }
    }

    @Override
    public void toolCardAction(PatternCardToolCardResponse useToolCardResponse){
        updatePatternCard(useToolCardResponse.player);
        updateAvailablePositions(useToolCardResponse.availablePositions);
        toolCardUsed.set(true);
        synchronized (gamePhase){
            gamePhase.notify();
        }
    }

    @Override
    public void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        System.out.println("Pay Attention\n You can't use this tool card now\n");
        synchronized (gamePhase){
            gamePhase.notify();
        }
    }

    /**
     * GROZING PLIERS ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        int chosenInput;
        do {
            System.out.println("Grozing Pliers\n\nChoose a die:");
            showDraftedDice();
            chosenInput = userIntegerInput();
        } while (!(0 < chosenInput && chosenInput <= draftedDice.size()));
        Dice selectedDice = draftedDice.get(chosenInput-1);
        chosenInput = -1;
        boolean goOn = false;
        do {
            System.out.println("Do you want to increase or decrease its face up value?\n");
            if (selectedDice.getFaceUpValue() < 6) System.out.println("1 - Increase\n");
            if (selectedDice.getFaceUpValue() > 1) System.out.println("2 - Decrease\n");
            chosenInput = userIntegerInput();

            if (chosenInput == 1 && selectedDice.getFaceUpValue() < 6) {
                currentConnectionType.grozingPliersMove(selectedDice, true);
                goOn = true;
            }
            if (chosenInput == 2 && selectedDice.getFaceUpValue() > 1) {
                currentConnectionType.grozingPliersMove(selectedDice, false);
                goOn = true;
            }
        } while(!goOn);


    }

    /**
     * FLUX BRUSH ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(FluxBrushResponse useToolCardResponse) {

        switch (useToolCardResponse.phase) {
            case 1:

                System.out.println("Flux Brush\n\nChoose which dice has to be rolled again:\n");
                int selecteDice = selectDiceFromDrafted();
                currentConnectionType.fluxBrushMove(draftedDice.get(selecteDice));
                break;

            case 2:

                System.out.println("This is the new rolled value: " + useToolCardResponse.selectedDice.toString() + "\n");
                updateAvailablePositions(useToolCardResponse.availablePositions);
                draftedDice = useToolCardResponse.draftedDice;
                boolean dicePlaced = false;

                do {
                    showPatternCardPlayer(players.get(currentPlayerIndex));

                    if (checkAndShowAvailablePositions(useToolCardResponse.selectedDice)) {

                        int selecteRow = chooseRowIndex();
                        int selectedColumn = chooseColumnIndex();
                        if (availablePosition.get(useToolCardResponse.selectedDice.toString())[selecteRow][selectedColumn]) {
                            dicePlaced = true;
                            currentConnectionType.fluxBrushMove(useToolCardResponse.selectedDice, selecteRow, selectedColumn);
                        } else System.out.println("You can't place this dice in this position, choose another one\n");

                    } else {
                        System.out.println("This die can't be placed\n");
                        currentConnectionType.fluxBrushMove();
                    }
                } while (dicePlaced);
                break;
        }

    }

    /**
     * FLUX REMOVER ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(FluxRemoverResponse useToolCardResponse) {
        switch(useToolCardResponse.phase){
            case 1:
                System.out.println("Flux Remover\n\nChoose which dice has to be removed:\n");
                currentConnectionType.fluxRemoverMove(draftedDice.get(selectDiceFromDrafted()));
                break;
            case 2:
                System.out.println("The new drafted die has color " + useToolCardResponse.draftedDie.getDiceColor() + "\n");
                int selectedValue;
                do {
                    System.out.println("Choose its face up value\nType 1,2,3,4,5 or 6:\n");
                    selectedValue = userIntegerInput();
                } while (selectedValue < 1 || selectedValue > 6);
                currentConnectionType.fluxRemoverMove(useToolCardResponse.draftedDie, selectedValue);
                break;
            case 3:
                updateAvailablePositions(useToolCardResponse.availablePositions);
                draftedDice = useToolCardResponse.draftedDice;
                showPatternCardPlayer(players.get(currentPlayerIndex));
                boolean dicePlaced = false;

                do {
                    if (checkAndShowAvailablePositions(useToolCardResponse.draftedDie)) {
                        int selectedRow = -1, selectedColumn = -1;

                        selectedRow = chooseRowIndex();
                        selectedColumn = chooseColumnIndex();

                        if (availablePosition.get(useToolCardResponse.draftedDie.toString())[selectedRow][selectedColumn]) {
                            currentConnectionType.fluxRemoverMove(useToolCardResponse.draftedDie, selectedRow, selectedColumn);
                            dicePlaced = true;
                        } else System.out.println("Position not available, choose another one\n");

                    } else {
                        System.out.println("You can't place this die");
                        currentConnectionType.fluxRemoverMove();
                    }
                } while (dicePlaced);
        }



    }

    /**
     * Method that show to the user the drafted dice list and make him choose one.
     *
     * @return the index of the die selected from the player
     */
    public int selectDiceFromDrafted(){
        int selectedDice;
        do {
            selectedDice = -1;
            System.out.println("Choose a die:\n");
            showDraftedDice();
            selectedDice = userIntegerInput();

        } while (!(0 < selectedDice && selectedDice <= draftedDice.size()));
        return selectedDice -1;
    }

    /**
     * GRINDING STONE ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(GrindingStoneResponse useToolCardResponse) {
        int chosenInput;
        do {

            System.out.println("Grinding Stone\n\nChoose which dice has to be flipped to the opposite side:");
            showDraftedDice();
            chosenInput = userIntegerInput();

        } while (!(0 < chosenInput && chosenInput <= draftedDice.size()));

        Dice selectedDice = draftedDice.get(chosenInput-1);
        chosenInput = -1;

        currentConnectionType.grindingStoneMove(selectedDice);
    }

    private int chooseRowIndex(){
        int rowIndex = -1;
        do {
            System.out.println("Choose the row index:\n");
            rowIndex = userIntegerInput();
        } while (rowIndex < 0 || rowIndex > 4);
        return rowIndex;
    }

    private int chooseColumnIndex(){
        int columnIndex = -1;
        do {
            System.out.println("Choose the column index:\n");
            columnIndex = userIntegerInput();
        } while (columnIndex < 0 || columnIndex > 5);
        return columnIndex;
    }

    /**
     * EGLOMISE BRUSH ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(EglomiseBrushResponse useToolCardResponse) {
        updateAvailablePositions(useToolCardResponse.availablePositions);
        System.out.println("Eglomise Brush\n\nMove a dice in the Pattern Card ignoring color restrictions\n");
        int rowOne, columnOne;
        boolean dicePlaced;

        do{
            System.out.println("Choose which die you want to move\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            do {
                System.out.println("Choose the row index of the die to move:\n");
                rowOne = userIntegerInput();
            } while (rowOne < 0 || rowOne > 4);

            do {
                System.out.println("Choose the column index of the die to move:\n");
                columnOne = userIntegerInput();
            } while (columnOne < 0 || columnOne > 5);

            dicePlaced = placeDiceWithNoRestricitionsToolCard(rowOne,columnOne);

        } while (dicePlaced);

    }


    /**
     * COPPER FOIL BURNISHER ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(CopperFoilBurnisherResponse useToolCardResponse) {
        updateAvailablePositions(useToolCardResponse.availablePositions);
        System.out.println("Copper Foil Burnisher\n\nMove a dice in the Pattern Card ignoring shade restrictions\n");
        int rowOne = -1, columnOne = -1;
        boolean dicePlaced = false;

        do {

            System.out.println("Choose which die you want to move\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            do {
                System.out.println("Choose the row index of the die to move:\n");
                rowOne = userIntegerInput();
            } while (rowOne < 0 || rowOne > 4);

            do {
                System.out.println("Choose the column index of the die to move:\n");
                columnOne = userIntegerInput();
            } while (columnOne < 0 || columnOne > 5);

           dicePlaced = placeDiceWithNoRestricitionsToolCard(rowOne,columnOne);

        } while (!dicePlaced);
    }

    public boolean placeDiceWithNoRestricitionsToolCard(int rowOne, int columnOne){
        int rowTwo,columnTwo;
        if (players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice() != null) {

            System.out.println("You chose " + players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString() + "\n");
            System.out.println("These are the position in which you can place the die:\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            if (checkAndShowAvailablePositions(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice())) {

                System.out.println("Choose were you want to place the die\n");

                do {
                    System.out.println("Choose the row index:\n");
                    rowTwo = userIntegerInput();
                } while (rowTwo < 0 || rowTwo > 4);

                do {
                    System.out.println("Choose the column index:\n");
                    columnTwo = userIntegerInput();
                } while (columnTwo < 0 || columnTwo > 5);

                if (availablePosition.get(players.get(currentPlayerIndex).getPatternCard().getGrid().get(rowOne).get(columnOne).getDice().toString())[rowTwo][columnTwo].equals(true)) {
                    Tuple diceToMoveIndex = new Tuple(rowOne,columnOne);
                    Tuple positionIndex = new Tuple(rowTwo,columnTwo);
                    currentConnectionType.copperFoilBurnisherMove(diceToMoveIndex,positionIndex);
                    return true;
                } else System.out.println("Wrong position input\n");

            } else System.out.println("You can't place this dice, choose another one\n");

        }
        return false;
    }

    /**
     * LATHEKIN ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(LathekinResponse useToolCardResponse) {

    }


    /**
     * CORK BACKED STRAIGHTEDGE ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(CorkBackedStraightedgeResponse useToolCardResponse) {
        System.out.println("Cork Backed Straightedge\nPlace a dice in a spot that is not adjacent to another die ");
        int selectedDice, selectedRow, selectedColumn;
        boolean dicePlaced = false;
        do {

            selectedDice = selectDiceFromDrafted();

            System.out.println("You chose " + draftedDice.get(selectedDice).toString() + "\n");
            System.out.println("These are the position in which you can place the die:\n");
            showPatternCardPlayer(players.get(currentPlayerIndex));

            if (checkAndShowAvailablePositions(draftedDice.get(selectedDice))) {

                System.out.println("Choose were you want to place the die\n");

                do {
                    System.out.println("Choose the row index:\n");
                    selectedRow = userIntegerInput();
                } while (selectedRow < 0 || selectedRow > 4);

                do {
                    System.out.println("Choose the column index:\n");
                    selectedColumn = userIntegerInput();
                } while (selectedColumn < 0 || selectedColumn > 5);

                if (availablePosition.get(draftedDice.get(selectedDice).toString())[selectedRow][selectedColumn].equals(true)) {
                    currentConnectionType.corkBackedStraightedgeMove(draftedDice.get(selectedDice), selectedRow, selectedColumn);
                    dicePlaced = true;
                } else System.out.println("Wrong position input\n");

            } else System.out.println("You can't place this dice, choose another one\n");

        } while (!dicePlaced);

    }

    /**
     * LENS CUTTER ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(LensCutterResponse useToolCardResponse) {
        System.out.println("Lens Cutter\nSwipe a die from the drafted die with another in the Round Track\n");
        int selectedDraftedDice = selectDiceFromDrafted();
        int selectedRound = -1, selectedTrackDice = -1;

        System.out.println("Select a die from the Round Track");
        showRoundTrack();
        if (!roundTrack.isEmpty()) {
            do {
                System.out.println("Select the round:\n");

                selectedRound = userIntegerInput() - 1;
            } while (!(0 <= selectedRound && selectedRound < roundTrack.size()));

            do {
                System.out.println("Choose the die you want to swipe with:\n");
                selectedTrackDice = userIntegerInput() - 1;
            } while (!(0 <= selectedTrackDice && selectedTrackDice < roundTrack.get(selectedRound).size()));

            currentConnectionType.lensCutter(selectedRound + 1, roundTrack.get(selectedRound).get(selectedTrackDice).toString(), draftedDice.get(selectedDraftedDice).toString());

        } else System.out.println("RoundTrack is empty, you can't swipe dice");
    }


    /**
     * RUNNING PLIERS ToolCard
     *
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(RunningPliersResponse useToolCardResponse) {
        System.out.println("Running Pliers\n Draft another die\n");
        boolean dicePlaced = false;

        do {
            int selectedDice = selectDiceFromDrafted();

            showPatternCardPlayer(players.get(currentPlayerIndex));
            System.out.println("These are the available position in wich you can place the selected die\n");

            if (checkAndShowAvailablePositions(draftedDice.get(selectedDice))) {
                int selectedRow = -1, selectedColumn = -1;

                do {
                    System.out.println("Choose the row index:\n");
                    selectedRow = userIntegerInput();
                } while (selectedRow < 0 || selectedRow > 4);

                do {
                    System.out.println("Choose the column index:\n");
                    selectedColumn = userIntegerInput();
                } while (selectedColumn < 0 || selectedColumn > 5);

                if (availablePosition.get(draftedDice.get(selectedDice).toString())[selectedRow][selectedColumn].equals(true)) {
                    currentConnectionType.runningPliersMove(draftedDice.get(selectedDice), selectedRow, selectedColumn);
                    dicePlaced = true;
                } else System.out.println("Wrong position input\n");

            } else System.out.println("You cannot place this die\n");

        } while (!dicePlaced);
    }
}
