package org.example.game_farmfrenzy2.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.example.game_farmfrenzy2.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingView implements Initializable {
    @FXML private StackPane root;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/MainMenuPic.png");
        if (imgUrl != null) {
            Image img = new Image(imgUrl.toExternalForm());
            BackgroundImage bg = new BackgroundImage(
                    img,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            root.setBackground(new Background(bg));
        }

        progressBar.setProgress(0);
        statusLabel.setText("Loading assets...");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        e -> statusLabel.setText("Loading assets..."),
                        new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1.0),
                        e -> statusLabel.setText("Loading sounds..."),
                        new KeyValue(progressBar.progressProperty(), 0.45)),
                new KeyFrame(Duration.seconds(2.0),
                        e -> statusLabel.setText("Almost ready..."),
                        new KeyValue(progressBar.progressProperty(), 0.8)),
                new KeyFrame(Duration.seconds(2.8),
                        e -> statusLabel.setText("Done!"),
                        new KeyValue(progressBar.progressProperty(), 1.0))
        );

        timeline.setOnFinished(e -> {
            try {
                SoundManager.getInstance().playMenuMusic();
                Main.getInstance().goToLogin();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        timeline.play();
    }
}