package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.MoveStatus;
import ingsw.view.nodes.DiceButton;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements SceneUpdater, Initializable {

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
    private TableView<MoveStatus> movesHistoryTableView;

    @FXML
    private TableColumn<MoveStatus, String> storyTableColumn;

    @FXML
    private HBox roundTrackHBox;

    /* Network Elements */
    private NetworkType networkType;

    /* Application Interface */
    private GUIUpdater application;

    /* Model Elements */
    private List<Player> players;
    private List<DiceButton> draftPool;

    /* View Elements */
    private List<ImageView> publicCardsImageViewsList;
    private List<ImageView> toolCardsImageViewsList;

    /* Panes */
    private List<WindowController> windowControllerList;
    private List<Button> roundButtonList;
    private List<VBox> roundDiceVBoxList;
    private ObservableList<MoveStatus> playersMoves;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publicCardsImageViewsList = new ArrayList<>();
        toolCardsImageViewsList = new ArrayList<>();
        windowControllerList = new ArrayList<>();
        roundButtonList = new ArrayList<>();
        roundDiceVBoxList = new ArrayList<>();
        draftPool = new ArrayList<>();

        /* Setup moves tableView */
        playersMoves = FXCollections.observableArrayList();
        movesHistoryTableView.setItems(playersMoves);
        storyTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        /* Every button must be disabled at first launch */
        draftDiceButton.setDisable(true);
        endTurnButton.setDisable(true);

        /* Create a list used to iterate through PublicOC */
        publicCardsImageViewsList.add(firstPublicCardImageView);
        publicCardsImageViewsList.add(secondPublicCardImageView);
        publicCardsImageViewsList.add(thirdPublicCardImageView);
        toolCardsImageViewsList.add(firstToolCardImageView);
        toolCardsImageViewsList.add(secondToolCardImageView);
        toolCardsImageViewsList.add(thirdToolCardImageView);


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
                    for (ImageView toolCard : toolCardsImageViewsList) {
                        toolCard.setDisable(false);
                    }
                }
        );
    }

    private void disableToolCard() {
        Platform.runLater(
                () -> {
                    for (ImageView toolCard : toolCardsImageViewsList) {
                        toolCard.setDisable(true);
                    }
                }
        );
    }

    private Alert createPopUpWindow(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

    private void displayToolCards(List<ToolCard> toolCards) {
        int counter = 0;
        for (ImageView imageView : toolCardsImageViewsList) {
            imageView.setId(toolCards.get(counter).getName());
            imageView.setImage(new Image("/img/toolcards/" + toolCards.get(counter).getName() + ".png"));

            imageView.setOnMouseClicked(event -> {
                networkType.useToolCard(imageView.getId());
                System.out.println(imageView.getId());
            });
            imageView.setDisable(true);
            counter++;
        }
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

    private void displayDraftedDice(List<Dice> diceList) {

        if (diceHorizontalBox.getChildren().size() > 0) {
            ObservableList<Node> nodes = diceHorizontalBox.getChildren();
            diceHorizontalBox.getChildren().removeAll(nodes);
        }

        for (int i = 0; i < diceList.size(); i++) {
            DiceButton diceButtonToAdd = new DiceButton(diceList.get(i), i);
            diceButtonToAdd.setOnMouseClicked(event -> {
                windowControllerList.get(0).setSelectedDice(diceButtonToAdd.getDice());
                windowControllerList.get(0).updateAvailablePositions(diceButtonToAdd.getButtonIndex());
            });
            diceButtonToAdd.getStyleClass().add(diceList.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70, 70);
            draftPool.add(diceButtonToAdd);
            diceHorizontalBox.setSpacing(5);
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
        windowControllerList.add(windowController);
        Tab windowTab = new Tab();
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

    /* UPDATE VIEWS METHODS */

    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;

        displayPublicObjectiveCards(boardDataResponse.publicObjectiveCards);
        displayToolCards(boardDataResponse.toolCards);
        setWindowsTab();
    }

    private void displayPublicObjectiveCards(List<PublicObjectiveCard> publicObjectiveCards) {
        int counter = 0;
        for (ImageView imageView : publicCardsImageViewsList) {
            imageView.setImage(new Image("/img/publicoc/" + publicObjectiveCards.get(counter).getName() + ".png"));
            counter++;
        }
    }

    @Override
    public void popUpDraftNotification() {
        Platform.runLater(() -> {
            endTurnButton.setDisable(true);
            draftDiceButton.setDisable(false);
            createPopUpWindow("Notification",
                    "It's your turn",
                    "Click on Draft Dice to draft the diceList").showAndWait();
        });
    }

    @Override
    public void toolCardAction(DraftPoolResponse draftPoolResponse) {
        Platform.runLater(() -> {
            displayDraftedDice(draftPoolResponse.draftedDice);
            disableToolCard();
        });
        System.out.println("Tool card used");
    }

    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        Platform.runLater(
                () -> {
                    ButtonType increase = new ButtonType("Increase");
                    ButtonType decrease = new ButtonType("Decrease");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to increase \nor decrease the dice value?", increase, decrease);
                    alert.setTitle("Use Tool card");
                    alert.setHeaderText("Grozing Pliers");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (!result.isPresent()) {
                        System.out.println("no button pressed");
                    } else {
                        activateDice();

                        if ((increase == result.get())) {
                            System.out.println("increase pressed");
                            ArrayList<DiceButton> diceButtons = new ArrayList<>();
                            for (Node button : diceHorizontalBox.getChildren()) {
                                diceButtons.add((DiceButton) button);
                            }

                            for (DiceButton button : diceButtons) {
                                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        if (button.getDice().toString().indexOf('6') < 0) {
                                            System.out.println(button.getDice().toString());
                                            networkType.grozingPliersMove(button.getDice(), true);
                                        } else {
                                            Alert ErrAlert = new Alert(Alert.AlertType.ERROR, "The value SIX can't be increased");
                                            ErrAlert.showAndWait();
                                        }

                                    }
                                });
                            }


                        } else if ((decrease == result.get())) {
                            System.out.println("decrease pressed");
                            ArrayList<DiceButton> diceButtons = new ArrayList<>();
                            for (Node button : diceHorizontalBox.getChildren()) {
                                diceButtons.add((DiceButton) button);
                            }

                            for (DiceButton button : diceButtons) {
                                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        if (button.getDice().toString().indexOf('1') < 0) {
                                            System.out.println(button.getDice());
                                            networkType.grozingPliersMove(button.getDice(), false);
                                        } else {
                                            Alert ErrAlert = new Alert(Alert.AlertType.ERROR, "The value ONE can't be decreased");
                                            ErrAlert.showAndWait();
                                        }

                                    }
                                });

                            }
                        }
                    }

                });
    }

    @Override
    public void toolCardAction(FluxBrushResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Choose which dice has to be rolled again");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Flux Brush");
            alert.showAndWait();

            ArrayList<DiceButton> diceButtons = new ArrayList<>();
            for (Node button : diceHorizontalBox.getChildren()) {
                diceButtons.add((DiceButton) button);
            }

            for (DiceButton button : diceButtons) {
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println(button.getDice().toString());
                        networkType.fluxBrushMove(button.getDice());

                    }
                });
            }

        });
    }

    @Override
    public void toolCardAction(FluxRemoverResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Choose which dice has to be removed");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Flux Remover");
            alert.showAndWait();

            ArrayList<DiceButton> diceButtons = new ArrayList<>();
            for (Node button : diceHorizontalBox.getChildren()) {
                diceButtons.add((DiceButton) button);
            }

            for (DiceButton button : diceButtons) {
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println(button.getDice().toString());
                        networkType.fluxRemoverMove(button.getDice());
                    }
                });
            }

        });
    }


    public void toolCardAction(GrindingStoneResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Choose which dice has to be flipped\nto the opposite side");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Grinding Stone");
            alert.showAndWait();

            ArrayList<DiceButton> diceButtons = new ArrayList<>();
            for (Node button : diceHorizontalBox.getChildren()) {
                diceButtons.add((DiceButton) button);
            }

            for (DiceButton button : diceButtons) {
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println(button.getDice().toString());

                        networkType.grindingStoneMove(button.getDice());

                    }
                });
            }

        });
    }

    @Override
    public void setDraftedDice(List<Dice> dice) {
        Platform.runLater(() -> {
            displayDraftedDice(dice);
        });
        draftDiceButton.setDisable(true);
        activateToolCard();
        networkType.sendAck();
    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        windowControllerList.get(0).setAvailablePosition(startTurnNotification.booleanListGrid);
        Platform.runLater(() -> createPopUpWindow("Notification", "It's your turn", "Make a move").showAndWait());
        activateDice();
        activateToolCard();
        endTurnButton.setDisable(false);
    }

    @Override
    public void updateView(UpdateViewResponse updateViewResponse) {
        for (WindowController windowController : windowControllerList) {
            if (windowController.getUsername().equals(updateViewResponse.player.getPlayerUsername())) {
                windowController.updatePatternCard(updateViewResponse.player.getPatternCard());
            }
        }
        disableDice();
    }

    @Override
    public void updateRoundTrack(RoundTrackNotification roundTrackNotification) {
        addRoundInRoundTrack(roundTrackNotification.roundTrack);

    }

    @Override
    public void updateMovesHistory(MoveStatusNotification notification) {
        playersMoves.clear();
        playersMoves.addAll(notification.moveStatuses);
    }

    private void addRoundInRoundTrack(List<Dice> diceToAdd) {
        int round = roundTrackHBox.getChildren().size() + 1;
        Button buttonRound = new Button("Round" + round);
        buttonRound.setId(String.valueOf(round));
        roundButtonList.add(buttonRound);
        buttonRound.setOnMouseClicked(event -> {
            int id = 0;
            if (buttonRound.getId() != null) {
                id = Integer.parseInt(buttonRound.getId()) - 1;
                FadeTransition fade = new FadeTransition(Duration.millis(1000));

                if (id <= roundDiceVBoxList.size()) {
                    if (roundDiceVBoxList.get(id).isVisible()) {
                        roundDiceVBoxList.get(id).setVisible(false);

                    } else {
                        fade.setNode(roundDiceVBoxList.get(id));
                        fade.setFromValue(0.0);
                        fade.setToValue(1.0);
                        fade.setCycleCount(1);
                        fade.setAutoReverse(false);
                        roundDiceVBoxList.get(id).setVisible(true);
                        fade.playFromStart();
                    }
                }
            } else System.err.println("Dice has no id");
        });

        VBox vBox = new VBox();
        roundDiceVBoxList.add(vBox);
        addDiceToRoundTrack(vBox, diceToAdd);
        vBox.setVisible(false);

        VBox containerVBox = new VBox();
        containerVBox.setSpacing(10);
        containerVBox.getChildren().addAll(buttonRound, vBox);

        Platform.runLater(
                () -> {
                    roundTrackHBox.getChildren().add(containerVBox);
                }
        );
    }

    private void addDiceToRoundTrack(VBox roundVBox, List<Dice> diceToAdd) {
        for (int i = 0; i < diceToAdd.size(); i++) {
            DiceButton diceButtonToAdd = new DiceButton(diceToAdd.get(i), i);
            diceButtonToAdd.setOnMouseClicked(event -> {

            });
            diceButtonToAdd.getStyleClass().add(diceToAdd.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70, 70);
            roundVBox.setSpacing(5);
            roundVBox.getChildren().add(diceButtonToAdd);
        }
    }
}

