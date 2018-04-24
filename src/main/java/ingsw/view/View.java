package ingsw.view;

import ingsw.controller.NetworkTransmitter;
import ingsw.model.SagradaGame;
import ingsw.model.cards.patterncard.PatternCard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Set;

public class View extends Application implements RemoteView {
    SagradaGame sagradaGame;
    private String username;
    private NetworkTransmitter RMITransmitter;
    private NetworkTransmitter SocketTransmitter;
    private NetworkTransmitter currentTransmitter;

    @FXML
    private TextField usernameTextField;
    @FXML
    private RadioButton rmiRadioButton;
    @FXML
    private RadioButton socketRadioButton;
    @FXML
    private Button loginButton;

    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent rootComponent = loader.load();
        scene = new Scene(new GridPane(), 500, 500);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Sagrada Game");
        primaryStage.setScene(new Scene(rootComponent));
        primaryStage.show();
    }

    public void retrieveSagradaGame() throws RemoteException, NotBoundException {
        sagradaGame = (SagradaGame) LocateRegistry.getRegistry().lookup("sagrada");
    }

    public void onLoginPressed(ActionEvent actionEvent) {
        username = usernameTextField.getText();
//        sagradaGame.joinSagradaGame(username);
        ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).setScene(scene);
    }

    public void selectedRMI(ActionEvent actionEvent) {
        System.out.println("RMI");
    }

    public void selectedSocket(ActionEvent actionEvent) {
        System.out.println("Socket");
    }

    @Override
    public void displayPatternCardsToChoose(Set<PatternCard> patternCards) {
        //TODO mostra pattern cards da scegliere
    }
}
