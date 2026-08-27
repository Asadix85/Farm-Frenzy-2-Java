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
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.structure.Vehicle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label coinLabel;
    @FXML private Label capacityLabel;
    @FXML private ListView<String> productList;

    private GameController controller;
    private int level;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/game/farm_bg.png");
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
        }
    }

    public void setController(GameController controller, int level) {
        this.controller = controller;
        this.level = level;
        updateUI();
    }

    public void setLevel(int level) {
        this.level = level;
        this.controller = Main.getInstance().getCurrentGame();
        if (controller == null) {
            Main.getInstance().startNewGame(level);
            this.controller = Main.getInstance().getCurrentGame();
        }
        updateUI();
    }

    public void updateUI() {
        if (controller == null) return;

        coinLabel.setText(String.valueOf(controller.getGameState().getCoins()));

        int current = controller.getGameState().getWarehouse().getCurrentVolume();
        int capacity = controller.getGameState().getWarehouse().getCapacity();
        capacityLabel.setText(current + "/" + capacity);

        productList.getItems().clear();
        for (Product p : controller.getGameState().getWarehouse().getProducts()) {
            String name = p.getClass().getSimpleName();
            int price = p.getSellPrice();
            productList.getItems().add(name + "  •  " + price + " coins");
        }
    }

    @FXML
    private void onSellSelected() {
        if (controller == null) return;

        int selectedIndex = productList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showAlert("No Selection", "Please select a product to sell.");
            return;
        }

        Product selectedProduct = controller.getGameState().getWarehouse()
                .getProducts().get(selectedIndex);

        int price = controller.sellProduct(selectedProduct);
        if (price > 0) {
            updateUI();
            showAlert("Success", "Product sold for " + price + " coins!");
        } else {
            showAlert("Error", "Could not sell this product.");
        }
    }

    @FXML
    private void onSendVehicle() {
        if (controller == null) return;
        Vehicle vehicle = controller.getGameState().getVehicle();
        if (vehicle == null) {
            showAlert("Error", "Vehicle not available!");
            return;
        }
        if (vehicle.isTraveling()) {
            showAlert("Busy", "Vehicle is already traveling!");
            return;
        }

        List<Product> products = new ArrayList<>(controller.getGameState().getWarehouse().getProducts());
        if (products.isEmpty()) {
            showAlert("Empty", "No products in warehouse!");
            return;
        }

        int capacity = vehicle.getCapacity();
        List<Product> toLoad = new ArrayList<>();
        for (Product p : products) {
            if (toLoad.size() >= capacity) break;
            toLoad.add(p);
        }

        for (Product p : toLoad) {
            controller.getGameState().getWarehouse().removeProduct(p);
        }

        if (!controller.loadVehicle(toLoad)) {
            for (Product p : toLoad) {
                controller.getGameState().getWarehouse().addProduct(p);
            }
            showAlert("Error", "Could not load vehicle!");
            return;
        }

        controller.startVehicle();
        updateUI();
        showAlert("Sent", "Vehicle sent to city with " + toLoad.size() + " products.");
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