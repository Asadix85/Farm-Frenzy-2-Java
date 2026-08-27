package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.model.database.ProgressRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelSelectView implements Initializable {
    @FXML private Button level1Btn;
    @FXML private Button level2Btn;
    @FXML private Button level3Btn;
    @FXML private Button level4Btn;
    @FXML private Button level5Btn;
    @FXML private Button level6Btn;
    @FXML private StackPane root;

    private final ProgressRepository progressRepo = new ProgressRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/map.png");
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
        refreshLocks();
    }

    private void refreshLocks() {
        int userId = Main.getInstance().getCurrentUserId();
        setLevelButton(level1Btn, 1, userId);
        setLevelButton(level2Btn, 2, userId);
        setLevelButton(level3Btn, 3, userId);
        setLevelButton(level4Btn, 4, userId);
        setLevelButton(level5Btn, 5, userId);
        setLevelButton(level6Btn, 6, userId);
    }

    private void setLevelButton(Button btn, int level, int userId) {
        if (btn == null) return;
        boolean unlocked = progressRepo.isLevelUnlocked(userId, level);
        btn.setDisable(!unlocked);
        if (unlocked) {
            btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 12;");
        }
    }

    @FXML
    private void onLevel1() throws Exception {
        Main.getInstance().goToMission(1);
    }

    @FXML
    private void onLevel2() throws Exception {
        Main.getInstance().goToMission(2);
    }

    @FXML
    private void onLevel3() throws Exception {
        Main.getInstance().goToMission(3);
    }

    @FXML
    private void onLevel4() throws Exception {
        Main.getInstance().goToMission(4);
    }

    @FXML
    private void onLevel5() throws Exception {
        Main.getInstance().goToMission(5);
    }

    @FXML
    private void onLevel6() throws Exception {
        Main.getInstance().goToMission(6);
    }

    @FXML
    private void onShop() throws Exception {
        Main.getInstance().goToShop(1);
    }

    @FXML
    private void onBack() throws Exception {
        Main.getInstance().goToHome();
    }
}