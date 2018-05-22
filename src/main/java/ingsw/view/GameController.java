package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Dice;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class GameController implements SceneUpdater {

    NetworkType networkType;
    GUIUpdater application;

    private ArrayList<PatternCard> patternCards;
    private ArrayList<Button> roundDice;
    private int numOfPLayers;

    @FXML
    private VBox toolCardVBox;

    @FXML
    private ImageView privateCardImageView;

    @FXML
    private VBox publicCardVBox;

    @FXML
    private Button draftDiceButton;

    @FXML
    private HBox diceHorizontalBox;

    @FXML
    private Button endTurnButton;

    @FXML
    private TabPane tabPane;

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }


    public void setWindowsTabPane() {

    }

    public void setDiceBox() {
        for (int i = 0; i < ((numOfPLayers * 2) + 1); i++) {
            roundDice.add(new Button("dice" + i));
            diceHorizontalBox.getChildren().add(roundDice.get(i));
        }
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    @Override
    public void updateConnectedUsers(int usersConnected) {

    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {

    }

    @Override
    public void popUpMatchAlreadyExistent() {

    }
}
