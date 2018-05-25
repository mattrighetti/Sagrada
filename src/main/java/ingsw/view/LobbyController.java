package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.utilities.DoubleString;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

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
    private ProgressForm progressForm;

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
            if (matchTableView.getSelectionModel().getSelectedItem() != null) {
                if (networkType.joinExistingMatch(matchTableView.getSelectionModel().getSelectedItem().getFirstField())) {
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launchThirdGui() {
        Platform.runLater(
                () -> {
                    try {
                        progressForm.getDialogStage().close();
                        application.launchThirdGUI();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
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

    static class ProgressForm {
        private final Stage dialogStage;
        private final Label headerLabel;
        private final ProgressIndicator pin = new ProgressIndicator();

        ProgressForm() {
            dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            headerLabel = new Label();
            headerLabel.setText("Waiting for other users");

            pin.setProgress(-1F);
            pin.setPadding(new Insets(10, 10, 10, 10));

            final HBox hb = new HBox();
            hb.setMinSize(200, 50);
            hb.setSpacing(15);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(headerLabel, pin);

            Scene scene = new Scene(hb);
            dialogStage.setScene(scene);
        }

        void activateProgressBar()  {
            dialogStage.show();
        }

        public Stage getDialogStage() {
            return dialogStage;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

