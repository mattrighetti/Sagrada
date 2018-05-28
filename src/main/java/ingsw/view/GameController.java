package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.controller.network.commands.StartTurnNotification;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.view.nodes.DiceButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

    private List<ImageView> toolCardsImageViews;
    private List<ImageView> publicCardsImageViews;

    private NetworkType networkType;
    private GUIUpdater application;

    private List<Player> players;
    private List<Button> diceButton;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCardList;
    private Set<ToolCard> toolCards = new HashSet<>();
    private List<ToolCard> toolCardList;
    private List<Dice> dice;
    private List<Boolean[][]> availaiblePosition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diceButton = new ArrayList<>();
        dice = new ArrayList<>();
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
    }

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

    void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    private void loadImageViews() {
        int counter = 0;
        for (ImageView imageView : toolCardsImageViews) {
            imageView.setImage(new Image("/img/toolcards/" + toolCardList.get(counter).getName() + ".png"));
            counter++;
        }

        counter = 0;
        for (ImageView imageView : publicCardsImageViews) {
            imageView.setImage(new Image("/img/publicoc/" + publicObjectiveCardList.get(counter).getName() + ".png"));
            counter++;
        }
    }

    private void setDiceBox() {
        for (int i = 0; i < dice.size(); i++) {
            DiceButton diceButtonToAdd = new DiceButton(dice.get(i));
            diceButtonToAdd.setOnMouseClicked(event -> System.out.println("Pressed " + diceButtonToAdd.getDice().toString()));
            diceButtonToAdd.getStyleClass().add(dice.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70,70);
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
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;
        this.publicObjectiveCards = boardDataResponse.publicObjectiveCards;
        this.toolCards = boardDataResponse.toolCards;

        for (ToolCard toolCard : toolCards) {
            toolCardList.add(toolCard);
        }

        for (PublicObjectiveCard publicObjectiveCard : publicObjectiveCards) {
            publicObjectiveCardList.add(publicObjectiveCard);
        }

        loadImageViews();
        setWindowsTab();
    }

    /**
     * Method that launches a popup window that notifies the user that it's his turn and he needs to draft the dice
     */
    @Override
    public void popUpDraftNotification() {
        Platform.runLater(() -> {
            draftDiceButton.setDisable(false);
            createPopUpWindow("Notification",
                    "It's your turn",
                    "Click on Draft Dice to draft the dice").showAndWait();
        });
    }

    @Override
    public void setDraftedDice(List<Dice> diceList) {
        this.dice = diceList;

        Platform.runLater(this::setDiceBox);
        networkType.sendAck();
    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        this.availaiblePosition = startTurnNotification.booleanListGrid;
        activateDice();
    }

    private void activateDice() {
        Platform.runLater(
                () -> {
                    for (Button button : diceButton) {
                        button.setDisable(false);
                    }
                }
        );
    }

    private void disableDice() {
        Platform.runLater(
                () -> {
                    for (Button button : diceButton) {
                        button.setDisable(true);
                    }
                }
        );
    }
}
