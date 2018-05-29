package ingsw.view;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                DicePane dicePane = new DicePane(i, j);
                dicePane.getStyleClass().add("grey");
                dicePane.setOpacity(0.8);
                dicePane.setOnMouseClicked(event -> System.out.println("Pressed pane at row: " + dicePane.getRowIndex() + " and at col: " + dicePane.getColumnIndex()));
                patternCardGridPane.add(dicePane, j, i);
                dicePanes[i][j] = dicePane;
            }
        }
    }

    void setAvailablePosition(List<Boolean[][]> availablePosition) {
        this.availablePosition = availablePosition;
    }

    @FXML
    void onBreakWindowPressed(ActionEvent event) {
        System.out.println("Broken window");
    }

    void setGridPaneBackground(String patternCardName) {
        patternCardGridPane.getStyleClass().add(patternCardName);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    void updatePatternCard(PatternCard patternCard) {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                patternCardGridPane.getChildren().get((j * i) + j).getStyleClass().clear();
                patternCardGridPane.getChildren().get((j * i) + j).getStyleClass().add(patternCard.getGrid().get(i).get(j).getDice().toString());
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
