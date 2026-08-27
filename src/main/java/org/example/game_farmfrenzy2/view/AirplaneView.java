package org.example.game_farmfrenzy2.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.controller.GameController;
import org.example.game_farmfrenzy2.model.structure.Airplane;
import org.example.game_farmfrenzy2.model.product.ProductType;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AirplaneView implements Initializable {
    @FXML private StackPane root;
    @FXML private Label coinLabel;
    @FXML private ComboBox<String> productCombo;
    @FXML private TextField quantityField;
    @FXML private ListView<String> orderList;
    @FXML private Label statusLabel;

    private GameController controller;
    private int level;
    private Map<ProductType, Integer> orders;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imgUrl = getClass().getResource("/images/airplane_bg.png");
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
        this.orders = new HashMap<>();
        initializeComboBox();
        updateUI();
    }

    public void setLevel(int level) {
        this.level = level;
        this.controller = Main.getInstance().getCurrentGame();
        if (controller == null) {
            Main.getInstance().startNewGame(level);
            this.controller = Main.getInstance().getCurrentGame();
        }
        this.orders = new HashMap<>();
        initializeComboBox();
        updateUI();
    }

    private void initializeComboBox() {
        productCombo.getItems().clear();
        productCombo.getItems().addAll(
                "Egg",
                "Egg Powder",
                "Milk",
                "Feather",
                "Bread",
                "Butter",
                "Thread",
                "Color",
                "Cloth",
                "Clothes"
        );
        productCombo.setValue("Egg");
    }

    public void updateUI() {
        if (controller == null) return;

        coinLabel.setText(String.valueOf(controller.getGameState().getCoins()));
        orderList.getItems().clear();
        for (Map.Entry<ProductType, Integer> entry : orders.entrySet()) {
            orderList.getItems().add(entry.getKey().toString() + "  x" + entry.getValue());
        }

        Airplane airplane = controller.getGameState().getAirplane();
        if (airplane != null) {
            if (airplane.isFlying()) {
                statusLabel.setText("Airplane is flying... (" +
                        airplane.getFlightProgress() + "/" + airplane.getSpeed() + ")");
                statusLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #f1c40f;");
            } else if (orders.isEmpty()) {
                statusLabel.setText("Airplane is ready. Place orders!");
                statusLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");
            } else {
                statusLabel.setText("Orders ready to send!");
                statusLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
            }
        } else {
            statusLabel.setText("Airplane not available in this level.");
            statusLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        }
    }

    @FXML
    private void onAddOrder() {
        String selected = productCombo.getValue();
        if (selected == null) return;

        String qtyText = quantityField.getText().trim();
        if (qtyText.isEmpty()) {
            showAlert("Error", "Please enter quantity!");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                showAlert("Error", "Quantity must be positive!");
                return;
            }

            ProductType type = ProductType.valueOf(selected.toUpperCase().replace(" ", "_"));

            Airplane airplane = controller.getGameState().getAirplane();
            if (airplane != null && airplane.isFlying()) {
                showAlert("Error", "Airplane is already flying!");
                return;
            }

            orders.put(type, orders.getOrDefault(type, 0) + quantity);
            updateUI();
            quantityField.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number!");
        } catch (IllegalArgumentException e) {
            showAlert("Error", "Invalid product type!");
        }
    }

    @FXML
    private void onSend() {
        if (orders.isEmpty()) {
            showAlert("Error", "No orders to send!");
            return;
        }

        int totalCost = 0;
        for (Map.Entry<ProductType, Integer> entry : orders.entrySet()) {
            totalCost += getProductPrice(entry.getKey()) * entry.getValue();
        }

        if (controller.getGameState().getCoins() < totalCost) {
            showAlert("Not Enough Coins", "You need " + totalCost + " coins for this order!");
            return;
        }

        controller.getGameState().setCoins(
                controller.getGameState().getCoins() - totalCost
        );

        Airplane airplane = controller.getGameState().getAirplane();
        if (airplane == null) {
            showAlert("Error", "Airplane not available!");
            return;
        }

        for (Map.Entry<ProductType, Integer> entry : orders.entrySet()) {
            airplane.placeOrder(entry.getKey(), entry.getValue());
        }

        airplane.startFlight();
        orders.clear();

        updateUI();
        showAlert("Success", "Airplane sent! It will return in " + airplane.getSpeed() + " seconds.");
    }

    @FXML
    private void onBack() throws Exception {
        Main.getInstance().goToGame(level);
    }

    private int getProductPrice(ProductType type) {
        switch (type) {
            case EGG: return 5;
            case EGG_POWDER: return 10;
            case MILK: return 8;
            case FEATHER: return 7;
            case BREAD: return 15;
            case BUTTER: return 20;
            case THREAD: return 12;
            case COLOR: return 15;
            case CLOTH: return 25;
            case CLOTHES: return 40;
            default: return 10;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}