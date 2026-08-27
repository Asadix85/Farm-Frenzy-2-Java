package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.controller.GameController;
import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.machine.*;
import org.example.game_farmfrenzy2.model.product.Thread;
import org.example.game_farmfrenzy2.model.exceptions.GameException;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.game.LevelConfig;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.structure.Airplane;
import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.structure.Vehicle;
import org.example.game_farmfrenzy2.model.structure.WaterWell;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameView implements Initializable {
    @FXML private VBox root;
    @FXML private GridPane gridPane;
    @FXML private Label coinLabel;
    @FXML private Label timeLabel;
    @FXML private Label targetLabel;

    private GameController controller;
    private int level;
    private final Map<String, SpriteAnimator> animators = new HashMap<>();

    private static final double CELL_SIZE = 80;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image bg = ImageLoader.get("/images/game/farm_bg.png");
        if (bg != null) {
            BackgroundImage backgroundImage = new BackgroundImage(
                    bg,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            root.setBackground(new Background(backgroundImage));
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
        this.level = controller.getGameState().getLevelNumber();
        this.controller.setView(this);
        initializeGrid();
        updateGrid();
        updateInfo();
    }

    public void setLevel(int level) {
        this.level = level;
        this.controller = Main.getInstance().getCurrentGame();
        if (controller == null) {
            Main.getInstance().startNewGame(level);
            this.controller = Main.getInstance().getCurrentGame();
        }
        this.controller.setView(this);
        initializeGrid();
        updateGrid();
        updateInfo();
    }

    private void initializeGrid() {
        stopAllAnimations();
        gridPane.getChildren().clear();
        for (int r = 0; r < GridManager.ROWS; r++) {
            for (int c = 0; c < GridManager.COLS; c++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);
                cell.setStyle("-fx-border-color: rgba(255,255,255,0.25); -fx-border-width: 1;");
                int row = r, col = c;
                cell.setOnMouseClicked(e -> onCellClick(row, col));
                gridPane.add(cell, c, r);
            }
        }
    }

    public void updateGrid() {
        stopAllAnimations();
        GridManager grid = controller.getGameState().getGrid();
        for (int r = 0; r < GridManager.ROWS; r++) {
            for (int c = 0; c < GridManager.COLS; c++) {
                StackPane cell = (StackPane) gridPane.getChildren().get(r * GridManager.COLS + c);
                cell.getChildren().clear();
                Object obj = grid.getCell(r, c);
                if (obj != null) {
                    ImageView view = SpriteFactory.create(obj, r + "_" + c, animators);
                    if (view != null) {
                        cell.getChildren().add(view);
                    }
                }
            }
        }
        updateInfo();
    }

    private void stopAllAnimations() {
        for (SpriteAnimator a : animators.values()) {
            a.stop();
        }
        animators.clear();
    }

    public void updateInfo() {
        LevelConfig cfg = controller.getGameState().getConfig();
        coinLabel.setText("Coins: " + controller.getGameState().getCoins());
        timeLabel.setText("Time: " + controller.getGameState().getTimeLeft() + "s");

        int chickens = 0;
        for (Animal a : controller.getGameState().getAnimals()) {
            if (a.isAlive() && a instanceof Chicken) chickens++;
        }

        StringBuilder sb = new StringBuilder();
        if (cfg.targetChickens > 0) sb.append("Chickens ").append(chickens).append("/").append(cfg.targetChickens).append("  ");
        if (cfg.targetEggPowder > 0) {
            int n = 0;
            for (Product p : controller.getGameState().getWarehouse().getProducts()) {
                if (p instanceof EggPowder) n++;
            }
            sb.append("Powder ").append(n).append("/").append(cfg.targetEggPowder).append("  ");
        }
        if (cfg.targetBread > 0) { sb.append("Bread ").append("/").append(cfg.targetBread).append("  "); }
        if (cfg.targetCoins > 0) sb.append("GoalCoins ").append(cfg.targetCoins).append("  ");

        WaterWell well = controller.getGameState().getWaterWell();
        if (well != null) sb.append("| Water: ").append(well.getWaterCharges());

        targetLabel.setText(sb.toString().trim());
        Vehicle v = controller.getGameState().getVehicle();
        Airplane a = controller.getGameState().getAirplane();
        String extra = "";
        if (v != null && v.isTraveling()) {
            extra += " | Truck: " + v.getTravelProgress() + "/" + v.getSpeed();
        }
        if (a != null && a.isFlying()) {
            extra += " | Plane: " + a.getFlightProgress() + "/" + a.getSpeed();
        }
    }

    private void onCellClick(int row, int col) {
        try {
            Object obj = controller.getGameState().getGrid().getCell(row, col);
            if (obj instanceof WaterWell) controller.fillWell();
            else if (obj instanceof Product && ((Product) obj).isOnGround())
                controller.collectProduct((Product) obj);
            else if (obj instanceof Machine m) {
                if (!m.isRunning()) controller.startMachine(m);
                else controller.stopMachine(m);
            } else if (obj instanceof WildAnimal w) controller.captureWildAnimal(w);
            else if (obj == null) controller.plantGrassAt(row, col);
        } catch (RuntimeException e) {
            showError(e.getMessage() != null ? e.getMessage() : "Action failed!");
        }
        updateGrid();
        updateInfo();
        controller.checkWinLose();
    }

    public void showEndScreen(boolean won, int stars, int coins, int timeSpent) {
        stopAllAnimations();
        try {
            Main.getInstance().goToEnd(won, stars, level, coins, timeSpent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onWarehouse() throws Exception {
        Main.getInstance().goToWarehouse(level);
    }

    @FXML
    private void onMenu() throws Exception {
        stopAllAnimations();
        Main.getInstance().goToHome();
    }

    @FXML
    private void onBuyChicken() {
        runAction(() -> controller.buyChicken());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Action Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onShop() throws Exception {
        Main.getInstance().goToShop(level);
    }

    @FXML
    private void onAirplane() throws Exception {
        Main.getInstance().goToAirplane(level);
    }

    @FXML
    private void onSettings() throws Exception {
        Main.getInstance().goToSettings();
    }


    private void runAction(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            showError(cause.getMessage() != null ? cause.getMessage() : "Action failed!");
        }
        updateGrid();
        updateInfo();
        controller.checkWinLose();
    }

    @FXML private void onBuyCow() { runAction(() -> controller.buyCow()); }
    @FXML private void onBuyOstrich() { runAction(() -> controller.buyOstrich()); }
    @FXML private void onBuyDog() { runAction(() -> controller.buyDog()); }
    @FXML private void onBuyCat() { runAction(() -> controller.buyCat()); }
}