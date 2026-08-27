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

import java.net.URL;
import java.util.ResourceBundle;

public class HomeView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label welcomeLabel;

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
    }

    public void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            welcomeLabel.setText("Welcome, " + username + "!");
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

    @FXML
    private void onStart() throws Exception {
        Main.getInstance().goToLevelSelect();
    }

    @FXML
    private void onSettings() throws Exception {
        Main.getInstance().goToSettings();
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}