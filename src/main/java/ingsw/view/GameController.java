package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameController implements SceneUpdater {

    NetworkType networkType;
    GUIUpdater application;

    private ArrayList<Player> players;
    private ArrayList<Button> diceButton;

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
        for (int i = 0; i < ((players.size() * 2) + 1); i++) {
            diceButton.add(new Button("dice" + i));
            diceHorizontalBox.getChildren().add(diceButton.get(i));
        }
    }

    public void setWindowsTab() throws IOException {
        for (int i = 0; i < players.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/window.fxml"));
            GridPane windowGrid = fxmlLoader.load();
            Tab windowTab = new Tab();
            windowTab.setContent(windowGrid);
            tabPane.getTabs().add(windowTab);
        }
    }

    public void setPrivateCardImageView(String privateCard){
        privateCardImageView.setImage(new Image("img/" + privateCard + ".png"));
    }

    public void setPublicCardVBox(List<String> publicCards) {
        for (String name : publicCards) {
            publicCardVBox.getChildren().add(new ImageView("img/" + name + ".png"));
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

}
