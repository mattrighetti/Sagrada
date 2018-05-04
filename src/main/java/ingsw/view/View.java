package ingsw.view;

import ingsw.controller.NetworkTransmitter;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
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

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class View extends Application implements RemoteView {
    private String username;
    private NetworkTransmitter RMITransmitter;
    private NetworkTransmitter SocketTransmitter;
    private NetworkTransmitter currentTransmitter;

    private ClientController clientController;

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
        /* DEPLOYS CLIENT */
        deployClient();

        /* CREATES VIEW */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent rootComponent = loader.load();
        scene = new Scene(new GridPane(), 500, 500);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Sagrada Game");
        primaryStage.setScene(new Scene(rootComponent));
        primaryStage.show();
    }

    public void onLoginPressed(ActionEvent actionEvent) {
        username = usernameTextField.getText();
    }

    public void selectedRMI(ActionEvent actionEvent) {
        System.out.println("rmi");
    }

    public void selectedSocket(ActionEvent actionEvent) {
        System.out.println("socket");
    }

    public void deployClient() throws IOException {
        Scanner fromKeyboard = new Scanner(System.in);
        System.out.println("Type host: ");
        String host = fromKeyboard.nextLine();
        System.out.println("Type port: ");
        int port = fromKeyboard.nextInt();
        Client client = new Client(host, port);
        client.connect();
        this.clientController = new ClientController(client, this);
    }



    @Override
    public void displayPatternCardsToChoose(Set<PatternCard> patternCards) {
        //TODO mostra pattern cards da scegliere
    }
}
