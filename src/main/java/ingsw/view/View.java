package ingsw.view;

import ingsw.controller.network.rmi.RMIController;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import ingsw.model.cards.patterncard.PatternCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class View extends Application implements GUIUpdater {
    private String username;
    private RMIController rmiController;
    private ClientController clientController;
    private Stage mainStage;
    private SceneUpdater currentScene;
    private int connectedUsers = 0;


    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * GUI starter which, in order, does create a Socket connection followed by an RMI connection and ultimately
     * launches the first GUI
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        deploySocketClient();
        deployRMIClient();
        this.mainStage = primaryStage;
        launchFirstGUI();
    }

    /**
     * Method that creates a client connection to the previously opened server socket
     * @throws IOException
     */
    public void deploySocketClient() throws IOException {
        Client client = new Client("localhost",8000);
        client.connect();
        this.clientController = new ClientController(client);
    }

    /**
     * Method that creates a RMI connection to SagradaGame which resides in the RMIHandler
     * @throws RemoteException
     */
    private void deployRMIClient() throws RemoteException {
        rmiController = new RMIController();
        rmiController.connect();
    }

    /**
     * Method that sets the current scene. Used to keep track of the current scene and exchanging data with it
     * @param currentScene
     */
    public void setCurrentScene(SceneUpdater currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * First GUI launcher
     * @throws IOException
     */
    public void launchFirstGUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        GridPane login = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        clientController.setSceneUpdater(loginController);
        rmiController.setSceneUpdater(loginController);
        loginController.setNetworkType(clientController);
        loginController.setApplication(this);
        mainStage.setScene(new Scene(login));
        mainStage.setTitle("Login");
        mainStage.show();
        setCurrentScene(loginController);
    }

    /**
     * Second GUI launcher
     * @throws IOException
     */
    @Override
    public void launchSecondGUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobby = fxmlLoader.load();
        LobbyController lobbyController = fxmlLoader.getController();
        clientController.setSceneUpdater(lobbyController);
        rmiController.setSceneUpdater(lobbyController);
        lobbyController.setNetworkType(clientController);
        lobbyController.setApplication(this);
        lobbyController.updateConnectedUsers(connectedUsers);
        mainStage.setScene(new Scene(lobby));
        mainStage.setTitle("Lobby");
        mainStage.show();
        setCurrentScene(lobbyController);
    }

    /**
     * Third GUI launcher
     * @throws IOException
     */
    @Override
    public void launchThirdGUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/patternCardChoice.fxml"));
        Parent patternCardChoise = fxmlLoader.load();
        PatternCardController patternCardContoller = fxmlLoader.getController();
        clientController.setSceneUpdater(patternCardContoller);
        rmiController.setSceneUpdater(patternCardContoller);
        patternCardContoller.setNetworkType(clientController);
        patternCardContoller.setApplication(this);
        mainStage.setScene(new Scene(patternCardChoise));
        mainStage.setTitle("Choose Pattern Card");
        mainStage.show();
        setCurrentScene(patternCardContoller);

    }

    @Override
    public void launchFourthGUI() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent game = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        clientController.setSceneUpdater(gameController);
        rmiController.setSceneUpdater(gameController);
        gameController.setNetworkType(clientController);
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
        try {
            currentScene.setNetworkType(rmiController);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to Socket connection
     */
    @Override
    public void changeToSocket() {
        try {
            currentScene.setNetworkType(clientController);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the connected users to the game (not the match)
     * @param connectedUsers
     */
    @Override
    public void updateConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    /**
     * Username setter
     * @param username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Username getter
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }
}
