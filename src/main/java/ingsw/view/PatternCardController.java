package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.utilities.DoubleString;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternCardController implements SceneUpdater {

    @FXML
    private GridPane patternCardChoice;

    @FXML
    private Button patternCardOne;
    @FXML
    private Button patternCardTwo;

    @FXML
    private Button patternCardThree;

    @FXML
    private Button patternCardFour;


    NetworkType networkType;
    GUIUpdater application;

    public PatternCardController() {

    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @FXML
    void onSelectPatternCardOne(ActionEvent event) throws IOException {
        //TODO
        application.launchFourthGUI();
    }

    @FXML
    void onSelectPatternCardTwo(ActionEvent event) throws IOException {
        //TODO
        application.launchFourthGUI();
    }

    @FXML
    void onSelectPatternCardThree(ActionEvent event) throws IOException {
        //TODO
        application.launchFourthGUI();
    }

    @FXML
    void onSelectPatternCardFour(ActionEvent event) throws IOException {
        //TODO
        application.launchFourthGUI();
    }

    public void showPatternCard(List<PatternCard> patternCards) {
        //TODO check url format
        patternCardOne.setGraphic(new ImageView("img/" + patternCards.get(0).getName() + ".jpg"));
        patternCardTwo.setGraphic(new ImageView("img/" + patternCards.get(1).getName() + ".jpg"));
        patternCardThree.setGraphic(new ImageView("img/" + patternCards.get(2).getName() + ".jpg"));
        patternCardFour.setGraphic(new ImageView("img/" + patternCards.get(3).getName() + ".jpg"));
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
    public void setPatternCards(List<PatternCard> patternCards) {
        patternCardOne.setVisible(true);
        patternCardTwo.setVisible(true);
        patternCardThree.setVisible(true);
        patternCardFour.setVisible(true);

        Platform.runLater(
                () -> {
                    patternCardOne.setText(patternCards.get(0).toString());
                    patternCardTwo.setText(patternCards.get(1).toString());
                    patternCardThree.setText(patternCards.get(2).toString());
                    patternCardFour.setText(patternCards.get(3).toString());
                }
        );
    }
}
