package ingsw.view;

import ingsw.controller.network.socket.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void onLoginPressed(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        clientController.loginUser(username);
    }
}
