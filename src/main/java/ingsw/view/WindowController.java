package ingsw.view;

import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.nodes.DicePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    @FXML
    private Button breakWindowButton;
    
    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                DicePane dicePane = new DicePane(j, i);
                dicePane.getStyleClass().add("grey");
                dicePane.setOpacity(0.8);
                dicePane.setOnMouseClicked(event -> System.out.println("Pressed pane at row: " + dicePane.getRowIndex() + " and at col: " + dicePane.getColumnIndex()));
                patternCardGridPane.add(dicePane, i, j);
            }
        }
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

    public void updatePatterCard(PatternCard patternCard) {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                patternCardGridPane.getChildren().get((j * i) + j).getStyleClass().clear();
                patternCardGridPane.getChildren().get((j * i) + j).getStyleClass().add(patternCard.getGrid().get(i).get(j).getDice().toString());
            }
        }
    }
}
