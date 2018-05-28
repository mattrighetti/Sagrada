package ingsw.view;

import ingsw.model.cards.patterncard.PatternCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.Arrays;

public class WindowController {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    @FXML
    private Button breakWindowButton;
    
    private String username;

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
