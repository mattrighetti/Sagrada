package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.UpdateViewResponse;
import ingsw.controller.network.commands.StartTurnNotification;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.view.nodes.DiceButton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class GameController implements SceneUpdater, Initializable {

    @FXML
    private HBox diceHorizontalBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox toolCardVBox;

    @FXML
    private ImageView firstToolCardImageView;

    @FXML
    private ImageView secondToolCardImageView;

    @FXML
    private ImageView thirdToolCardImageView;

    @FXML
    private VBox publicCardVBox;

    @FXML
    private ImageView firstPublicCardImageView;

    @FXML
    private ImageView secondPublicCardImageView;

    @FXML
    private ImageView thirdPublicCardImageView;

    @FXML
    private Button showPrivateCardButton;

    @FXML
    private Button draftDiceButton;

    @FXML
    private Button endTurnButton;

    /* View Elements */
    private List<ImageView> toolCardsImageViews;
    private List<ImageView> publicCardsImageViews;

    /* Network Elements */
    private NetworkType networkType;

    /* Application Interface */
    private GUIUpdater application;

    /* Panes */
    private List<WindowController> windowControllers = new ArrayList<>();
    private List<Player> players;
    private List<Button> diceButton;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCardList;
    private Set<ToolCard> toolCards = new HashSet<>();
    private List<ToolCard> toolCardList;
    private List<Dice> diceList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diceButton = new ArrayList<>();
        diceList = new ArrayList<>();
        publicObjectiveCardList = new ArrayList<>();
        toolCardList = new ArrayList<>();
        publicCardsImageViews = new ArrayList<>();
        toolCardsImageViews = new ArrayList<>();

        /* Every button must be disables at the first launch */
        draftDiceButton.setDisable(true);
        endTurnButton.setDisable(true);

        /* Create a list used to iterate through ImageViews */
        toolCardsImageViews.add(firstToolCardImageView);
        toolCardsImageViews.add(secondToolCardImageView);
        toolCardsImageViews.add(thirdToolCardImageView);
        publicCardsImageViews.add(firstPublicCardImageView);
        publicCardsImageViews.add(secondPublicCardImageView);
        publicCardsImageViews.add(thirdPublicCardImageView);
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

    void setApplication(GUIUpdater application) {
        this.application = application;
    }

    /* UPDATE VIEWS METHODS */

    private void displayPublicObjectiveCards() {
        int counter = 0;
        for (ImageView imageView : publicCardsImageViews) {
            imageView.setImage(new Image("/img/publicoc/" + publicObjectiveCardList.get(counter).getName() + ".png"));
            counter++;
        }
    }

    private void displayToolCards() {
        int counter = 0;
        for (ImageView imageView : toolCardsImageViews) {
            imageView.setImage(new Image("/img/toolcards/" + toolCardList.get(counter).getName() + ".png"));
            counter++;
        }
    }

    private void setDiceBox() {

        if (diceHorizontalBox.getChildren().size() > 0) {
            ObservableList<Node> nodes = diceHorizontalBox.getChildren();
            diceHorizontalBox.getChildren().removeAll(nodes);
        }

        for (int i = 0; i < diceList.size(); i++) {
            DiceButton diceButtonToAdd = new DiceButton(diceList.get(i), i);
            diceButtonToAdd.setOnMouseClicked(event -> {
                windowControllers.get(0).setSelectedDice(diceButtonToAdd.getDice());
                windowControllers.get(0).updateAvailablePositions(diceButtonToAdd.getButtonIndex());
            });
            diceButtonToAdd.getStyleClass().add(diceList.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70, 70);
            diceHorizontalBox.getChildren().add(diceButtonToAdd);
        }
    }

    /**
     * Method that creates a Tab for a Player
     *
     * @param player player to create the Tab with
     */
    private void createTabOfPlayer(Player player) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/window.fxml"));
        AnchorPane windowGrid = null;

        try {
            windowGrid = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WindowController windowController = fxmlLoader.getController();
        windowController.setUsername(player.getPlayerUsername());
        windowControllers.add(windowController);
        Tab windowTab = new Tab();
        windowGrid.setMinSize(633, 666);
        windowTab.setContent(windowGrid);

        if (application.getUsername().equals(player.getPlayerUsername())) {
            windowTab.setText("You");
        } else {
            windowTab.setText(player.getPlayerUsername());
        }

        tabPane.setPadding(new Insets(0, 0, 0, 0));
        tabPane.getTabs().add(windowTab);
        windowController.setNetworkType(networkType);
        windowController.setGridPaneBackground(player.getPatternCard().getName());
    }

    /**
     * Creates a tab for every player putting the current User always first
     */
    private void setWindowsTab() {
        for (Player player : players) {
            if (player.getPlayerUsername().equals(application.getUsername())) {
                createTabOfPlayer(player);

            }
        }

        for (Player player : players) {
            if (!player.getPlayerUsername().equals(application.getUsername())) {
                createTabOfPlayer(player);
            }
        }
    }

    private Alert createPopUpWindow(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;
        this.publicObjectiveCards = boardDataResponse.publicObjectiveCards;
        this.toolCards = boardDataResponse.toolCards;

        toolCardList.addAll(toolCards);

        publicObjectiveCardList.addAll(publicObjectiveCards);

        displayPublicObjectiveCards();
        displayToolCards();
        setWindowsTab();
    }

    /**
     * Method that activates every Dice in the view
     */
    private void activateDice() {
        Platform.runLater(
                () -> {
                    for (Node diceButton : diceHorizontalBox.getChildren()) {
                        diceButton.setDisable(false);
                    }
                }
        );
    }

    /**
     * Method that disables every Dice in the view
     */
    private void disableDice() {
        Platform.runLater(
                () -> {
                    for (Node diceButton : diceHorizontalBox.getChildren()) {
                        diceButton.setDisable(true);
                    }
                }
        );
    }

    /**
     * Method that disables every ToolCard
     */
    private void activateToolCard() {
        Platform.runLater(
                () -> {
                    for (ImageView toolCard : toolCardsImageViews) {
                        toolCard.setOnMouseClicked(event -> {
                            // TODO
                        });
                        toolCard.setDisable(false);
                    }
                }
        );
    }

    private void disableToolCard() {
        Platform.runLater(
                () -> {
                    for (ImageView toolCard : toolCardsImageViews) {
                        toolCard.setDisable(true);
                    }
                }
        );
    }

    /**
     * Method that updates the pattern card of the user who's playing on every view
     * @param updateViewResponse
     */
    @Override
    public void updateView(UpdateViewResponse updateViewResponse) {
        for (WindowController windowController : windowControllers) {
            if (windowController.getUsername().equals(updateViewResponse.player.getPlayerUsername())) {
                windowController.updatePatternCard(updateViewResponse.player.getPatternCard());
            }
        }
        disableDice();
    }

    /**
     * Method that launches a popup window that notifies the user that it's his turn and he needs to draft the diceList
     */
    @Override
    public void popUpDraftNotification() {
        Platform.runLater(() -> {
            draftDiceButton.setDisable(false);
            createPopUpWindow("Notification",
                    "It's your turn",
                    "Click on Draft Dice to draft the diceList").showAndWait();
        });
    }

    @Override
    public void setDraftedDice(List<Dice> diceList) {
        this.diceList = diceList;

        Platform.runLater(this::setDiceBox);
        draftDiceButton.setDisable(true);
        networkType.sendAck();
    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        windowControllers.get(0).setAvailablePosition(startTurnNotification.booleanListGrid);
        Platform.runLater(() -> {
            createPopUpWindow("Notification",
                    "It's your turn",
                    "Make a move").showAndWait();
        });
        activateDice();
        activateToolCard();
        endTurnButton.setDisable(false);
    }
}
