package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.model.database.ProgressRepository;
import org.example.game_farmfrenzy2.model.database.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterView implements Initializable {
    @FXML private StackPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label errorLabel;

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

    @FXML
    private void onRegister() throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirm = confirmField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            errorLabel.setText("Please fill all fields!");
            return;
        }

        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match!");
            return;
        }

        if (username.length() < 3) {
            errorLabel.setText("Username must be at least 3 characters!");
            return;
        }

        if (password.length() < 4) {
            errorLabel.setText("Password must be at least 4 characters!");
            return;
        }

        UserRepository repo = new UserRepository();
        if (repo.registerUser(username, password)) {
            int userId = repo.getUserId(username);
            ProgressRepository progressRepo = new ProgressRepository();
            progressRepo.unlockLevel(userId, 1);
            Main.getInstance().setCurrentUser(userId, username);
            Main.getInstance().goToHome();
        } else {
            errorLabel.setText("Username already exists!");
        }
    }

    @FXML
    private void onBack() throws Exception {
        Main.getInstance().goToLogin();
    }
}