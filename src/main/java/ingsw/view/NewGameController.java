package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.StartTurnNotification;
import ingsw.controller.network.commands.UpdateViewResponse;
import ingsw.model.Dice;
import ingsw.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewGameController implements SceneUpdater, Initializable {

    @FXML
    private VBox toolCardVBox;

    @FXML
    private VBox publicCardVBox;

    @FXML
    private ImageView firstPublicCardImageView;

    @FXML
    private ImageView secondPublicCardImageView;

    @FXML
    private ImageView thirdPublicCardImageView;

    @FXML
    private HBox diceHorizontalBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button draftDiceButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button showPrivateCardButton;

    @FXML
    private TableColumn<?, ?> storyTable;

    /* Network Elements */
    private NetworkType networkType;

    /* Application Interface */
    private GUIUpdater application;

    /* Model Elements */
    private List<Player> players;

    /* View Elements */
    private List<ImageView> publicCardsImageViewsList;

    /* Panes */
    private List<WindowController> windowControllerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publicCardsImageViewsList = new ArrayList<>();

        /* Every button must be disabled at first launch */
        draftDiceButton.setDisable(true);
        endTurnButton.setDisable(true);

        /* Create a list used to iterate through PublicOC */
        publicCardsImageViewsList.add(firstPublicCardImageView);
        publicCardsImageViewsList.add(secondPublicCardImageView);
        publicCardsImageViewsList.add(thirdPublicCardImageView);

    }

    @FXML
    void onDraftDicePressed(ActionEvent event) {
        networkType.draftDice();
        draftDiceButton.setDisable(true);
    }

    @FXML
    void onEndTurnPressed(ActionEvent event) {
        networkType.endTurn();
        disableDice();
        endTurnButton.setDisable(true);
    }

    /**
     * Method that pops up a widow showing the Player PatternCard
     *
     * @param event event that triggers the window
     */
    @FXML
    void onShowPrivateCardPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Private Objective Card");
        ImageView imageView = new ImageView("/img/privateoc/" + players.get(0).getPrivateObjectiveCard().getName() + ".png");
        imageView.setFitHeight(193);
        imageView.setFitWidth(137.5);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    /* SETUP METHODS */

    @Override
    public void setNetworkType(NetworkType clientController) {
        this.networkType = clientController;
    }

    void setApplication(GUIUpdater application) {
        this.application = application;
    }

    /* UPDATE VIEWS METHODS */

    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;

    }

    @Override
    public void launchFourthGui(BoardDataResponse boardDataResponse) {

    }

    @Override
    public void popUpDraftNotification() {

    }

    @Override
    public void setDraftedDice(List<Dice> dice) {

    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {

    }

    @Override
    public void updateView(UpdateViewResponse updateViewResponse) {

    }
}

