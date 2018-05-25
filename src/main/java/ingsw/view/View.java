package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class View extends Application implements GUIUpdater {
    private String username;
    private RMIController rmiController;
    private ClientController clientController;
    private NetworkType currentNetworkType;
    private Stage mainStage;
    private SceneUpdater currentScene;
    private int connectedUsers = 0;
    private List<DoubleString> matches = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * GUI starter which, in order, does create a Socket connection followed by an RMI connection and ultimately
     * launches the first GUI
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) {
        deploySocketClient();
        deployRMIClient();
        this.mainStage = primaryStage;
        launchFirstGUI();
    }

    /**
     * Method that creates a client connection to the previously opened server socket
     *
     * @throws IOException
     */
    public void deploySocketClient() {
        Client client = new Client("localhost", 8000);

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.clientController = new ClientController(client);
        currentNetworkType = clientController;
    }

    /**
     * Method that creates a RMI connection to SagradaGame which resides in the RMIHandler
     *
     * @throws RemoteException
     */
    private void deployRMIClient() {
        rmiController = new RMIController();
        rmiController.connect();
    }

    /**
     * Method that sets the current scene. Used to keep track of the current scene and exchanging data with it
     *
     * @param currentScene
     */
    public void setCurrentScene(SceneUpdater currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * First GUI launcher
     *
     * @throws IOException
     */
    public void launchFirstGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        GridPane login = null;

        try {
            login = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = fxmlLoader.getController();

        // Pass the Scene to the first layer controllers
        clientController.setSceneUpdater(loginController);
        rmiController.setSceneUpdater(loginController);

        // Set networkType to the ViewController
        loginController.setNetworkType(clientController);

        // Pass GuiUpdater to the ViewController
        loginController.setApplication(this);

        // Load the scene
        mainStage.setScene(new Scene(login));
        mainStage.setTitle("Login");
        mainStage.show();

        // Updates CurrentScene
        setCurrentScene(loginController);
    }

    /**
     * Second GUI launcher
     *
     * @throws IOException
     */
    @Override
    public void launchSecondGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobby = null;

        try {
            lobby = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LobbyController lobbyController = fxmlLoader.getController();

        // Pass the Scene to the first layer controllers
        clientController.setSceneUpdater(lobbyController);
        rmiController.setSceneUpdater(lobbyController);

        // Set networkType to the ViewController
        lobbyController.setNetworkType(currentNetworkType);

        // Pass GuiUpdater to the ViewController
        lobbyController.setApplication(this);
        lobbyController.updateConnectedUsers(connectedUsers);
        lobbyController.updateExistingMatches(matches);

        // Load the scene
        mainStage.setScene(new Scene(lobby));
        mainStage.setTitle("Lobby");
        mainStage.show();

        // Updates CurrentScene
        setCurrentScene(lobbyController);
    }

    /**
     * Third GUI launcher
     *
     * @throws IOException
     */
    @Override
    public void launchThirdGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/patternCardChoice.fxml"));
        Parent patternCardChoice = null;

        try {
            patternCardChoice = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PatternCardController patternCardController = fxmlLoader.getController();
        clientController.setSceneUpdater(patternCardController);
        rmiController.setSceneUpdater(patternCardController);
        patternCardController.setNetworkType(currentNetworkType);
        patternCardController.setApplication(this);
        mainStage.setScene(new Scene(patternCardChoice));
        mainStage.setTitle("Choose Pattern Card");
        mainStage.show();
        setCurrentScene(patternCardController);
    }

    @Override
    public void launchFourthGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent game = null;

        try {
            game = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameController gameController = fxmlLoader.getController();
        clientController.setSceneUpdater(gameController);
        rmiController.setSceneUpdater(gameController);
        gameController.setNetworkType(currentNetworkType);
        gameController.setApplication(this);
        mainStage.setScene(new Scene(game));
        mainStage.setTitle("Choose Pattern Card");
        mainStage.show();
        setCurrentScene(gameController);
    }

    /**
     * Switch to RMI connection
     */
    @Override
    public void changeToRMI() {
        currentScene.setNetworkType(rmiController);
        currentNetworkType = rmiController;
    }

    /**
     * Switch to Socket connection
     */
    @Override
    public void changeToSocket() {
        currentScene.setNetworkType(clientController);
        currentNetworkType = clientController;

    }

    /**
     * Update the connected users to the game (not the match)
     *
     * @param connectedUsers
     */
    @Override
    public void updateConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        this.matches = matches;
    }

    /**
     * Username setter
     *
     * @param username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Username getter
     *
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }
}
