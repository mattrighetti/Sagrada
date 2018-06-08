package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.PatternCardNotification;
import ingsw.utilities.DoubleString;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.*;

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
        availableMatches = FXCollections.observableArrayList();
        matchTableView.setItems(availableMatches);
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<DoubleString, String>("firstField"));
        matchConnectedUsersColumn.setCellValueFactory(new PropertyValueFactory<DoubleString, Integer>("secondField"));
    }

    void setApplication(View application) {
        this.application = application;
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
        networkType.logoutUser();
        application.closeApplication();
    }

    @FXML
    void onJoinPressed(ActionEvent event) {
        if (matchTableView.getSelectionModel().getSelectedItem() != null) {
            networkType.joinExistingMatch(matchTableView.getSelectionModel().getSelectedItem().getFirstField());
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
    public void launchThirdGui(PatternCardNotification patternCardNotification) {
        Platform.runLater(
                () -> {
                    progressForm.getDialogStage().close();
                    application.launchThirdGUI(patternCardNotification);
                }
        );
    }

    static class ProgressForm {
        private final Stage dialogStage;
        private final VBox vBox;
        private final HBox hBox;
        private final Label text;
        final Timeline timeline = new Timeline();

        ProgressForm() {
            dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);

            text = new Label("Waiting for other players");
            text.setPadding(new Insets(10, 10, 10, 10));

            hBox = new HBox();
            hBox.getChildren().addAll(createProgressBar(), createTimeCircle());
            hBox.getStylesheets().add(getClass().getResource("/lobbyStyle.css").toExternalForm());

            vBox = new VBox();
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBox.getChildren().addAll(text, hBox);

            Scene scene = new Scene(vBox);
            dialogStage.setScene(scene);
            dialogStage.show();
        }

        Parent createProgressBar() {
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(200);
            progressBar.setLayoutY(45);
            progressBar.setStyle("-fx-accent: orange;");
            return progressBar;
        }

        Parent createTimeCircle() {
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setPrefSize(25, 25);
            timeline.setCycleCount(1);
            timeline.setOnFinished(event -> timeline.stop());
            progressIndicator.styleProperty().bind(Bindings.createStringBinding(
                    () -> {
                        final double percent = progressIndicator.getProgress();
                        final double m = (2d * percent);
                        final int n = (int) m;
                        final double f = m - n;
                        final int t = (int) (255 * f);
                        int r = 0, g = 0, b = 0;
                        switch (n) {
                            case 2:
                                r = 255;
                                g = t;
                                b = 0;
                                break;
                            case 1:
                                r = 255 - t;
                                g = 255;
                                b = 0;
                                break;
                            case 0:
                                r = 0;
                                g = 255;
                                b = 0;
                                break;
                            default:
                                break;
                        }

                        return String.format("-fx-progress-color: rgb(%d,%d,%d)", r, g, b);
                    },
                    progressIndicator.progressProperty()));
            final KeyValue kv0 = new KeyValue(progressIndicator.progressProperty(), 0);
            final KeyValue kv1 = new KeyValue(progressIndicator.progressProperty(), 1);
            final KeyFrame kf0 = new KeyFrame(Duration.ZERO, kv0);
            final KeyFrame kf1 = new KeyFrame(Duration.millis(5000), kv1);
            timeline.getKeyFrames().addAll(kf0, kf1);
            return progressIndicator;
        }

        void activateProgressBar() {
            dialogStage.show();
            timeline.play();
        }

        Stage getDialogStage() {
            return dialogStage;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}

