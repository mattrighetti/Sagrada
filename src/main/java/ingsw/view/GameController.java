package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.*;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.MoveStatus;
import ingsw.utilities.RoundState;
import ingsw.view.nodes.DiceButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    @FXML
    private Button showPrivateCardButton;

    @FXML
    private Button roundOneButton;

    @FXML
    private Button roundTwoButton;

    @FXML
    private Button roundThreeButton;

    @FXML
    private Button roundFourButton;

    @FXML
    private Button roundFiveButton;

    @FXML
    private Button roundSixButton;

    @FXML
    private Button roundSevenButton;

    @FXML
    private Button roundEightButton;

    @FXML
    private Button roundNineButton;

    @FXML
    private Button roundTenButton;

    @FXML
    private TableView<MoveStatus> movesHistoryTableView;

    @FXML
    private TableColumn<MoveStatus, String> storyTableColumn;

    @FXML
    private HBox roundTrackDiceHBox;

    /* Network Elements */
    private NetworkType networkType;

    /* Application Interface */
    private GUIUpdater application;

    /* Model Elements */
    private List<Player> players;
    private List<DiceButton> draftPool;
    private List<List<Dice>> roundTrackDice;

    /* View Elements */
    private List<ImageView> publicCardsImageViewsList;
    private List<ImageView> toolCardsImageViewsList;

    /* Panes */
    private List<WindowController> windowControllerList;
    private List<Button> roundTrackButtonList;
    private ObservableList<MoveStatus> playersMoves;

    /* Utility Elements */
    private int currentRound;
    private DiceButton toolCardSelectedDice;
    private RoundState roundState;
    private int selectedRoundTrack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publicCardsImageViewsList = new ArrayList<>();
        toolCardsImageViewsList = new ArrayList<>();
        windowControllerList = new ArrayList<>();
        roundTrackButtonList = new ArrayList<>();
        draftPool = new ArrayList<>();
        roundTrackDice = new ArrayList<>();

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

        roundOneButton.setId("0");
        roundTwoButton.setId("1");
        roundThreeButton.setId("2");
        roundFourButton.setId("3");
        roundFiveButton.setId("4");
        roundSixButton.setId("5");
        roundSevenButton.setId("6");
        roundEightButton.setId("7");
        roundNineButton.setId("8");
        roundTenButton.setId("9");

        roundOneButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundOneButton.getId())), "");
            selectedRoundTrack = 0;
        });

        roundTwoButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundTwoButton.getId())), "");
            selectedRoundTrack = 1;
        });

        roundThreeButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundThreeButton.getId())), "");
            selectedRoundTrack = 2;
        });

        roundFourButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundFourButton.getId())), "");
            selectedRoundTrack = 3;
        });

        roundFiveButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundFiveButton.getId())), "");
            selectedRoundTrack = 4;
        });

        roundSixButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundSixButton.getId())), "");
            selectedRoundTrack = 5;
        });

        roundSevenButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundSevenButton.getId())), "");
            selectedRoundTrack = 6;
        });

        roundEightButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundEightButton.getId())), "");
            selectedRoundTrack = 7;
        });

        roundNineButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundNineButton.getId())), "");
            selectedRoundTrack = 8;
        });

        roundTenButton.setOnMouseClicked(event -> {
            roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
            showDiceInRoundTrack(roundTrackDice.get(Integer.parseInt(roundTenButton.getId())), "");
            selectedRoundTrack = 9;
        });

        roundTrackButtonList.add(roundOneButton);
        roundTrackButtonList.add(roundTwoButton);
        roundTrackButtonList.add(roundThreeButton);
        roundTrackButtonList.add(roundFourButton);
        roundTrackButtonList.add(roundFiveButton);
        roundTrackButtonList.add(roundSixButton);
        roundTrackButtonList.add(roundSevenButton);
        roundTrackButtonList.add(roundEightButton);
        roundTrackButtonList.add(roundNineButton);
        roundTrackButtonList.add(roundTenButton);

        currentRound = 0;

        roundState = RoundState.NOT_YOUR_TURN;
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to use " + imageView.getId().toString() + "?", no, yes);
                alert.setTitle("Use Tool card");
                Optional<ButtonType> result = alert.showAndWait();
                System.out.println("no button pressed");
                if (result.get() == yes) {
                    networkType.useToolCard(imageView.getId());
                    clearSelectedDice();
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
                    clearSelectedDice();
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
        windowControllerList.get(0).setCursorDice(dice.toString());
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
        windowController.updatePatternCard(player.getPatternCard());
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
    public void timeOut(TimeOutResponse timeOutResponse) {
        Platform.runLater(() -> {

            if (timeOutResponse.toolCardMoveActive) {
                this.roundTrackDice = timeOutResponse.roundTrack;
                roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
                displayDraftedDice(timeOutResponse.draftedDice);
                windowControllerList.get(0).updatePatternCard(timeOutResponse.currentPlayer.getPatternCard());
            }
            disableCommandsAndReset();
            endTurnButton.setOnMouseClicked(event -> endTurnButtonReset());
            createPopUpWindow("Time Out", "Timer ended", "You've been using too much time for your moves\nYour turn is ended").showAndWait();
        });
    }

    @Override
    public void endedTurn() {
        disableCommandsAndReset();
    }

    @Override
    public void setAvailablePositions(Map<String, Boolean[][]> availablePositions) {
        windowControllerList.get(0).setAvailablePosition(availablePositions);
    }

    private void endTurnButtonReset() {
        disableCommandsAndReset();
        networkType.endTurn(application.getUsername());
    }

    private void disableCommandsAndReset() {
        windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
        windowControllerList.get(0).setSelectedDice(null);
        disableDice();
        disableToolCards();
        roundState = RoundState.NOT_YOUR_TURN;
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

    @Override
    public void setDraftedDice(List<Dice> dice) {
        Platform.runLater(() -> displayDraftedDice(dice));
        draftDiceButton.setDisable(true);
        networkType.sendAck();
    }

    @Override
    public void startTurn(StartTurnNotification startTurnNotification) {
        Platform.runLater(() -> {
            createPopUpWindow("Notification", "It's your turn", "Make a move").showAndWait();
            windowControllerList.get(0).setAvailablePosition(startTurnNotification.booleanMapGrid);
            roundState = RoundState.YOUR_TURN;
            activateCommands();
        });
    }

    private void activateCommands() {
        activateDice();
        activateToolCard();
        endTurnButton.setDisable(false);
    }

    /**
     * Clear the selected dice
     * sets null the attribute selectedDice in the WindowController
     * sets the Default Cursor
     */
    private void clearSelectedDice() {
        windowControllerList.get(0).setSelectedDice(null);
        windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
    }

    private void updateTab(Player player, Map<String, Boolean[][]> availablePositions) {
        for (WindowController windowController : windowControllerList) {
            if (windowController.getUsername().equals(player.getPlayerUsername())) {
                windowController.updatePatternCard(player.getPatternCard());
                windowController.setAvailablePosition(availablePositions);
            }
        }
    }

    private void addRoundInRoundTrack(List<Dice> diceToAdd) {
        currentRound++;
        roundTrackDice.add(diceToAdd);
        roundTrackButtonList.get(currentRound - 1).getStyleClass().add("roundtrack-button");
        roundTrackButtonList.get(currentRound - 1).setDisable(false);
    }

    private void showDiceInRoundTrack(List<Dice> diceToAdd, String toolCard) {
        for (int i = 0; i < diceToAdd.size(); i++) {

            DiceButton diceButtonToAdd = new DiceButton(diceToAdd.get(i), i);

            switch (toolCard) {
                case "LensCutter":
                    diceButtonToAdd.setOnMouseClicked(event -> {
                        if (toolCardSelectedDice != null)
                            networkType.lensCutter(selectedRoundTrack, diceButtonToAdd.getStyleClass().get(1), toolCardSelectedDice.getStyleClass().get(1));
                    });
                    break;
                case "TapWheel":
                    diceButtonToAdd.setOnMouseClicked(event -> {
                        toolCardSelectedDice = diceButtonToAdd;
                        System.out.println("the selected dice is " + toolCardSelectedDice);
                        networkType.tapWheelMove(toolCardSelectedDice.getDice(), 0);
                        disableRoundTrack();
                    });
                    break;
                default:
                    break;
            }

            diceButtonToAdd.getStyleClass().add(diceToAdd.get(i).toString());
            diceButtonToAdd.getStyleClass().add("roundTrackImageSize");
            diceButtonToAdd.setMinSize(60, 60);
            roundTrackDiceHBox.setSpacing(5);
            roundTrackDiceHBox.getChildren().add(diceButtonToAdd);
        }
    }

    public void disableRoundTrack() {
        roundTrackDiceHBox.setDisable(true);
    }

    private void activateRoundTrack() {
        roundTrackDiceHBox.setDisable(false);
    }

    @Override
    public void setPlaceDiceMove() {
        roundState = RoundState.DICE_PLACED;
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
    public void showLostNotification(int totalScore) {
        Platform.runLater(() -> {
            createPopUpWindow("Match has ended", "You lost :(", "Your Score: " + totalScore).showAndWait();
            application.launchSecondGUI(application.getUsername());
        });
    }

    @Override
    public void showWinnerNotification(int totalScore) {
        Platform.runLater(() -> {
            createPopUpWindow("Match has ended", "You won!", "Your Score: " + totalScore).showAndWait();
            application.launchSecondGUI(application.getUsername());
        });

    }

    private boolean checkAvailablePositions(Boolean[][] availablePositions) {
        boolean existsAvailablePosition = false;
        for (int i = 0; i < availablePositions.length; i++) {
            for (int j = 0; j < availablePositions[i].length; j++) {
                existsAvailablePosition = availablePositions[i][j] || existsAvailablePosition;
            }

        }
        return existsAvailablePosition;
    }


    /* **************TOOL CARDS************* */


    /**
     * @param draftedDiceToolCardResponse
     */
    @Override
    public void toolCardAction(DraftedDiceToolCardResponse draftedDiceToolCardResponse) {
        Platform.runLater(() -> {
            displayDraftedDice(draftedDiceToolCardResponse.draftedDice);
            if (roundState != RoundState.YOUR_TURN) {
                disableDice();
                endTurnButton.setDisable(true);
            } else activateDice();


            if (draftedDiceToolCardResponse.endTurn && !roundState.equals(RoundState.NOT_YOUR_TURN))
                endTurnButtonReset();
        });
    }

    @Override
    public void toolCardAction(RoundTrackToolCardResponse useToolCardResponse) {
        ObservableList<Node> nodesDice = roundTrackDiceHBox.getChildren();
        Platform.runLater(() -> roundTrackDiceHBox.getChildren().removeAll(nodesDice));

        roundTrackDice = useToolCardResponse.roundTrack;
        for (Button button : roundTrackButtonList) {
            int selectedRound = Integer.parseInt(button.getId());
            button.setOnMouseClicked(event -> {
                roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
                showDiceInRoundTrack(roundTrackDice.get(selectedRound), "");
                selectedRoundTrack = selectedRound;
            });
        }

        if (roundState != RoundState.YOUR_TURN) {
            disableDice();
            endTurnButton.setDisable(true);
        } else activateDice();

        disableRoundTrack();
    }


    @Override
    public void toolCardAction(PatternCardToolCardResponse useToolCardResponse) {
        updateTab(useToolCardResponse.player, useToolCardResponse.availablePositions);
        if (roundState != RoundState.YOUR_TURN) {
            disableDice();
            endTurnButton.setDisable(true);
        } else activateDice();
    }

    @Override
    public void toolCardAction(AvoidToolCardResponse useToolCardResponse) {
        Platform.runLater(() -> {
            if (useToolCardResponse.playerTokens >= 0) {
                createPopUpWindow("Use Tool Card", "Move not allowed", "You can't use this tool card, you haven't got enough favor tokens\n You have " + useToolCardResponse.playerTokens + " tokens").showAndWait();
            } else {
                createPopUpWindow("Use Tool Card", "Move not allowed ", "You can't use this tool card").showAndWait();
                activateToolCard();
            }
        });

    }

    /**
     * COPPER FOIL BURNISHER
     * <p>
     * Tool Card that makes move a die in the Pattern Card in another position
     * ignoring the shade restrictions
     *
     * @param useToolCardResponse response with the available positions to place dice with specific restriction for this tool card
     */
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

    /**
     * CORK BACKED STRAIGHTEDGE
     * <p>
     * Tool Card that makes place a die in a spot that is not adjacent to another die
     *
     * @param useToolCardResponse response with the available positions to place dice with specific restriction for this tool card
     */
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

    /**
     * EGLOMISE BRUSH
     * <p>
     * Tool Card that makes move a die in the Pattern Card in another position
     * ignoring the color restrictions
     *
     * @param useToolCardResponse response with the available positions to place dice with specific restriction for this tool card
     */
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

    /**
     * FLUX BRUSH
     * <p>
     * Tool Card that makes choose a die to be rolled again, than makes place it
     * if it is possible, otherwise let place it in the drafted pool
     *
     * @param useToolCardResponse
     */
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
                                clearSelectedDice();

                            }
                        });
                    }
                    activateDice();

                });
                break;
            case 2:
                Platform.runLater(() -> {
                    createPopUpWindow("Flux Brush", "This is the new rolled die", "Place the dice, if it is possible").showAndWait();

                    clearSelectedDice();
                    windowControllerList.get(0).setSelectedDice(useToolCardResponse.selectedDice);
                    setCursorDice(useToolCardResponse.selectedDice);
                    windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                    windowControllerList.get(0).updateAvailablePositions(useToolCardResponse.selectedDice.toString());
                    displayDraftedDice(useToolCardResponse.draftedDice);
                    windowControllerList.get(0).fluxBrushMove();

                    boolean noAvailable = false;

                    for (int j = 0; j < useToolCardResponse.availablePositions.get(useToolCardResponse.selectedDice.toString()).length; j++) {
                        for (int k = 0; k < useToolCardResponse.availablePositions.get(useToolCardResponse.selectedDice.toString())[j].length; k++) {
                            noAvailable = useToolCardResponse.availablePositions.get(useToolCardResponse.selectedDice.toString())[j][k] || noAvailable;
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

                    } else windowControllerList.get(0).fluxBrushMove();
                });

        }
    }

    /**
     * FLUX REMOVER
     * <p>
     * Tool Card that makes choose a die to be removed from the drafted pool
     * then, received a new one from the dice bag, makes choose its value
     * and place it in the pattern, if it is possible
     * otherwise let place it in the drafted pool
     *
     * @param useToolCardResponse
     */
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
                        button.setOnMouseClicked(event -> {
                            System.out.println(button.getDice().toString());
                            networkType.fluxRemoverMove(button.getDice());
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

                    if (useToolCardResponse.availablePositions.get(useToolCardResponse.draftedDie.toString()) != null) {
                        if (!checkAvailablePositions(useToolCardResponse.availablePositions.get(useToolCardResponse.draftedDie.toString()))) {
                            for (Node button : diceHorizontalBox.getChildren()) {
                                diceButtons.add((DiceButton) button);
                            }
                            for (DiceButton button : diceButtons) {
                                if (useToolCardResponse.draftedDie.toString().equals(button.getDice().toString())) {
                                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            networkType.fluxRemoverMove();
                                            windowControllerList.get(0).setSelectedDice(null);
                                            windowControllerList.get(0).getPatternCardGridPane().setCursor(Cursor.DEFAULT);
                                        }
                                    });
                                    button.setDisable(false);
                                } else button.setDisable(true);
                            }
                        } else disableDice();
                    } else System.out.println("Error in available position Flux Remover");
                    windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                    windowControllerList.get(0).updateAvailablePositions(useToolCardResponse.draftedDie.toString());
                    windowControllerList.get(0).setSelectedDice(useToolCardResponse.draftedDie);
                    setCursorDice(useToolCardResponse.draftedDie);
                    windowControllerList.get(0).fluxRemoverMove();
                });
                break;
            default:
                break;
        }
    }

    /*
      GRINDING STONE

      Tool Card that makes choose a die to flipped to the opposite side.
     */

    /**
     * @param useToolCardResponse empty response
     */
    @Override
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
            activateDice();

        });
    }

    /*
       GROZING PLIERS

       Toolcard that makes you choose if you want to increase or decrease the value of one die
     */
    @Override
    public void toolCardAction(GrozingPliersResponse useToolCardResponse) {
        Platform.runLater(
                () -> {
                    switch (useToolCardResponse.restrictions) {
                        case "one":
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You can only increase values");
                            alert.setTitle("Use Tool card");
                            alert.setHeaderText("Grozing Pliers");
                            alert.showAndWait();

                            activateDice();

                            System.out.println("increase pressed");
                            ArrayList<DiceButton> diceButtons = new ArrayList<>();
                            for (Node button : diceHorizontalBox.getChildren()) {
                                diceButtons.add((DiceButton) button);
                            }

                            for (DiceButton button : diceButtons) {
                                button.setOnMouseClicked(event -> {
                                    System.out.println(button.getDice());
                                    networkType.grozingPliersMove(button.getDice(), true);
                                });
                            }
                            break;
                        case "six":
                            alert = new Alert(Alert.AlertType.INFORMATION, "You can only decrease values");
                            alert.setTitle("Use Tool card");
                            alert.setHeaderText("Grozing Pliers");
                            alert.showAndWait();

                            activateDice();

                            System.out.println("decrease pressed");
                            diceButtons = new ArrayList<>();
                            for (Node button : diceHorizontalBox.getChildren()) {
                                diceButtons.add((DiceButton) button);
                            }

                            for (DiceButton button : diceButtons) {
                                button.setOnMouseClicked(event -> {
                                    System.out.println(button.getDice());
                                    networkType.grozingPliersMove(button.getDice(), false);
                                });
                            }
                            break;
                        case "none":
                            ButtonType increase = new ButtonType("Increase");
                            ButtonType decrease = new ButtonType("Decrease");
                            alert = new Alert(Alert.AlertType.INFORMATION, "Do currentPlayerIndex want to increase \nor decrease the dice value?", increase, decrease);
                            alert.setTitle("Use Tool card");
                            alert.setHeaderText("Grozing Pliers");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (!result.isPresent()) {
                                System.out.println("no button pressed");
                            } else {
                                activateDice();

                                if ((increase == result.get())) {
                                    System.out.println("increase pressed");
                                    diceButtons = new ArrayList<>();
                                    for (Node button : diceHorizontalBox.getChildren()) {
                                        diceButtons.add((DiceButton) button);
                                    }

                                    for (DiceButton button : diceButtons) {
                                        button.setOnMouseClicked(event -> {
                                            if (button.getDice().toString().indexOf('6') < 0) {
                                                System.out.println(button.getDice().toString());
                                                networkType.grozingPliersMove(button.getDice(), true);
                                            } else {
                                                Alert errAlert = new Alert(Alert.AlertType.ERROR, "The value SIX can't be increased");
                                                errAlert.showAndWait();
                                            }
                                        });
                                    }
                                } else if ((decrease == result.get())) {
                                    System.out.println("decrease pressed");
                                    diceButtons = new ArrayList<>();
                                    for (Node button : diceHorizontalBox.getChildren()) {
                                        diceButtons.add((DiceButton) button);
                                    }

                                    for (DiceButton button : diceButtons) {
                                        button.setOnMouseClicked(event -> {
                                            if (button.getDice().toString().indexOf('1') < 0) {
                                                System.out.println(button.getDice());
                                                networkType.grozingPliersMove(button.getDice(), false);
                                            } else {
                                                Alert errAlert = new Alert(Alert.AlertType.ERROR, "The value ONE can't be decreased");
                                                errAlert.showAndWait();
                                            }
                                        });
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                });
    }


    /**
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(LathekinResponse useToolCardResponse) {
        Platform.runLater(() -> {
            disableDice();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Move the dice.\nYou must pay attention to all restrictions");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Lathekin");
            alert.showAndWait();

            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
            windowControllerList.get(0).updatePatternCard(useToolCardResponse.patternCard);
            windowControllerList.get(0).enableDice(useToolCardResponse.patternCard);
            windowControllerList.get(0).moveDiceinPatternCardLathekin();
        });
    }

    /*
        LENS CUTTER
        Toolcard that makes you choose a die from the drafted pool to swipe up with and another one in roundtrack.
     */

    @Override
    public void toolCardAction(LensCutterResponse useToolCardResponse) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Choose a dice from the Drafted dice,\nthen choose what dice in the round track to swipe with");
            alert.setTitle("Use Tool card");
            alert.setHeaderText("Lens Cutter");
            alert.showAndWait();

            Platform.runLater(() -> roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren()));
            activateRoundTrack();

            for (Button button : roundTrackButtonList) {
                int selectedRound = Integer.parseInt(button.getId());
                button.setOnMouseClicked(event -> {
                    roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
                    showDiceInRoundTrack(roundTrackDice.get(selectedRound), "LensCutter");
                    selectedRoundTrack = selectedRound;
                });
            }

            for (DiceButton diceButton : draftPool) {
                diceButton.setOnMouseClicked(mouseEvent -> toolCardSelectedDice = diceButton);
            }
            activateDice();
        });
    }

    /*
        RUNNING PLIERS
        Toolcard that makes to choose a die and place it before the end of the first turn
     */

    /**
     * @param useToolCardResponse
     */
    @Override
    public void toolCardAction(RunningPliersResponse useToolCardResponse) {
        Platform.runLater(() -> {
            createPopUpWindow("Use Tool Card", "Running Pliers", "Choose and place a die").showAndWait();
            activateDice();
            windowControllerList.get(0).runningPliersMove();
        });
    }

    /*
        TAP WHEEL

     */

    /**
     * @param useToolCardResponse
     */
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

                            Platform.runLater(() -> roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren()));
                            for (Button button : roundTrackButtonList) {
                                int selectedRound = Integer.parseInt(button.getId());
                                button.setOnMouseClicked(event -> {
                                    roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
                                    showDiceInRoundTrack(roundTrackDice.get(selectedRound), "TapWheel");
                                    selectedRoundTrack = selectedRound;
                                });
                            }
                            activateRoundTrack();
                        });
                break;
            case 1:
                System.out.println("phase 1");
                Platform.runLater(
                        () -> {
                            for (Button button : roundTrackButtonList) {
                                int selectedRound = Integer.parseInt(button.getId());
                                button.setOnMouseClicked(event -> {
                                    roundTrackDiceHBox.getChildren().removeAll(roundTrackDiceHBox.getChildren());
                                    showDiceInRoundTrack(roundTrackDice.get(selectedRound), "");
                                    selectedRoundTrack = selectedRound;
                                });
                            }
                            disableRoundTrack();
                            windowControllerList.get(0).setAvailablePosition(useToolCardResponse.availablePositions);
                            windowControllerList.get(0).activateTapWheelDice(toolCardSelectedDice.getDice().getDiceColor());
                            windowControllerList.get(0).moveDiceinPatternCardTapWheel(1, toolCardSelectedDice.getDice().getDiceColor().toString());
                        }
                );
                break;
            case 2:
                Platform.runLater(
                        () -> {
                            windowControllerList.get(0).updatePatternCardTapWheel(useToolCardResponse.player.getPatternCard());
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType no = new ButtonType("No");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to place another dice?", yes, no);
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
            default:
                break;
        }
    }


}