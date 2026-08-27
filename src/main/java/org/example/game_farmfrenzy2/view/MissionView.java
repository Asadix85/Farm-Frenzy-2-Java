package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.model.game.LevelConfig;

import java.net.URL;
import java.util.ResourceBundle;

public class MissionView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label levelLabel;
    @FXML private Label goalLabel;
    @FXML private Label timeLabel;
    @FXML private Label hintLabel;

    private int level;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/mission.png");
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
    }

    public void setLevel(int level) {
        this.level = level;
        LevelConfig cfg = LevelConfig.get(level);
        levelLabel.setText(cfg.title);
        goalLabel.setText(cfg.goalText);
        timeLabel.setText("Gold: " + formatTime(cfg.goldTime) + "  |  Silver: " + formatTime(cfg.silverTime));
        hintLabel.setText(cfg.hintText);
    }

    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return m + ":" + (s < 10 ? "0" + s : s);
    }

    @FXML
    private void onStart() throws Exception {
        Main.getInstance().goToGameFresh(level);
    }

    @FXML
    private void onBack() throws Exception {
        Main.getInstance().goToLevelSelect();
    }
}