package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.DoubleString;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class GameController implements SceneUpdater, Initializable {

    @FXML
    private HBox diceHorizontalBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox toolCardVBox;

    @FXML
    private VBox publicCardVBox;

    @FXML
    private ImageView privateCardImageView;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button draftDiceButton;

    NetworkType networkType;
    GUIUpdater application;

    private List<Player> players;
    private List<Button> diceButton;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private Set<ToolCard> toolCards;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
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
    public void updateConnectedUsers(int usersConnected) {

    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {

    }

    @Override
    public void loadData(BoardDataResponse boardDataResponse) {
        this.players = boardDataResponse.players;
        this.publicObjectiveCards = boardDataResponse.publicObjectiveCards;
        this.toolCards = boardDataResponse.toolCards;
    }
}
