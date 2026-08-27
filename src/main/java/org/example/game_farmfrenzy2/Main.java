package org.example.game_farmfrenzy2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.game_farmfrenzy2.controller.GameController;
import org.example.game_farmfrenzy2.view.*;
import org.example.game_farmfrenzy2.model.database.*;

public class Main extends Application {
    private static Main instance;
    private Stage primaryStage;
    private int currentUserId;
    private String currentUsername;
    private GameController currentGame;

    public static final double WINDOW_WIDTH = 1024;
    public static final double WINDOW_HEIGHT = 768;

    public static Main getInstance() {
        return instance;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUser(int userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
    }

    public GameController getCurrentGame() {
        return currentGame;
    }

    @Override
    public void stop() {
        SoundManager.getInstance().stopAll();
        DatabaseConnection.getInstance().close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.primaryStage = stage;
        primaryStage.setResizable(false);
        goToLoading();
    }

    public void goToLoading() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Loading.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Farm Frenzy 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToHome() throws Exception {
        SoundManager.getInstance().playMenuMusic();
        clearCurrentGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Home.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        HomeView view = loader.getController();
        view.setUsername(currentUsername);
        primaryStage.setTitle("Farm Frenzy 2 - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToLevelSelect() throws Exception {
        SoundManager.getInstance().playMenuMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/LevelSelect.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Farm Frenzy 2 - Level Select");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToGame(int level) throws Exception {
        ensureGame(level);
        if (currentGame != null) currentGame.resumeGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Game.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        GameView view = loader.getController();
        view.setController(currentGame);
        primaryStage.setTitle("Farm Frenzy 2 - Level " + level);
        primaryStage.setScene(scene);
        primaryStage.show();
        SoundManager.getInstance().playGameMusic();
    }

    public void goToGameFresh(int level) throws Exception {
        SoundManager.getInstance().playGameMusic();
        startNewGame(level);
        goToGame(level);
    }

    public void goToEnd(boolean won, int stars, int level, int coins, int timeSpent) throws Exception {
        SoundManager.getInstance().stopGameMusic();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/game_farmfrenzy2/End.fxml")
        );
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        EndView view = loader.getController();
        view.setResult(won, stars, level, coins, timeSpent);
        primaryStage.setTitle("Farm Frenzy 2 - Game Over");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToWarehouse(int level) throws Exception {
        ensureGame(level);
        if (currentGame != null) currentGame.pauseGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Warehouse.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        WarehouseView view = loader.getController();
        view.setController(currentGame, level);
        primaryStage.setTitle("Farm Frenzy 2 - Warehouse");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToLogin() throws Exception {
        SoundManager.getInstance().playMenuMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Login.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Farm Frenzy 2 - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToRegister() throws Exception {
        SoundManager.getInstance().playMenuMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Register.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Farm Frenzy 2 - Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToSettings() throws Exception {
        if (currentGame != null) currentGame.pauseGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Settings.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Farm Frenzy 2 - Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToShop(int level) throws Exception {
        ensureGame(level);
        if (currentGame != null) currentGame.pauseGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Shop.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        ShopView view = loader.getController();
        view.setController(currentGame, level);
        primaryStage.setTitle("Farm Frenzy 2 - Shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToAirplane(int level) throws Exception {
        ensureGame(level);
        if (currentGame != null) currentGame.pauseGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Airplane.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        AirplaneView view = loader.getController();
        view.setController(currentGame, level);
        primaryStage.setTitle("Farm Frenzy 2 - Airplane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void goToMission(int level) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/game_farmfrenzy2/Mission.fxml"));
        Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        MissionView view = loader.getController();
        view.setLevel(level);
        primaryStage.setTitle("Farm Frenzy 2 - Mission");
        primaryStage.setScene(scene);
        primaryStage.show();
        SoundManager.getInstance().playMenuMusic();
    }

    public void startNewGame(int level) {
        if (currentGame != null) {
            currentGame.stopGame();
        }
        currentGame = new GameController(level);
    }

    public void clearCurrentGame() {
        if (currentGame != null) {
            currentGame.stopGame();
            currentGame = null;
        }
    }

    private void ensureGame(int level) {
        if (currentGame == null
                || currentGame.getGameState().getLevelNumber() != level
                || currentGame.getGameState().isGameOver()) {
            startNewGame(level);
        }
    }
}