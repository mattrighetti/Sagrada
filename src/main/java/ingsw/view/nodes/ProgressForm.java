package ingsw.view.nodes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Progress form used during the loading phase of the game(join and rejoin a match)
 */
public class ProgressForm {
    private final Stage dialogStage;
    private final VBox vBox;
    private final HBox hBox;
    private final Label text;
    final Timeline timeline = new Timeline();

    public ProgressForm() {
        dialogStage = new Stage();
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UNDECORATED);

        text = new Label("Waiting for other players");
        text.setPadding(new Insets(10, 10, 10, 10));

        hBox = new HBox();
        hBox.getChildren().addAll(createProgressBar(), createTimeCircle());
        hBox.getStylesheets().add(getClass().getResource("/lobbyStyle.css").toExternalForm());

        vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(text, hBox);

        Scene scene = new Scene(vBox);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private Parent createProgressBar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setLayoutY(45);
        progressBar.setStyle("-fx-accent: orange;");
        return progressBar;
    }

    private Parent createTimeCircle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(25, 25);
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> timeline.stop());
        progressIndicator.styleProperty().bind(Bindings.createStringBinding(
                () -> {
                    final double percent = progressIndicator.getProgress();
                    final double m = (2d * percent);
                    final int n = (int) m;
                    final double f = m - n;
                    final int t = (int) (255 * f);
                    int r = 0, g = 0, b = 0;
                    switch (n) {
                        case 2:
                            r = 255;
                            g = t;
                            b = 0;
                            break;
                        case 1:
                            r = 255 - t;
                            g = 255;
                            b = 0;
                            break;
                        case 0:
                            r = 0;
                            g = 255;
                            b = 0;
                            break;
                        default:
                            break;
                    }

                    return String.format("-fx-progress-color: rgb(%d,%d,%d)", r, g, b);
                },
                progressIndicator.progressProperty()));
        final KeyValue kv0 = new KeyValue(progressIndicator.progressProperty(), 0);
        final KeyValue kv1 = new KeyValue(progressIndicator.progressProperty(), 1);
        final KeyFrame kf0 = new KeyFrame(Duration.ZERO, kv0);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(5000), kv1);
        timeline.getKeyFrames().addAll(kf0, kf1);
        return progressIndicator;
    }

    public void activateProgressBar() {
        dialogStage.show();
        timeline.play();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

}
