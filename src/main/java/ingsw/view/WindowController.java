package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Color;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.Tuple;
import ingsw.view.nodes.DicePane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    private static final String CLICKED = "clicked";
    private static final String NO_DICE_SELECTED = "No dice selected";

    private String username;

    private Map<String, Boolean[][]> availablePosition;
    private DicePane[][] dicePanes = new DicePane[4][5];
    private Dice selectedDice;
    private NetworkType networkType;
    private List<Tuple> selectedPositions = new ArrayList<>();
    private GameUpdater gameUpdater;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = new DicePane(i, j);
                dicePane.getStyleClass().add("grey");
                dicePane.setOpacity(0.8);
                diceCursorMouseEvent(dicePane);
                patternCardGridPane.add(dicePane, j, i);
                dicePanes[i][j] = dicePane;
            }
        }
    }

    void resetDiceCursorMouseEvent() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                diceCursorMouseEvent(dicePanes[i][j]);
            }
        }
    }

    private void diceCursorMouseEvent(DicePane dicePane) {
        dicePane.setOnMouseClicked(event -> {
            System.out.println(CLICKED);
            if (selectedDice != null) {
                patternCardGridPane.setCursor(Cursor.DEFAULT);
                gameUpdater.setPlaceDiceMove();
                networkType.placeDice(selectedDice, dicePane.getColumnIndex(), dicePane.getRowIndex());
            } else {
                System.out.println(NO_DICE_SELECTED);
            }
            selectedDice = null;
        });
    }

    void setAvailablePosition(Map<String, Boolean[][]> availablePosition) {
        this.availablePosition = availablePosition;
    }

    GridPane getPatternCardGridPane() {
        return patternCardGridPane;
    }

    void setGameUpdater(GameUpdater gameUpdater) {
        this.gameUpdater = gameUpdater;
    }

    void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    void setGridPaneBackground(String patternCardName) {
        anchorPane.getStyleClass().add(patternCardName);
    }

    void setSelectedDice(Dice dice) {
        this.selectedDice = dice;
    }

    public Dice getSelectedDice() {
        return selectedDice;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    void updatePatternCard(PatternCard patternCard) {

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane dicePane = dicePanes[j][k];
                dicePane.getStyleClass().clear();
                if (patternCard.getGrid().get(j).get(k).getDice() != null) {
                    dicePane.setDice(patternCard.getGrid().get(j).get(k).getDice());
                    dicePane.getStyleClass().add(patternCard.getGrid().get(j).get(k).getDice().toString());
                    dicePane.getStyleClass().add("dicePaneImageSize");
                }
                diceCursorMouseEvent(dicePane);
            }
        }
    }

    void updateAvailablePositions(String diceString) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                if (availablePosition.get(diceString) != null) {
                    System.out.print(availablePosition.get(diceString)[j][k] + "\t");
                    if (!availablePosition.get(diceString)[j][k]) {
                        (dicePanes[j][k]).getStyleClass().add("grey");
                        (dicePanes[j][k]).setDisable(true);
                    } else {
                        (dicePanes[j][k]).getStyleClass().remove("grey");
                        dicePanes[j][k].setDisable(false);
                    }
                } else dicePanes[j][k].getStyleClass().add("grey");
            }
            System.out.println();
        }
    }

    void setCursorDice(String dice) {
        Image cursor = new Image("/img/dice/" + dice + ".png", 90, 90, true, true);
        ImageCursor imageCursor = new ImageCursor(cursor);
        patternCardGridPane.setCursor(imageCursor);
    }

    public void unsetSelectedDice() {
        selectedDice = null;
    }

    /**
     * Method used by EglomiseBrush, CopperFoilBurnisher toolcards
     */
    void moveDiceinPatternCard() {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane thisDicePane = dicePanes[j][k];

                //set the onMouseClicked of the Pattern Card
                thisDicePane.setOnMouseClicked(event -> {
                    if (selectedPositions.isEmpty()) {
                        if (!thisDicePane.getStyleClass().isEmpty()) {
                            selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                            System.out.println(thisDicePane.getStyleClass().toString());
                            updateAvailablePositions(thisDicePane.getStyleClass().get(0) + thisDicePane.getRowIndex() + thisDicePane.getColumnIndex());
                            setCursorDice(thisDicePane.getStyleClass().get(0));
                        }
                    } else if (selectedPositions.size() == 1) {
                        if (thisDicePane.getStyleClass().isEmpty()) {
                            selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                            patternCardGridPane.setCursor(Cursor.DEFAULT);
                            networkType.copperFoilBurnisherMove(selectedPositions.get(0), selectedPositions.get(1));

                            selectedPositions.clear();
                        }
                    } else
                        selectedPositions.clear();
                });

            }
        }

    }

    void corkBackedStraightedge() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = dicePanes[i][j];
                dicePanes[i][j].setOnMouseClicked(event -> {
                    System.out.println(CLICKED);
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        gameUpdater.disableDice();
                        networkType.corkBackedStraightedgeMove(selectedDice, dicePane.getRowIndex(), dicePane.getColumnIndex());
                    } else {
                        System.out.println(NO_DICE_SELECTED);
                    }
                    selectedDice = null;
                });
            }
        }
    }

    void moveDiceinPatternCardLathekin() {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane thisDicePane = dicePanes[j][k];
                thisDicePane.setOnMouseClicked(event -> lathekinMouseEvent(thisDicePane));
            }
        }

    }

    void enableDiceInPatternCard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (dicePanes[i][j].getDice() != null) {
                    dicePanes[i][j].setDisable(false);
                } else {
                    dicePanes[i][j].setDisable(true);
                }
            }
        }
    }

    private void lathekinMouseEvent(DicePane thisDicePane) {
        if (selectedPositions.isEmpty()) {
            if (!thisDicePane.getStyleClass().isEmpty() && !thisDicePane.getStyleClass().contains("grey")) {
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                System.out.println(thisDicePane.getStyleClass().toString());
                updateAvailablePositions(thisDicePane.getStyleClass().get(0) + thisDicePane.getRowIndex() + thisDicePane.getColumnIndex());
            }
        } else if (selectedPositions.size() == 1) {
            if (!thisDicePane.getStyleClass().contains("grey")) {
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                if (thisDicePane.getStyleClass().isEmpty())
                    networkType.lathekinMove(selectedPositions.get(0), selectedPositions.get(1), false);
                else
                    networkType.lathekinMove(selectedPositions.get(0), selectedPositions.get(1), true);
                selectedPositions.clear();
            } else {
                System.out.println("cannot place dice here " + thisDicePane.getStyleClass());
            }
        } else
            selectedPositions.clear();
    }

    void fluxBrushMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                DicePane dicePane = dicePanes[i][j];
                dicePane.setOnMouseClicked(event -> {
                    System.out.println(CLICKED);
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        networkType.fluxBrushMove(selectedDice, dicePane.getRowIndex(), dicePane.getColumnIndex());
                    } else {
                        System.out.println(NO_DICE_SELECTED);
                    }
                    selectedDice = null;
                });
            }
        }
    }


    void fluxRemoverMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                DicePane dicePane = dicePanes[i][j];
                dicePane.setOnMouseClicked(event -> {
                    System.out.println(CLICKED);
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        networkType.fluxRemoverMove(selectedDice, dicePane.getColumnIndex(), dicePane.getRowIndex());
                    } else {
                        System.out.println(NO_DICE_SELECTED);
                    }
                    selectedDice = null;
                });
            }
        }
    }

    void runningPliersMove() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = dicePanes[i][j];
                dicePane.setOnMouseClicked(event -> {
                    System.out.println(CLICKED);
                    if (selectedDice != null) {
                        patternCardGridPane.setCursor(Cursor.DEFAULT);
                        networkType.runningPliersMove(selectedDice, dicePane.getRowIndex(), dicePane.getColumnIndex());
                    } else {
                        System.out.println(NO_DICE_SELECTED);
                    }
                    selectedDice = null;
                });
            }
        }
    }

    void activateTapWheelDice(Color diceColor) {
        System.out.println("Activating dice " + diceColor);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                for (String style : dicePanes[i][j].getStyleClass()) {
                    if (style.contains(diceColor.toString())) {
                        dicePanes[i][j].setDisable(false);
                        break;
                    } else
                        dicePanes[i][j].setDisable(true);
                }
            }
        }
    }

    void moveDiceinPatternCardTapWheel(int phase, String diceColor) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane thisDicePane = dicePanes[i][j];
                thisDicePane.setOnMouseClicked(event -> tapWheelMouseEvent(thisDicePane, phase, diceColor));

            }
        }
    }

    private void tapWheelMouseEvent(DicePane thisDicePane, int phase, String diceColor) {
        System.out.println("TAPWHEEL");
        if (selectedPositions.isEmpty()) {
            System.out.println("A");
            if (!thisDicePane.getStyleClass().isEmpty() && !thisDicePane.getStyleClass().contains("grey")) {
                System.out.println("B");
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                System.out.println("Dice to move " + thisDicePane.getStyleClass().toString());
                updateAvailablePositions(thisDicePane.getStyleClass().get(0) + thisDicePane.getRowIndex() + thisDicePane.getColumnIndex());
            }
        } else if (selectedPositions.size() == 1) {
            System.out.println("C");
            if ((selectedPositions.get(0).getFirst() != thisDicePane.getRowIndex() || selectedPositions.get(0).getSecond() != thisDicePane.getColumnIndex())) {
                selectedPositions.add(new Tuple(thisDicePane.getRowIndex(), thisDicePane.getColumnIndex()));
                if (thisDicePane.getStyleClass().isEmpty()) {
                    System.out.println("D");
                    System.out.println("placing the dice in the phase " + phase);
                    networkType.tapWheelMove(selectedPositions.get(0), selectedPositions.get(1), phase, false);
                    if (phase == 1)
                        activateTapWheelDice(selectedPositions.get(0).getFirst(), selectedPositions.get(0).getSecond());
                    if (phase == 2)
                        resetDiceCursorMouseEvent();
                    selectedPositions.clear();
                } else {
                    System.out.println("e");
                    networkType.tapWheelMove(selectedPositions.get(0), selectedPositions.get(1), phase, true);
                    selectedPositions.clear();
                }
            } else selectedPositions.clear();
        } else
            selectedPositions.clear();
    }

    private void activateTapWheelDice(int first, int second) {
        String diceColor = dicePanes[first][second].getStyle();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != first || j != second) {
                    if (dicePanes[i][j].getStyle().contains(diceColor))
                        dicePanes[i][j].setDisable(false);
                    else
                        dicePanes[i][j].setDisable(true);
                }
            }
        }
    }

    void updatePatternCardTapWheel(PatternCard patternCard) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                DicePane dicePane = dicePanes[j][k];
                dicePane.getStyleClass().clear();
                if (patternCard.getGrid().get(j).get(k).getDice() != null) {
                    dicePane.getStyleClass().add(patternCard.getGrid().get(j).get(k).getDice().toString());
                    dicePane.getStyleClass().add("dicePaneImageSize");
                }
            }
        }
    }
}
