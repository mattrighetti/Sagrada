package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Player;
import ingsw.utilities.DoubleString;
import ingsw.view.nodes.ProgressForm;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

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

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    /**
     * Method that triggers the User login
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onLoginPressed(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        networkType.loginUser(username);
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

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        application.updateExistingMatches(matches);
    }

    @Override
    public void launchSecondGui(String username) {
        Platform.runLater(() -> application.launchSecondGUI(username));
    }

    @Override
    public void launchAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Username has already been taken");
            alert.setContentText("Choose another username");
            alert.showAndWait();
        });
    }

    @Override
    public void launchProgressForm() {
        Platform.runLater(() -> new ProgressForm().getDialogStage());
        System.out.println("Waiting for next UpdateViewResponse");
    }
}

