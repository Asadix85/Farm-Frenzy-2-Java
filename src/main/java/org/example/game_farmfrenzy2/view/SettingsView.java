package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import org.example.game_farmfrenzy2.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsView implements Initializable {
    @FXML private StackPane root;
    @FXML private Slider musicSlider;
    @FXML private Slider soundSlider;

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

        SoundManager sm = SoundManager.getInstance();
        musicSlider.setValue(sm.getMusicVolume() * 100);
        soundSlider.setValue(sm.getSoundVolume() * 100);

        musicSlider.valueProperty().addListener((obs, oldV, newV) ->
                sm.setMusicVolume(newV.doubleValue() / 100.0));

        soundSlider.valueProperty().addListener((obs, oldV, newV) ->
                sm.setSoundVolume(newV.doubleValue() / 100.0));
    }

    @FXML
    private void onSave() {
        SoundManager sm = SoundManager.getInstance();
        sm.setMusicVolume(musicSlider.getValue() / 100.0);
        sm.setSoundVolume(soundSlider.getValue() / 100.0);
    }

    @FXML
    private void onBack() throws Exception {
        if (Main.getInstance().getCurrentGame() != null
                && !Main.getInstance().getCurrentGame().getGameState().isGameOver()) {
            int level = Main.getInstance().getCurrentGame().getGameState().getLevelNumber();
            Main.getInstance().goToGame(level);
        } else {
            Main.getInstance().goToHome();
        }
    }
}