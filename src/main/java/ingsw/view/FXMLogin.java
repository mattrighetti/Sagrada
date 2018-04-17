package ingsw.view;

import ingsw.model.SagradaGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class FXMLogin extends Application {
    SagradaGame sagradaGame;

    @FXML
    private TextField usernameTextField;
    @FXML
    private RadioButton rmiRadioButton, socketRadioButton;
    @FXML
    private Button loginButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));

        Parent rootComponent = loader.load();



        primaryStage.setResizable(false);
        primaryStage.setTitle("Sagrada Game");
        primaryStage.setScene(new Scene(rootComponent));
        primaryStage.show();


    }

    public void retrieveSagradaGame() throws RemoteException, NotBoundException {
        sagradaGame = (SagradaGame) LocateRegistry.getRegistry().lookup("sagrada");
    }

    public void onLoginPressed(ActionEvent actionEvent) {
        sagradaGame.joinSagradaGame(usernameTextField.getText());
        System.out.println("@" + usernameTextField.getText() + " logged in.");

    }

    public void selectedRMI(ActionEvent actionEvent) {
        System.out.println("RMI");
    }

    public void selectedSocket(ActionEvent actionEvent) {
        System.out.println("Socket");
    }
}
