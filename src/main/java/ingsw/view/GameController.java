package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.model.Dice;
import ingsw.model.Player;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.ToolCard;
import ingsw.utilities.DoubleString;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;


public class GameController implements SceneUpdater, Initializable {

    @FXML
    private HBox diceHorizontalBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox toolCardVBox;

    @FXML
    private ImageView firstToolCardImageView;

    @FXML
    private ImageView secondToolCardImageView;

    @FXML
    private ImageView thirdToolCardImageView;

    @FXML
    private VBox publicCardVBox;

    @FXML
    private ImageView firstPublicCardImageView;

    @FXML
    private ImageView secondPublicCardImageView;

    @FXML
    private ImageView thirdPublicCardImageView;

    @FXML
    private Button showPrivateCardButton;

    @FXML
    private Button draftDiceButton;

    @FXML
    private Button endTurnButton;

    private List<ImageView> toolCardsImageViews;
    private List<ImageView> publicCardsImageViews;

    NetworkType networkType;
    GUIUpdater application;

    private List<Player> players;
    private List<Button> diceButton;
    private Set<PublicObjectiveCard> publicObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCardList;
    private Set<ToolCard> toolCards = new HashSet<>();
    private List<ToolCard> toolCardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publicObjectiveCardList = new ArrayList<>();
        toolCardList = new ArrayList<>();
        publicCardsImageViews = new ArrayList<>();
        toolCardsImageViews = new ArrayList<>();

        /* Every button must be disables at the first launch */
        draftDiceButton.setDisable(true);
        endTurnButton.setDisable(true);

        /* Create a list used to iterate through ImageViews */
        toolCardsImageViews.add(firstToolCardImageView);
        toolCardsImageViews.add(secondToolCardImageView);
        toolCardsImageViews.add(thirdToolCardImageView);
        publicCardsImageViews.add(firstPublicCardImageView);
        publicCardsImageViews.add(secondPublicCardImageView);
        publicCardsImageViews.add(thirdPublicCardImageView);
    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    public void loadImageViews() {
        int counter = 0;
        for (ImageView imageView : toolCardsImageViews) {
            imageView.setImage(new Image("/img/toolcards/" + toolCardList.get(counter).getName() + ".png"));
            counter++;
        }

        counter = 0;
        for (ImageView imageView : publicCardsImageViews) {
            imageView.setImage(new Image("/img/publicoc/"+ publicObjectiveCardList.get(counter).getName() +".png"));
            counter++;
        }
    }



    @FXML
    void onDraftDicePressed(ActionEvent event) {

    }

    @FXML
    void onEndTurnPressed(ActionEvent event) {

    }

    @FXML
    void onShowPrivateCardPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Private Objective Card");
        ImageView imageView = new ImageView("/img/privateoc/" + players.get(0).getPrivateObjectiveCard().getName() + ".png");
        imageView.setFitHeight(193);
        imageView.setFitWidth(137.5);
        alert.setGraphic(imageView);
        alert.showAndWait();
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
            AnchorPane windowGrid = fxmlLoader.load();
            WindowController windowController = fxmlLoader.getController();
            Tab windowTab = new Tab();
            windowGrid.setMinSize(633, 666);
            windowTab.setContent(windowGrid);
            windowTab.setText("You");
            tabPane.setPadding(new Insets(0,0,0,0));
            tabPane.getTabs().add(windowTab);
            windowController.setPatternCardImageView(new ImageView("/img/patterncards/" + players.get(0).getPatternCard().getName() + ".png"));
        }
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

        for (ToolCard toolCard : toolCards) {
            toolCardList.add(toolCard);
        }

        for (PublicObjectiveCard publicObjectiveCard : publicObjectiveCards) {
            publicObjectiveCardList.add(publicObjectiveCard);
        }

        loadImageViews();

        try {
            setWindowsTab();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void popUpDraftNotification() {
        Platform.runLater(() -> {
            draftDiceButton.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("It's your turn");
            alert.setContentText("Click on Draft Dice to draft the dice");
            alert.showAndWait();
        });
    }
}
