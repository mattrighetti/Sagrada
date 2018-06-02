package ingsw.view;


import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.controller.network.commands.RoundTrackNotification;
import ingsw.controller.network.commands.StartTurnNotification;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.DoubleString;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class CLI implements SceneUpdater {
    private String username;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType currentConnectionType;
    private SceneUpdater currentScene;
    private Scanner scanner;
    private List<DoubleString> availableMatches = new ArrayList<>();
    boolean moveNext;


    private List<Player> players;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<Dice> draftedDice;
    private List<Boolean[][]> availaiblePosition;
    private List<List<Dice>> roundTrack = new ArrayList<>();


    CLI() {
        AnsiConsole.systemInstall();
        this.scanner = new Scanner(System.in);
    }

    void startCLI() {
        System.out.println("Deploying Socket & RMI");

        try {
            deploySocketClient();
            deployRMIClient();
            rmiController.setSceneUpdater(this);
            clientController.setSceneUpdater(this);
        } catch (IOException e) {
            System.err.println("Error during deployment of networkTypes");
            e.printStackTrace();
        }

        askForTypeOfConnection();

    }

    private void flushScanner() {
        scanner.nextLine();
    }

    private String userStringInput() {
        return scanner.nextLine();
    }

    private int userIntegerInput() {
        return scanner.nextInt();
    }

    public void notMoveNext() {
        moveNext = false;
    }

    public void moveNext() {
        moveNext = true;
    }

    private void askForTypeOfConnection() {
        int selectedConnection;
        moveNext = false;

        System.out.println("You're now connected!\nChoose a type of connection: ");

        do {
            System.out.println("1 - RMI\n2 - Socket");
            selectedConnection = userIntegerInput();

            if (selectedConnection == 1) {
                moveNext();
                setNetworkType(rmiController);
                System.out.println("Alright! You selected RMI");
                chooseUsernameAndLogin();
            } else if (selectedConnection == 2) {
                moveNext();
                setNetworkType(clientController);
                System.out.println("Alright! You selected Socket");
                chooseUsernameAndLogin();
            } else {
                System.out.println("Incorrect input, try again");
            }

        } while (!moveNext);
    }

    private void chooseUsernameAndLogin() {
        String username;
        moveNext = false;

        System.out.println("We need to log you in now");
        flushScanner();

        do {
            System.out.println("Username: ");
            username = userStringInput();

            currentConnectionType.loginUser(username);
            System.out.println("Ok! Your username is: " + username);
            moveNext();
        } while (!moveNext);

    }

    private void showLobbyCommandsAndWait() {
        int selectedCommand;
        moveNext = false;

        System.out.println("You're finally logged to SagradaGame");
        flushScanner();

        do {
            System.out.println("Choose a command:\n" +
                    "1 - Create a match\n" +
                    "2 - Join an existing match\n" +
                    "3 - Show my statistics" +
                    "\n4 - Show Ranking");

            selectedCommand = userIntegerInput();

            switch (selectedCommand) {
                case 1:
                    createMatch();
                    notMoveNext();
                    break;
                case 2:
                    joinMatch();
                    break;
                case 3:
                    showStatistics();
                    notMoveNext();
                    break;
                case 4:
                    showRanking();
                    notMoveNext();
                    break;
                default:
                    System.err.println("Wrong input");
                    break;
            }
        } while (!moveNext);

    }

    public void createMatch() {
        String matchName;
        moveNext = false;

        System.out.println("Insert Match Name: ");
        flushScanner();

        do {
            matchName = userStringInput();

            boolean tmpBoolean = false;
            for (DoubleString doubleString : availableMatches) {
                if (doubleString.getFirstField().equals(matchName)) {
                    tmpBoolean = true;
                }
            }
            if (!tmpBoolean) {
                currentConnectionType.createMatch(matchName);
                moveNext();
            } else {
                System.err.println("Match name has already been taken, choose another one");
            }

        } while (!moveNext);
    }

    private void joinMatch() {
        int selectedMatch;
        moveNext = false;
        flushScanner();

        if (!availableMatches.isEmpty()) {
            while (!moveNext) {
                System.out.println("Available matches:\n");
                for (int i = 0; i < availableMatches.size(); i++) {
                    System.out.println(i + 1 + " - "
                            + availableMatches.get(i).getFirstField() + "\t players:"
                            + availableMatches.get(i).getSecondField() + "\n");
                }
                selectedMatch = userIntegerInput();

                if (0 < selectedMatch && selectedMatch < (availableMatches.size() + 1)) {
                    currentConnectionType.joinExistingMatch(availableMatches.get(selectedMatch - 1).getFirstField());
                    System.out.println("Confirmed\nYou logged in successfully!\nWait for other players");
                    moveNext();
                } else System.out.println("Not valid Match selected, choose another match");
            }

            System.out.println("Waiting...");
        } else {
            System.out.println("There are no matches. Please create a new one");
        }
    }

    private void showStatistics() {
        flushScanner();
        // TODO da implementare
    }

    private void showRanking() {
        flushScanner();
        // TODO da implementare
    }

    /**
     * Method that creates a client connection to the previously opened server socket
     *
     * @throws IOException
     */
    public void deploySocketClient() throws IOException {
        Client client = new Client("localhost", 8000);
        client.connect();
        this.clientController = new ClientController(client);
    }

    /**
     * Method that creates a RMI connection to SagradaGame which resides in the RMIHandler
     *
     * @throws RemoteException
     */
    public void deployRMIClient() throws RemoteException {
        rmiController = new RMIController();
        rmiController.connect();
    }

    @Override
    public void launchSecondGui(String username) {
        showLobbyCommandsAndWait();
    }

    @Override
    public void launchThirdGui(PatternCardNotification patternCardNotification) {
        int chosenPatternCard;
        moveNext = false;
        flushScanner();

        while (!moveNext) {
            System.out.println("Now select your Pattern Card:");


            for (int i = 0; i < patternCardNotification.patternCards.size(); i++) {
                System.out.println(i + 1 + " - "
                        + patternCardNotification.patternCards.get(i).toString());
            }
            chosenPatternCard = userIntegerInput();

            if (0 < chosenPatternCard && chosenPatternCard < (patternCardNotification.patternCards.size() + 1)) {
                currentConnectionType.choosePatternCard(patternCardNotification.patternCards.get(chosenPatternCard));
                moveNext();
            } else System.out.println("Not valid Pattern Card selected, choose another one");

        }
    }

    @Override
    public void launchFourthGui(BoardDataResponse boardDataResponse) {
        System.out.println("You're now in the game");
        loadData(boardDataResponse);

    }

    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;
        this.publicObjectiveCards = boardDataResponse.publicObjectiveCards;
        this.toolCards = boardDataResponse.toolCards;

    }

    @Override
    public void popUpDraftNotification() {
        String ack;
        moveNext = false;
        flushScanner();

        while (!moveNext) {
            System.out.println("It's time to draft the dice: press a key to draft");
            ack =  userStringInput();
            currentConnectionType.draftDice();
            moveNext();
        }
    }

    @Override
    public void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        roundTrack = roundTrackNotification.roundTrack;
    }
    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        availaiblePosition = startTurnNotification.booleanListGrid;
        chooseMove();
    }

    private void chooseMove() {
        int selectedMove;
        System.out.println("\nChoose what move you want to do:\n" +

                "1 - Place dice\n" +
                "2 - Use tool card\n" +
                "3 - End turn\n");

        selectedMove = userIntegerInput();

        switch (selectedMove){
            case 1 :
                placeDice();
                break;
            case 2 :
                toolCardMove();
                break;
            case 3 :
                endTurnMove();
                break;
            default :
                System.err.println("Wrong input");
        }
    }

    private void placeDice() {
        int selectedDice;
        do {
            System.out.println("Select a dice");
            for (int i = 0; i < draftedDice.size(); i++) {
                System.out.println(i + " - " + draftedDice.get(i).toString() + "\n");
            }
            selectedDice = userIntegerInput();
            if (0 < selectedDice && selectedDice < draftedDice.size()){
                System.out.println("Select the position in the pattern card: \n");
                //TODO show patern card
            } else System.out.println("Wrong input\n");

        } while ( 0 > selectedDice && selectedDice > draftedDice.size() );
    }

    private void endTurnMove(){
        currentConnectionType.endTurn();
    }

    private void toolCardMove(){
        //TODO
    }

    @Override
    public void setDraftedDice(List<Dice> dice) {
        this.draftedDice = dice;
        currentConnectionType.sendAck();
    }


    @Override
    public void updateConnectedUsers(int usersConnected) {
        System.out.println("Connected Users: " + usersConnected);
    }

    @Override
    public void setNetworkType(NetworkType currentConnectionType) {
        this.currentConnectionType = currentConnectionType;
    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        availableMatches.clear();
        availableMatches = matches;
    }

}
