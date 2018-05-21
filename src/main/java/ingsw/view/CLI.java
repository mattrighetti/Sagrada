package ingsw.view;


import ingsw.controller.network.NetworkType;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CLI implements SceneUpdater {
    private String username;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType currentConnectionType;
    private Stage mainStage;
    private SceneUpdater currentScene;
    private Scanner scanner;

    public CLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        CLI cli = new CLI(new Scanner(System.in));
        System.out.println("Deploying Socket & RMI");

        try {
            cli.deploySocketClient();
            cli.deployRMIClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("You're now connected!\nChoose a type of connection: ");
        System.out.println("1 - RMI\n2 - Socket");
        cli.askForTypeOfConnection();
        System.out.println("We need to log you in now, choose a username");
        cli.chooseUsernameAndLogin();
        System.out.println("You're finally logged to SagradaGame");
        cli.showLobbyCommandsAndWait();
    }

    public void askForTypeOfConnection() {
        int selectedConnection;
        boolean moveNext = false;

        do {
            selectedConnection = scanner.nextInt();

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

    public void chooseUsernameAndLogin() {
        String username;
        boolean moveNext = false;

        do {
            username = scanner.nextLine();

            try {
                currentConnectionType.loginUser(username);
                System.out.println("Ok! Your username is: " + username);
                moveNext = true;
            } catch (RemoteException e) {
                e.printStackTrace();
                System.err.println("Name has already been taken");
            }

        } while (!moveNext);

    }

    public void showLobbyCommandsAndWait() {
        int selectedCommand;
        boolean moveNext = false;

        do {
            System.out.println("Choose a command:\n1 - Create a match\n2 - Join an existing match\n3 - Show my statistics" +
                    "\n4 - Show Ranking");

            selectedCommand = scanner.nextInt();

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
            }

        } while (!moveNext);

    }

    public void createMatch() {
        String matchName;
        boolean moveNext = false;

        do {
            matchName = scanner.nextLine();

            try {
                currentConnectionType.createMatch(matchName);
                moveNext = true;
            } catch (RemoteException e) {
                e.printStackTrace();
                System.err.println("Match name has already been taken, choose another one");
            }

        } while (!moveNext);
    }

    public void joinMatch() {
        int selectedMatch;
        boolean moveNext = false;

        do {
            System.out.println("Available rounds:\n");
            // TODO decidi se richiedere le partite al momento o salvarle da qualche parte nella classe del CLI
            selectedMatch = scanner.nextInt();
        } while (!moveNext);

    }

    public void showStatistics() {
        // TODO da implementare
    }

    public void showRanking() {
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
    public void updateExistingMatches(String matchName) throws NoSuchMethodException {

    }
}
