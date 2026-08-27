package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.model.database.ProgressRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class EndView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label resultLabel;
    @FXML private Text star1;
    @FXML private Text star2;
    @FXML private Text star3;
    @FXML private Label messageLabel;
    @FXML private Label coinsLabel;
    @FXML private Label timeLabel;
    @FXML private Button nextButton;

    private boolean won;
    private int stars;
    private int level;
    private int coins;
    private int timeSpent;
    private int userId;
    ProgressRepository repo = new ProgressRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/end/end_bg.png");
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/images/MainMenuPic.png");
        }
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
            userId = Main.getInstance().getCurrentUserId();
        }
    }

    public void setResult(boolean won, int stars, int level, int coins, int timeSpent) {
        this.won = won;
        this.stars = stars;
        this.level = level;
        this.coins = coins;
        this.timeSpent = timeSpent;

        if (won) {
            repo.unlockLevel(userId, level + 1);
            saveProgress();
        }
        displayResult();
    }

    private void saveProgress() {
        int userId = Main.getInstance().getCurrentUserId();
        if (userId == -1) {
            System.err.println("No user logged in!");
            return;
        }
        ProgressRepository repo = new ProgressRepository();
        repo.saveProgress(userId, level, stars, coins, timeSpent);
        if (level == 1) {
            repo.unlockLevel(userId, level + 1);
        }
        System.out.println("Progress saved for level " + level + " with " + stars + " stars!");
    }

    private void displayResult() {
        if (won) {
            resultLabel.setText("YOU WIN!");
            resultLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");
            messageLabel.setText("Great job! Level " + level + " completed.");
            nextButton.setVisible(true);
            nextButton.setManaged(true);
        } else {
            resultLabel.setText("YOU LOSE!");
            resultLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            messageLabel.setText("Try again. You can do better!");
            nextButton.setVisible(false);
            nextButton.setManaged(false);
        }

        star1.setText(stars >= 1 ? "★" : "☆");
        star2.setText(stars >= 2 ? "★" : "☆");
        star3.setText(stars >= 3 ? "★" : "☆");

        coinsLabel.setText("Coins: " + coins);
        if (won) {
            timeLabel.setText("Time: " + timeSpent + " seconds");
        } else {
            timeLabel.setText("");
        }
    }

    @FXML
    private void onNext() throws Exception {
        Main.getInstance().goToGameFresh(level + 1);
    }

    @FXML
    private void onRetry() throws Exception {
        Main.getInstance().goToGameFresh(level);
    }

    @FXML
    private void onMenu() throws Exception {
        Main.getInstance().goToHome();
    }
}