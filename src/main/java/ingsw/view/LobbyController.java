package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BundleDataResponse;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.utilities.DoubleString;
import ingsw.utilities.TripleString;
import ingsw.view.nodes.ProgressForm;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.*;

import static javafx.application.Application.launch;

public class LobbyController implements SceneUpdater, Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button joinButton;

    @FXML
    private Button createButton;

    @FXML
    private TableView<DoubleString> matchTableView;

    @FXML
    private TableColumn<DoubleString, String> matchNameColumn;

    @FXML
    private TableColumn<DoubleString, Integer> matchConnectedUsersColumn;

    @FXML
    private TableView<TripleString> rankingTableView;

    @FXML
    private TableColumn<TripleString, String> rankColumn;

    @FXML
    private TableColumn<TripleString, String> usernameColumn;

    @FXML
    private TableColumn<TripleString, String> rankWinsColumn;

    @FXML
    private TableView<TripleString> statisticsTableView;

    @FXML
    private TableColumn<TripleString, String> statsWinsColumn;

    @FXML
    private TableColumn<TripleString, String> losesColumn;

    @FXML
    private TableColumn<TripleString, String> timePlayedColumn;

    @FXML
    private Text connectedUsersText;

    private NetworkType networkType;
    private View application;

    private ObservableList<DoubleString> availableMatches;
    private ObservableList<TripleString> statistics;
    private ObservableList<TripleString> ranking;
    private ProgressForm progressForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableMatches = FXCollections.observableArrayList();
        statistics = FXCollections.observableArrayList();
        ranking = FXCollections.observableArrayList();

        matchTableView.setItems(availableMatches);
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>(DoubleString.FIRST_FIELD));
        matchConnectedUsersColumn.setCellValueFactory(new PropertyValueFactory<>(DoubleString.SECOND_FIELD));

        rankingTableView.setItems(ranking);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.FIRST_FIELD));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.SECOND_FIELD));
        rankWinsColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.THIRD_FIELD));

        statisticsTableView.setItems(statistics);
        statsWinsColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.FIRST_FIELD));
        losesColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.SECOND_FIELD));
        timePlayedColumn.setCellValueFactory(new PropertyValueFactory<>(TripleString.THIRD_FIELD));
    }

    public static void main(String[] args) {
        launch(args);
    }

    void setApplication(View application) {
        this.application = application;
    }

    @Override
    public void disconnectUser() {

    }

    @Override
    public void setNetworkType(NetworkType clientController) {
        this.networkType = clientController;
    }

    @FXML
    void onCreatePressed(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Match");
        dialog.setHeaderText("Creating Match");
        dialog.setContentText("Please enter the match name currentPlayerIndex'd like to create");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!StringUtils.isBlank(result.get())) {
                boolean tmpBoolean = false;
                for (DoubleString doubleString : availableMatches) {
                    if (doubleString.getFirstField().equals(result.get())) {
                        tmpBoolean = true;
                    }
                }

                if (!tmpBoolean) networkType.createMatch(result.get());
            } else {
                popUpInvalidMatchName();
            }
        }
    }

    private void popUpInvalidMatchName() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Match has already been taken");
        alert.setContentText("Choose another match name");
        alert.showAndWait();
    }

    @FXML
    void onExitPressed(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void onJoinPressed(ActionEvent event) {
        if (matchTableView.getSelectionModel().getSelectedItem() != null  &&
                !matchTableView.getSelectionModel().getSelectedItem().getFirstField().equals(String.valueOf(4))) {

            networkType.joinExistingMatch(matchTableView.getSelectionModel().getSelectedItem().getFirstField());
            joinButton.setDisable(true);
            createButton.setDisable(true);
            exitButton.setDisable(true);
            historyButton.setDisable(true);
            progressForm = new ProgressForm();
            progressForm.activateProgressBar();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("You didn't log in successfully");
            alert.setContentText("Retry");
            alert.showAndWait();
        }
    }

    @FXML
    void onHistoryButtonPressed() {
        application.launchHistoryView();
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

    @Override
    public void loadLobbyData(BundleDataResponse bundleDataResponse) {
        availableMatches.clear();
        availableMatches.addAll(bundleDataResponse.matches);
        ranking.clear();
        ranking.addAll(bundleDataResponse.rankings);
        statistics.clear();
        statistics.addAll(bundleDataResponse.getUserStatsData(application.getUsername()));
        updateConnectedUsers(bundleDataResponse.connectedUsers);
    }

    void requestBundleData() {
        networkType.requestBundleData();
    }

    @Override
    public void updateRankingStatsTableView(List<TripleString> tripleStringList) {
        ranking.clear();
        ranking.addAll(tripleStringList);
    }

    @Override
    public void launchThirdGui(PatternCardNotification patternCardNotification) {
        Platform.runLater(
                () -> {
                    progressForm.getDialogStage().close();
                    application.launchThirdGUI(patternCardNotification);
                }
        );
    }

    @Override
    public void closeStage() {
        application.closeApplication();
    }
}

