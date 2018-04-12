package ingsw.view;

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

public class FXMLogin extends Application {

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
        FXMLogin loginGui = loader.getController();

        primaryStage.setTitle("Sagrada Game");
        primaryStage.setScene(new Scene(rootComponent));
        primaryStage.show();
    }


    public void onLoginPressed(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        System.out.println(username);
    }
}
