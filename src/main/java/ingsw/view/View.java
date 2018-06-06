package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.utilities.DoubleString;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class View extends Application implements GUIUpdater {
    private String username;
    private String ipAddress;
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
     * @param primaryStage primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        if (getParameters() != null) {
            this.ipAddress = getParameters().getRaw().get(0);
        }
        this.mainStage = primaryStage;
        launchFirstGUI();
    }

    /**
     * Method that creates a client connection to the previously opened server socket
     */
    @Override
    public void deploySocketClient() {
        Client client = new Client(ipAddress, 8000);

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
     */
    @Override
    public void deployRMIClient() {
        rmiController = new RMIController();
        rmiController.connect(ipAddress);
        currentNetworkType = rmiController;
    }

    /**
     * Method that sets the current scene. Used to keep track of the current scene and exchanging data with it
     *
     * @param currentScene scene currently displayed
     */
    private void setCurrentScene(SceneUpdater currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * First GUI launcher
     */
    private void launchFirstGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        GridPane login = null;

        try {
            login = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = fxmlLoader.getController();

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
     */
    @Override
    public void launchSecondGUI(String username) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobby = null;

        try {
            lobby = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LobbyController lobbyController = fxmlLoader.getController();

        // Pass the Scene to the first layer controllers
        currentNetworkType.setSceneUpdater(lobbyController);

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

        this.username = username;
    }

    /**
     * Third GUI launcher
     */
    @Override
    public void launchThirdGUI(PatternCardNotification patternCardNotification) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/patternCardChoice.fxml"));
        Parent patternCardChoice = null;

        try {
            patternCardChoice = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PatternCardController patternCardController = fxmlLoader.getController();
        currentNetworkType.setSceneUpdater(patternCardController);
        patternCardController.setNetworkType(currentNetworkType);
        patternCardController.setApplication(this);
        mainStage.setScene(new Scene(patternCardChoice));
        mainStage.setTitle("Choose Pattern Card");
        mainStage.show();
        patternCardController.setPatternCards(patternCardNotification.patternCards);
        setCurrentScene(patternCardController);
    }

    /**
     * Fourth GUI launcher
     *
     * @param boardDataResponse
     */
    @Override
    public void launchFourthGUI(BoardDataResponse boardDataResponse) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent game = null;

        try {
            game = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }

        GameController gameController = fxmlLoader.getController();
        currentNetworkType.setSceneUpdater(gameController);
        gameController.setNetworkType(currentNetworkType);
        gameController.setApplication(this);
        mainStage.setScene(new Scene(game));
        mainStage.setTitle("Sagrada");
        mainStage.show();
        setCurrentScene(gameController);
        currentScene.loadData(boardDataResponse);
    }

    /**
     * Method that switches to RMI connection
     */
    @Override
    public void changeToRMI() {
        currentScene.setNetworkType(rmiController);
        currentNetworkType = rmiController;
    }

    /**
     * Method that switches to Socket connection
     */
    @Override
    public void changeToSocket() {
        currentScene.setNetworkType(clientController);
        currentNetworkType = clientController;

    }

    /**
     * Update the connected users to the game (not the match)
     *
     * @param connectedUsers number of users connected to the game
     */
    @Override
    public void updateConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    /**
     * Method that adds a match to the TabView if a match is created by a user
     *
     * @param matches list of matches currently active
     */
    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        this.matches = matches;
    }

    /**
     * Method that returns the player's username
     *
     * @return player's username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Method that closes the application
     */
    @Override
    public void closeApplication() {
        mainStage.close();
    }
}
