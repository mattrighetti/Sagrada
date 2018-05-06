package ingsw.view;

import ingsw.controller.network.socket.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private RadioButton rmiButton;
    @FXML
    private RadioButton socketButton;
    @FXML
    private Button loginButton;

    ClientController clientController;
    Stage primaryStage;
    GridPane lobbyPane;

    public LoginController() {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void selectedRMI() {
        System.out.println("rmi");
    }


    public void selectedSocket() {
        System.out.println("socket");
    }

    public void onLoginPressed(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        clientController.loginUser(username);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
            Parent secondRoot = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Lobby");
            stage.setScene(new Scene(secondRoot));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }
}
