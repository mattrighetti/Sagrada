package ingsw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class WindowController  {

    @FXML
    private VBox windowFrameVBox;

    @FXML
    private ImageView patternCardImageView;

    @FXML
    private Button breakWindowButton;

    @FXML
    void onBreakWindowPressed(ActionEvent event) {
        System.out.println("Broken window");
    }

    public void setPatternCardImageView(ImageView imageView) {
        this.patternCardImageView.setImage(imageView.getImage());
    }

}
