package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.controller.GameController;
import org.example.game_farmfrenzy2.model.database.UpgradeRepository;
import org.example.game_farmfrenzy2.model.machine.Machine;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label coinLabel;
    @FXML private ListView<String> upgradeList;

    private GameController controller;
    private int level;
    private List<UpgradeableItem> items;
    private final UpgradeRepository upgradeRepo = new UpgradeRepository();

    private static class UpgradeableItem {
        String name;
        int currentLevel;
        int maxLevel;
        int upgradeCost;
        Runnable upgradeAction;

        UpgradeableItem(String name, int currentLevel, int maxLevel, int upgradeCost, Runnable upgradeAction) {
            this.name = name;
            this.currentLevel = currentLevel;
            this.maxLevel = maxLevel;
            this.upgradeCost = upgradeCost;
            this.upgradeAction = upgradeAction;
        }

        String getDisplayText() {
            return name + "  •  Lv " + currentLevel + "/" + maxLevel + "  •  " + upgradeCost + " coins";
        }

        boolean canUpgrade() {
            return currentLevel < maxLevel;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/mission.png");
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/images/game/farm_bg.png");
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
        }
    }

    public void setController(GameController controller, int level) {
        this.controller = controller;
        this.level = level;
        initializeItems();
        updateUI();
    }

    public void setLevel(int level) {
        this.level = level;
        this.controller = Main.getInstance().getCurrentGame();
        if (controller == null) {
            Main.getInstance().startNewGame(level);
            this.controller = Main.getInstance().getCurrentGame();
        }
        initializeItems();
        updateUI();
    }

    private void initializeItems() {
        items = new ArrayList<>();
        int userId = Main.getInstance().getCurrentUserId();

        int warehouseLv = upgradeRepo.getUpgradeLevel(userId, "WAREHOUSE");
        items.add(new UpgradeableItem(
                "Warehouse",
                warehouseLv, 5, 50 + (warehouseLv - 1) * 25,
                () -> {
                    int newLv = warehouseLv + 1;
                    upgradeRepo.saveUpgradeLevel(userId, "WAREHOUSE", newLv);
                    controller.getGameState().getWarehouse()
                            .setCapacity(20 + (newLv - 1) * 10);
                }
        ));

        int wellLv = upgradeRepo.getUpgradeLevel(userId, "WELL");
        items.add(new UpgradeableItem(
                "Water Well",
                wellLv, 3, 30 + (wellLv - 1) * 20,
                () -> {
                    int newLv = wellLv + 1;
                    upgradeRepo.saveUpgradeLevel(userId, "WELL", newLv);
                    if (controller.getGameState().getWaterWell() != null) {
                        controller.getGameState().getWaterWell().setLevel(newLv);
                    }
                }
        ));

        int vehicleLv = upgradeRepo.getUpgradeLevel(userId, "VEHICLE");
        items.add(new UpgradeableItem(
                "Vehicle",
                vehicleLv, 5, 75 + (vehicleLv - 1) * 30,
                () -> {
                    int newLv = vehicleLv + 1;
                    upgradeRepo.saveUpgradeLevel(userId, "VEHICLE", newLv);
                    if (controller.getGameState().getVehicle() != null) {
                        controller.getGameState().getVehicle().upgrade();
                    }
                }
        ));

        int airplaneLv = upgradeRepo.getUpgradeLevel(userId, "AIRPLANE");
        items.add(new UpgradeableItem(
                "Airplane",
                airplaneLv, 5, 100 + (airplaneLv - 1) * 40,
                () -> {
                    int newLv = airplaneLv + 1;
                    upgradeRepo.saveUpgradeLevel(userId, "AIRPLANE", newLv);
                    if (controller.getGameState().getAirplane() != null) {
                        controller.getGameState().getAirplane().upgrade();
                    }
                }
        ));

        for (Machine machine : controller.getGameState().getMachines()) {
            String key = machine.getClass().getSimpleName().toUpperCase();
            int mLv = upgradeRepo.getUpgradeLevel(userId, key);
            items.add(new UpgradeableItem(
                    machine.getClass().getSimpleName(),
                    mLv, 3, 40 + (mLv - 1) * 20,
                    () -> {
                        int newLv = mLv + 1;
                        upgradeRepo.saveUpgradeLevel(userId, key, newLv);
                        machine.setLevel(newLv);
                    }
            ));
        }
    }

    public void updateUI() {
        if (controller == null) return;

        coinLabel.setText(String.valueOf(controller.getGameState().getCoins()));
        upgradeList.getItems().clear();

        for (UpgradeableItem item : items) {
            if (item.canUpgrade()) {
                upgradeList.getItems().add(item.getDisplayText());
            } else {
                upgradeList.getItems().add(item.name + "  •  MAX LEVEL");
            }
        }
    }

    @FXML
    private void onUpgrade() {
        int selectedIndex = upgradeList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showAlert("No Selection", "Please select an item to upgrade.");
            return;
        }

        UpgradeableItem selected = items.get(selectedIndex);

        if (!selected.canUpgrade()) {
            showAlert("Max Level", "This item is already at maximum level!");
            return;
        }

        if (controller.getGameState().getCoins() < selected.upgradeCost) {
            showAlert("Not Enough Coins", "You need " + selected.upgradeCost + " coins to upgrade!");
            return;
        }

        controller.getGameState().setCoins(
                controller.getGameState().getCoins() - selected.upgradeCost
        );

        selected.upgradeAction.run();
        selected.currentLevel++;
        initializeItems();
        updateUI();
        showAlert("Success", selected.name + " upgraded to level " + selected.currentLevel + "!");
    }

    @FXML
    private void onBack() throws Exception {
        Main.getInstance().goToGame(level);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}