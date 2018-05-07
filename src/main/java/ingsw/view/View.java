package ingsw.view;

import ingsw.controller.network.commands.ResponseHandler;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application implements GUIUpdater {
    private String username;
    private ResponseHandler RMITransmitter;
    private ResponseHandler SocketTransmitter;
    private ResponseHandler currentTransmitter;
    private ClientController clientController;
    private Stage mainStage;
    private int connectedUsers = 0;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        deployClient();
        this.mainStage = primaryStage;
        launchFirstGUI();
    }

    public void deployClient() throws IOException {
        Client client = new Client("localhost",8000);
        client.connect();
        this.clientController = new ClientController(client);
    }

    public void launchFirstGUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        GridPane login = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        clientController.setSceneUpdater(loginController);
        loginController.setClientController(clientController);
        loginController.setApplication(this);
        mainStage.setScene(new Scene(login));
        mainStage.setTitle("Login");
        mainStage.show();
    }

    @Override
    public void launchSecondGUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
        Parent lobby = fxmlLoader.load();
        LobbyController lobbyController = fxmlLoader.getController();
        clientController.setSceneUpdater(lobbyController);
        lobbyController.setClientController(clientController);
        lobbyController.setApplication(this);
        lobbyController.updateConnectedUsers(connectedUsers);
        mainStage.setScene(new Scene(lobby));
        mainStage.setTitle("Lobby");
        mainStage.show();
    }

    @Override
    public void launchThirdGUI() throws IOException {

    }

    @Override
    public void updateConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }
}
