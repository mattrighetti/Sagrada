package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.nodes.DicePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    @FXML
    private Button breakWindowButton;

    private String username;
    private List<Boolean[][]> availablePosition;
    private DicePane[][] dicePanes = new DicePane[4][5];
    private Dice selectedDice;
    private NetworkType networkType;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = new DicePane(i, j);
                dicePane.getStyleClass().add("grey");
                dicePane.setOpacity(0.8);
                dicePane.setOnMouseClicked(event -> {
                    System.out.println("clicked");
                    if (selectedDice != null) {
                        networkType.placeDice(selectedDice, dicePane.getColumnIndex(), dicePane.getRowIndex());
                    } else {
                        System.out.println("No dice selected");
                    }
                    selectedDice = null;
                });
                patternCardGridPane.add(dicePane, j, i);
                dicePanes[i][j] = dicePane;
            }
        }
    }

    void setAvailablePosition(List<Boolean[][]> availablePosition) {
        this.availablePosition = availablePosition;
    }

    void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    @FXML
    void onBreakWindowPressed(ActionEvent event) {
        System.out.println("Broken window");
    }

    void setGridPaneBackground(String patternCardName) {
        patternCardGridPane.getStyleClass().add(patternCardName);
    }

    void setSelectedDice(Dice dice) {
        this.selectedDice = dice;
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
                (dicePanes[j][k]).getStyleClass().clear();
                if (patternCard.getGrid().get(j).get(k).getDice() != null) {
                    System.out.println(patternCard.getGrid().get(j).get(k).getDice());
                    (dicePanes[j][k]).getStyleClass().add(patternCard.getGrid().get(j).get(k).getDice().toString());
                    (dicePanes[j][k]).getStyleClass().add("dicePaneImageSize");
                }
            }
        }
    }

    void updateAvailablePositions(int i) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 5; k++) {
                if (!availablePosition.get(i)[j][k]) {
                    (dicePanes[j][k]).getStyleClass().add("grey");
                } else {
                    (dicePanes[j][k]).getStyleClass().clear();
                }
            }
        }
    }
}
