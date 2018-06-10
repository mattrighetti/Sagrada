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
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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
import java.util.*;

public class GameController implements SceneUpdater, Initializable, GameUpdater {

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

    /* Utility Elements */
    private DiceButton toolCardSelectedDice;
    private boolean placeDiceMoveDone;

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
        endTurnButtonReset();
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
    public void activateDice() {
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
    public void disableDice() {
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

    private void disableToolCards() {
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
                ButtonType no = new ButtonType("No");
                ButtonType yes = new ButtonType("Yes");
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Do you want to use " + imageView.getId().toString() + "?",  no, yes);
                alert.setTitle("Use Tool card");
                Optional<ButtonType> result = alert.showAndWait();
                System.out.println("no button pressed");
                if (result.get() == yes) {
                    networkType.useToolCard(imageView.getId());
                    System.out.println(imageView.getId());
                    disableToolCards();
                }
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
                if (windowControllerList.get(0).getSelectedDice() == null || !(windowControllerList.get(0).getSelectedDice().toString().equals(diceButtonToAdd.getDice().toString()))) {
                    setCursorDice(diceButtonToAdd.getDice());
                    windowControllerList.get(0).setSelectedDice(diceButtonToAdd.getDice());
                    windowControllerList.get(0).updateAvailablePositions(diceButtonToAdd.getDice().toString());
                } else {
                    windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
                    windowControllerList.get(0).setSelectedDice(null);
                }
            });
            diceButtonToAdd.getStyleClass().add(diceList.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70, 70);
            diceButtonToAdd.setDisable(true);
            draftPool.add(diceButtonToAdd);
            diceHorizontalBox.setSpacing(5);
            diceHorizontalBox.getChildren().add(diceButtonToAdd);
        }
    }

    private void setCursorDice(Dice dice) {
        Image cursor = new Image("/img/dice/" + dice.toString() + ".png", 90, 90, true, true);
        ImageCursor imageCursor = new ImageCursor(cursor);
        windowControllerList.get(0).getPatternCardGridPane().setCursor(imageCursor);
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
        windowController.setGameUpdater(this);
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
    public void toolCardAction(DraftedDiceToolCardResponse draftedDiceToolCardResponse) {
        Platform.runLater(() -> {
            displayDraftedDice(draftedDiceToolCardResponse.draftedDice);
            if (draftedDiceToolCardResponse.endTurn)
                windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
            windowControllerList.get(0).setSelectedDice(null);
            networkType.endTurn();
            if (placeDiceMoveDone)
                disableDice();
            endTurnButton.setDisable(true);
        });
        System.out.println("Tool card used");
    }

    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        Platform.runLater(
                () -> {
                    ButtonType increase = new ButtonType("Increase");
                    ButtonType decrease = new ButtonType("Decrease");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do currentPlayerIndex want to increase \nor decrease the dice value?", increase, decrease);
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
        switch (useToolCardResponse.phase) {
            case 1:
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
                    activateDice();

                });
                break;
            case 2:
                Platform.runLater(() -> {
                    createPopUpWindow("Flux Brush", "This is the new rolled die", "Place the dice, if it is possible").showAndWait();

                    windowControllerList.get(0).setSelectedDice(useToolCardResponse.selectedDice);
                    windowControllerList.get(0).fluxBrushMove();
                    setCursorDice(useToolCardResponse.selectedDice);
                    windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                    windowControllerList.get(0).updateAvailablePositions(useToolCardResponse.selectedDice.toString());
                    displayDraftedDice(useToolCardResponse.draftedDice);
                    boolean noAvailable = false;
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 5; k++) {
                            noAvailable = useToolCardResponse.availablePositions.get(useToolCardResponse.selectedDice)[j][k] || noAvailable;
                        }
                    }
                    if (!noAvailable) {
                        ArrayList<DiceButton> diceButtons = new ArrayList<>();
                        for (Node button : diceHorizontalBox.getChildren()) {
                            diceButtons.add((DiceButton) button);
                        }
                        for (DiceButton button : diceButtons) {
                            if (useToolCardResponse.selectedDice.toString().equals(button.getDice().toString()))
                                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        networkType.fluxBrushMove();
                                        windowControllerList.get(0).setSelectedDice(null);
                                        windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
                                    }
                                });
                        }
                    }
                });
        }
    }

    @Override
    public void toolCardAction(FluxRemoverResponse useToolCardResponse) {
        switch (useToolCardResponse.phase) {
            case 1:
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
                    activateDice();

                });
                break;
            case 2:
                Platform.runLater(() -> {
                    ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, 2, 3, 4, 5, 6);
                    dialog.setTitle("Flux Remover");
                    dialog.setHeaderText("The drafted dice is: " + useToolCardResponse.draftedDie.getDiceColor().toString() + "\nChoose the face up value");
                    dialog.showAndWait();
                    int chosenValue = dialog.getSelectedItem();
                    networkType.fluxRemoverMove(useToolCardResponse.draftedDie, chosenValue);
                });
                break;
            case 3:
                Platform.runLater(() -> {
                    displayDraftedDice(useToolCardResponse.draftedDice);
                    ArrayList<DiceButton> diceButtons = new ArrayList<>();
                    for (Node button : diceHorizontalBox.getChildren()) {
                        diceButtons.add((DiceButton) button);
                    }
                    for (DiceButton button : diceButtons) {
                        if (useToolCardResponse.draftedDie.toString().equals(button.getDice().toString()))
                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    networkType.fluxRemoverMove();
                                    windowControllerList.get(0).setSelectedDice(null);
                                    windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
                                }
                            });
                        else button.setDisable(true);
                    }
                    windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                    windowControllerList.get(0).updateAvailablePositions(useToolCardResponse.draftedDie.toString());
                    windowControllerList.get(0).setSelectedDice(useToolCardResponse.draftedDie);
                    setCursorDice(useToolCardResponse.draftedDie);
                    windowControllerList.get(0).fluxRemoverMove();
                });
        }
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
    public void toolCardAction(CopperFoilBurnisherResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Move a dice in the Pattern Card\nignoring shade restrictions");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Copper Foil Burnisher");
            alert.showAndWait();
            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);

            windowControllerList.get(0).moveDiceinPatternCard();
        });
    }

    public void toolCardAction(LathekinResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Move the dice.\nYou must pay attention to all restrictions");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Lathekin");
            alert.showAndWait();

            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
            windowControllerList.get(0).moveDiceinPatternCardLathekin();
        });
    }

    @Override
    public void toolCardAction(CorkBackedStraightedgeResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Place a dice in a spot that is not\n adjacent to another die");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Cork Backed Straightedge");
            alert.showAndWait();
            activateDice();
            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
            windowControllerList.get(0).corkBackedStraightedge();
        });
    }

    @Override
    public void toolCardAction(LensCutterResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Choose a dice from the Drafted dice,\nthen choose what dice in the round track to swipe with");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Lens Cutter");
            alert.showAndWait();

            for (DiceButton diceButton : draftPool) {
                diceButton.setOnMouseClicked(mouseEvent -> {
                    toolCardSelectedDice = diceButton;
                });
            }
            for (VBox roundVbox : roundDiceVBoxList) {
                roundVbox.setDisable(false);
            }
        });
    }


    @Override
    public void toolCardAction(EglomiseBrushResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Move a dice in the Pattern Card\nignoring color restrictions");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Eglomise Brush");
            alert.showAndWait();
            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
        });
        windowControllerList.get(0).moveDiceinPatternCard();
    }

    @Override
    public void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        ObservableList<Node> nodes = roundTrackHBox.getChildren();
        Platform.runLater(() -> roundTrackHBox.getChildren().removeAll(nodes));
        roundDiceVBoxList.clear();
        roundButtonList.clear();
        for (List<Dice> roundDice : useToolCardResponse.roundTrack) {
            addRoundInRoundTrack(roundDice);
        }
        if (placeDiceMoveDone)
            disableDice();
    }

    @Override
    public void setDraftedDice(List<Dice> dice) {
        Platform.runLater(() -> displayDraftedDice(dice));
        draftDiceButton.setDisable(true);
        activateToolCard();
        networkType.sendAck();
    }

    @Override
    public void setAvailablePosition(StartTurnNotification startTurnNotification) {
        Platform.runLater(() -> createPopUpWindow("Notification", "It's your turn", "Make a move").showAndWait());
        windowControllerList.get(0).setAvailablePosition(startTurnNotification.booleanMapGrid);
        activateDice();
        activateToolCard();
        endTurnButton.setDisable(false);
    }

    private void updateTab(Player player, Map<String, Boolean[][]> availablePositions) {
        for (WindowController windowController : windowControllerList) {
            if (windowController.getUsername().equals(player.getPlayerUsername())) {
                windowController.updatePatternCard(player.getPatternCard());
                windowController.setAvailablePosition(availablePositions);
            }
        }
    }

    @Override
    public void toolCardAction(PatternCardToolCardResponse useToolCardResponse) {
        updateTab(useToolCardResponse.player, useToolCardResponse.availablePositions);
        if (placeDiceMoveDone)
            disableDice();
    }

    @Override
    public void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        Platform.runLater(() -> {
            createPopUpWindow("Use Tool Card", "Move not allowed ", "You can't use this tool card").showAndWait();
            activateToolCard();
        });

    }

    @Override
    public void toolCardAction(RunningPliersResponse useToolCardResponse) {
        Platform.runLater(() -> {
            createPopUpWindow("Use Tool Card", "Running Pliers", "Choose and place another die").showAndWait();
            activateDice();
            windowControllerList.get(0).runningPliersMove();
        });
    }

    @Override
    public void toolCardAction(TapWheelResponse useToolCardResponse) {
        switch (useToolCardResponse.phase) {
            case 0:
                Platform.runLater(
                        () -> {
                            disableRoundTrack();
                            disableDice();
                            disableToolCards();
                            endTurnButton.setOnAction(event1 -> {
                                        windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
                                        windowControllerList.get(0).setSelectedDice(null);
                                        networkType.tapWheelMove(-1);
                                        disableDice();
                                        endTurnButton.setOnAction(event2 -> endTurnButtonReset());
                                        endTurnButton.setDisable(true);
                                        disableRoundTrack();
                                    });

                            Alert alert = createPopUpWindow("Use Tool card", "Tap Wheel", "Select a dice from the round track and\n move at most two dice of that color in the pattern");
                            alert.showAndWait();

                            for (VBox roundVbox : roundDiceVBoxList) {
                                roundVbox.setDisable(false);
                                for (Node node : roundVbox.getChildren()) {
                                    node.setDisable(false);
                                    node.setOnMouseClicked(event -> {
                                        toolCardSelectedDice = (DiceButton) node;
                                        System.out.println("the selected dice is " + toolCardSelectedDice);
                                        networkType.tapWheelMove(toolCardSelectedDice.getDice(), 0);
                                    });
                                }
                            }
                        });
                break;
            case 1:
                System.out.println("phase 1");
                Platform.runLater(
                        () -> {
                            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                            windowControllerList.get(0).activateTapWheelDice(toolCardSelectedDice.getDice().getDiceColor());
                            windowControllerList.get(0).moveDiceinPatternCardTapWheel(1, toolCardSelectedDice.getDice().getDiceColor().toString());
                        }
                );
                break;
            case 2:
                Platform.runLater(
                        () -> {
                            windowControllerList.get(0).updatePatternCardTapWheel(useToolCardResponse.patternCard);
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType no = new ButtonType("No");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Do you want to place another dice?", yes, no);
                            alert.setTitle("Use Tool card");
                            alert.setHeaderText("Tap Wheel");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (!result.isPresent()) {
                                System.err.println("No button pressed");
                            } else {
                                if (result.get() == yes) {
                                    windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                                    windowControllerList.get(0).moveDiceinPatternCardTapWheel(2, toolCardSelectedDice.getDice().getDiceColor().toString());
                                }

                                if (result.get() == no) {
                                    networkType.tapWheelMove(-1);
                                    windowControllerList.get(0).resetDiceCursorMouseEvent();
                                }
                            }
                        });
                break;
        }
    }

    private void endTurnButtonReset() {
        windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
        windowControllerList.get(0).setSelectedDice(null);
        networkType.endTurn();
        disableDice();
        placeDiceMoveDone = false;
        endTurnButton.setDisable(true);
    }

    @Override
    public void updateView(UpdateViewResponse updateViewResponse) {
        updateTab(updateViewResponse.player, updateViewResponse.availablePositions);
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
        int round = roundButtonList.size();
        Button buttonRound = new Button("Round " + (round + 1));
        buttonRound.setId(String.valueOf(round));
        roundButtonList.add(buttonRound);
        /*  Set Event:  */
        buttonRound.setOnMouseClicked(event -> {
            int id = 0;
            if (buttonRound.getId() != null) {
                id = Integer.parseInt(buttonRound.getId());
                FadeTransition fade = new FadeTransition(Duration.millis(1000));

                if (id < roundDiceVBoxList.size()) {
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
        addDiceToRoundTrack(vBox, diceToAdd);
        vBox.setVisible(false);
        vBox.setDisable(true);
        roundDiceVBoxList.add(vBox);

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
                if (toolCardSelectedDice != null)
                    for (int j = 0; j < roundDiceVBoxList.size(); j++) {
                        if (roundDiceVBoxList.get(j).equals(roundVBox)) {
                            networkType.lensCutter(j, diceButtonToAdd.getStyleClass().get(1), toolCardSelectedDice.getStyleClass().get(1));
                        }
                    }
            });
            diceButtonToAdd.getStyleClass().add(diceToAdd.get(i).toString());
            diceButtonToAdd.getStyleClass().add("diceImageSize");
            diceButtonToAdd.setMinSize(70, 70);
            roundVBox.setSpacing(5);
            roundVBox.getChildren().add(diceButtonToAdd);
            diceButtonToAdd.setDisable(false);
        }
    }

    public void disableRoundTrack() {
        for (VBox roundVbox : roundDiceVBoxList) {
            roundVbox.setDisable(false);
            for (Node node : roundVbox.getChildren()) {
                node.setDisable(true);
            }
        }
    }

    @Override
    public void setPlaceDiceMove() {
        placeDiceMoveDone = true;
    }
}