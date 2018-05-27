package ingsw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class WindowController {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private GridPane patternCardGridPane;

    @FXML
    private Button breakWindowButton;

    @FXML
    void onBreakWindowPressed(ActionEvent event) {
        System.out.println("Broken window");
    }

    void setGridPaneBackground(String patternCardName) {
        patternCardGridPane.getStyleClass().add(patternCardName);
    }

}
