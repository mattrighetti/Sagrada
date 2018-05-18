package ingsw.view;

import ingsw.controller.network.NetworkType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoginController implements SceneUpdater {

    @FXML
    private GridPane loginPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ToggleButton RMIToggleButton;

    @FXML
    private ToggleGroup SocketRMIToggleGroup;

    @FXML
    private ToggleButton SocketToggleButton;

    @FXML
    private Button loginButton;

    NetworkType networkType;
    GUIUpdater application;

    public LoginController() {

    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @FXML
    void onLoginPressed(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        if(networkType.loginUser(username))
            application.launchSecondGUI();
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Username has already been taken");
            alert.setContentText("Choose another username");
            alert.showAndWait();
        }
    }

    @FXML
    void selectedRMI(ActionEvent event) {
        application.changeToRMI();
    }

    @FXML
    void selectedSocket(ActionEvent event) {
        application.changeToSocket();
    }

    @Override
    public void updateConnectedUsers(int connectedUsers) {
        application.updateConnectedUsers(connectedUsers);
    }
}

