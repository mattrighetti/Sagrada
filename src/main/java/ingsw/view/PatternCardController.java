package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.controller.network.commands.BoardDataResponse;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.view.nodes.ProgressForm;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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

    private NetworkType networkType;
    private View application;

    private List<PatternCard> patternCards;
    private ProgressForm progressForm;

    void setApplication(View application) {
        this.application = application;
    }

    @Override
    public void setNetworkType(NetworkType clientController) {
        this.networkType = clientController;
    }

    @FXML
    void onClickPatternCardFour(ActionEvent event) {
        networkType.choosePatternCard(patternCards.get(3));
        disablePatternCardButtons();
        //progressForm = new ProgressForm();
        //progressForm.activateProgressBar();
    }

    @FXML
    void onClickPatternCardOne(ActionEvent event) {
        networkType.choosePatternCard(patternCards.get(0));

        disablePatternCardButtons();
        //progressForm = new ProgressForm();
        //progressForm.activateProgressBar();
    }

    @FXML
    void onClickPatternCardThree(ActionEvent event) {
        networkType.choosePatternCard(patternCards.get(2));
        disablePatternCardButtons();
        //progressForm = new ProgressForm();
        //progressForm.activateProgressBar();
    }

    @FXML
    void onClickPatternCardTwo(ActionEvent event) {
        networkType.choosePatternCard(patternCards.get(1));
        disablePatternCardButtons();
        //progressForm = new ProgressForm();
        //progressForm.activateProgressBar();
    }

    private void disablePatternCardButtons(){
        patternCardOne.setDisable(true);
        patternCardTwo.setDisable(true);
        patternCardThree.setDisable(true);
        patternCardFour.setDisable(true);
    }

    @Override
    public void setPatternCards(List<PatternCard> patternCards) {
        this.patternCards = patternCards;
        Platform.runLater(
                () -> {
                    ImageView imageViewOne = new ImageView("/img/patterncards/" + patternCards.get(0).getName() + ".png");
                    imageViewOne.setFitWidth(194);
                    imageViewOne.setFitHeight(165.5);
                    ImageView imageViewTwo = new ImageView("/img/patterncards/" + patternCards.get(1).getName() + ".png");
                    imageViewTwo.setFitWidth(194);
                    imageViewTwo.setFitHeight(165.5);
                    ImageView imageViewThree = new ImageView("/img/patterncards/" + patternCards.get(2).getName() + ".png");
                    imageViewThree.setFitWidth(194);
                    imageViewThree.setFitHeight(165.5);
                    ImageView imageViewFour = new ImageView("/img/patterncards/" + patternCards.get(3).getName() + ".png");
                    imageViewFour.setFitWidth(194);
                    imageViewFour.setFitHeight(165.5);
                    patternCardOne.setGraphic(imageViewOne);
                    patternCardTwo.setGraphic(imageViewTwo);
                    patternCardThree.setGraphic(imageViewThree);
                    patternCardFour.setGraphic(imageViewFour);
                }
        );
    }

    @Override
    public void launchFourthGui(BoardDataResponse boardDataResponse) {
        Platform.runLater(() -> application.launchFourthGUI(boardDataResponse));
    }
}
