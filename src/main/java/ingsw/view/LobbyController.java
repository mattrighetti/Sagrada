package ingsw.view;

import ingsw.controller.network.socket.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.awt.*;

public class LobbyController {
    @FXML
    private Button exitButton;
    @FXML
    private Button joinButton;
    @FXML
    private Button createButton;
    @FXML
    private TableView matchesTable;
    @FXML
    private TableView rankingTable;
    @FXML
    private TableView statisticsTable;

    ClientController clientController;
    Stage primarystage;


    public LobbyController(TableView tableView1) {
    }

    public void onExitPressed(ActionEvent actionEvent) {

    }

    public void onJoinPressed(ActionEvent actionEvent) {

    }

    public void onCreatePressed(ActionEvent actionEvent) {

    }
}
