package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.utilities.MoveStatus;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements SceneUpdater, Initializable {

    @FXML
    private TableView<String> matchesTableView;

    @FXML
    private TableColumn<String, String> matchesTableColumn;

    @FXML
    private TableView<MoveStatus> historyTableView;

    @FXML
    private TableColumn<MoveStatus, String> historyTableColumn;

    @FXML
    private Button backButton;

    private NetworkType networkType;
    private GUIUpdater application;

    private ObservableList<MoveStatus> moveStatusList;
    private ObservableList<String> matchesPlayed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchesTableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            networkType.requestHistory(matchesTableView.getSelectionModel().getSelectedItem());
        });
    }

    /**
     * Method that populates the matches TableView with the played matches till that moment
     */
    void requestFinishedMatchesList() {
        matchesPlayed = FXCollections.observableArrayList();
        moveStatusList = FXCollections.observableArrayList();

        matchesTableView.setItems(matchesPlayed);
        historyTableView.setItems(moveStatusList);

        matchesTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        historyTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        networkType.requestFinishedMatches();
    }

    @Override
    public void showFinishedMatches(List<String> finishedMatches) {
        matchesPlayed.clear();
        matchesPlayed.addAll(finishedMatches);
    }

    @Override
    public void showSelectedMatchHistory(List<MoveStatus> history) {
        moveStatusList.clear();
        moveStatusList.addAll(history);
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    void setApplication(GUIUpdater application) {
        this.application = application;
    }


    public void onBackButtonPressed(ActionEvent actionEvent) {
        Platform.runLater(() -> application.launchSecondGUI(application.getUsername()));
    }
}
