package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.socket.UserObserver;
import ingsw.model.User;
import ingsw.utilities.DoubleString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LobbyController implements SceneUpdater, Initializable {

    @FXML
    private GridPane lobbyPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button joinButton;

    @FXML
    private Button createButton;

    @FXML
    private TableView<DoubleString> matchTableView;

    @FXML
    private TableColumn matchNameColumn;

    @FXML
    private TableColumn matchConnectedUsersColumn;

    @FXML
    private TableView<?> rankingTableView;

    @FXML
    private TableColumn rankColumn;

    @FXML
    private TableColumn usernameColumn;

    @FXML
    private TableColumn winsColumn;

    @FXML
    private TableView<?> statisticsTableView;

    @FXML
    private TableColumn losesColumn;

    @FXML
    private TableColumn timePlayedColumn;

    @FXML
    private Text connectedUsersText;

    private NetworkType networkType;
    private View application;

    private ObservableList<DoubleString> availableMatches;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableMatches = FXCollections.observableArrayList(new DoubleString("MatchInit", 2));
        matchTableView.setItems(availableMatches);
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<DoubleString, String>("firstField"));
        matchConnectedUsersColumn.setCellValueFactory(new PropertyValueFactory<DoubleString, Integer>("secondField"));
    }

    public void setApplication(View application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType clientController) {
        this.networkType = clientController;
    }

    @FXML
    void onCreatePressed(ActionEvent event) throws RemoteException {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            boolean tmpBoolean = false;
            for (DoubleString doubleString : availableMatches) {
                if (doubleString.getFirstField().equals(result.get())) {
                    tmpBoolean = true;
                }
            }

            if (!tmpBoolean) networkType.createMatch(result.get());
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Match has already been taken");
                alert.setContentText("Choose another match name");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void onExitPressed(ActionEvent event) {

    }

    @FXML
    void onJoinPressed(ActionEvent event) {
        try {
            if (networkType.joinExistingMatch(matchTableView.getSelectionModel().getSelectedItem().getFirstField())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmed");
                alert.setHeaderText("You logged in successfully");
                alert.setContentText("Wait other playes");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("You didn't log in successfully");
                alert.setContentText("Retry");
                alert.showAndWait();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateConnectedUsers(int usersConnected) {
        connectedUsersText.setText("Connected users: " + usersConnected);
    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {
        availableMatches.clear();
        availableMatches.addAll(matches);
    }
}

