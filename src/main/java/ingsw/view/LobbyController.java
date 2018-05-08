package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.socket.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class LobbyController implements SceneUpdater {

    @FXML
    private GridPane lobbyPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button joinButton;

    @FXML
    private Button createButton;

    @FXML
    private Text connectedUsersText;

    private NetworkType networkType;
    private View application;

    public void setApplication(View application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType clientController) {
        this.networkType = networkType;
    }

    @FXML
    void onCreatePressed(ActionEvent event) {
        // Crea match
        networkType.createMatch();
    }

    @FXML
    void onExitPressed(ActionEvent event) {

    }

    @FXML
    void onJoinPressed(ActionEvent event) {

    }

    @Override
    public void updateConnectedUsers(int usersConnected) {
        connectedUsersText.setText("Connected users: "+ String.valueOf(usersConnected));
    }
}

