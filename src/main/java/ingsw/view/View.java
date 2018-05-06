package ingsw.view;

import ingsw.controller.network.commands.ResponseHandler;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    private String username;
    private ResponseHandler RMITransmitter;
    private ResponseHandler SocketTransmitter;
    private ResponseHandler currentTransmitter;
    private ClientController clientController;
    private Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        deployClient();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent login = fxmlLoader.load();


        LoginController loginController = fxmlLoader.getController();
        loginController.setClientController(clientController);
        loginController.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(login));
        primaryStage.setTitle("Sagrada Game");
        primaryStage.show();
    }

    public void deployClient() throws IOException {
        Client client = new Client("localhost",8000);
        client.connect();
        this.clientController = new ClientController(client, this);
    }

    public void switchToLobby() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("lobby.fxml"));

        primaryStage.setTitle("FXML Main");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
