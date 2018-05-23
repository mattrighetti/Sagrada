package ingsw.view;


import ingsw.controller.network.NetworkType;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.utilities.DoubleString;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements SceneUpdater {
    private String username;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType currentConnectionType;
    private SceneUpdater currentScene;
    private Scanner scanner;
    private List<DoubleString> availableMatches = new ArrayList<>();

    CLI() {
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
        chooseUsernameAndLogin();
        showLobbyCommandsAndWait();
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

    private void askForTypeOfConnection() {
        int selectedConnection;
        boolean moveNext = false;

        System.out.println("You're now connected!\nChoose a type of connection: ");

        do {
            System.out.println("1 - RMI\n2 - Socket");
            selectedConnection = userIntegerInput();

            if (selectedConnection == 1) {
                moveNext = true;
                setNetworkType(rmiController);
                System.out.println("Alright! You selected RMI");
            } else if (selectedConnection == 2) {
                moveNext = true;
                setNetworkType(clientController);
                System.out.println("Alright! You selected Socket");
            } else {
                System.out.println("Incorrect input, try again");
            }

        } while (!moveNext);
    }

    private void chooseUsernameAndLogin() {
        String username;
        boolean moveNext = false;

        System.out.println("We need to log you in now");
        flushScanner();

        do {
            System.out.println("Username: ");
            username = userStringInput();

            try {
                if (currentConnectionType.loginUser(username)) {
                    System.out.println("Ok! Your username is: " + username);
                    moveNext = true;
                } else
                    System.err.println("Username has already been taken");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } while (!moveNext);

    }

    private void showLobbyCommandsAndWait() {
        int selectedCommand;
        boolean moveNext = false;

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
                    break;
                case 2:
                    joinMatch();
                    break;
                case 3:
                    showStatistics();
                    break;
                case 4:
                    showRanking();
                    break;
                default:
                    System.err.println("Wrong input");
                    break;
            }

        } while (!moveNext);

    }

    public void createMatch() {
        String matchName;
        boolean moveNext = false;

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
                try {
                    currentConnectionType.createMatch(matchName);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                moveNext = true;
            } else {
                System.err.println("Match name has already been taken, choose another one");
            }

        } while (!moveNext);
    }

    private void joinMatch() {
        int selectedMatch;
        boolean moveNext = false;
        flushScanner();

        while (!moveNext) {
            System.out.println("Available matches:\n");
            for (int i = 0; i < availableMatches.size(); i++) {
                System.out.println( i+1 + " - " + availableMatches.get(i).getFirstField() + "\t players:" + availableMatches.get(i).getSecondField() + "\n");
            }
            selectedMatch = userIntegerInput();

            if (0 < selectedMatch && selectedMatch < (availableMatches.size() + 1)) {
                try {
                    if (currentConnectionType.joinExistingMatch(availableMatches.get(selectedMatch - 1).getFirstField())){
                        System.out.println("Confirmed\nYou logged in successfully!\nWait for other players");
                        moveNext = true;
                    }
                    else System.out.println("Error\nYou didn't log in successfully\nRetry");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("Not valid Match selected, choose another match");
        }

        System.out.println("Waiting...");
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
