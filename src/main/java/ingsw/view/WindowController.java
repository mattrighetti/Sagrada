package ingsw.view;

import ingsw.controller.network.NetworkType;
import ingsw.utilities.DoubleString;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class WindowController implements SceneUpdater {

    @FXML
    private GridPane patternCardGrid;

    @FXML
    private GridPane windowGrid;

    @FXML
    private ImageView patternCardImageView;

    @FXML
    private Button breakWindowButton;

    @Override
    public void updateConnectedUsers(int usersConnected) {

    }

    @Override
    public void setNetworkType(NetworkType clientController) {

    }

    @Override
    public void updateExistingMatches(List<DoubleString> matches) {

    }

    @Override
    public void popUpMatchAlreadyExistent() {

    }
}

