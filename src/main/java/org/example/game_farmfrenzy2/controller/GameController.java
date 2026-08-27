package org.example.game_farmfrenzy2.controller;

import org.example.game_farmfrenzy2.controller.service.*;
import org.example.game_farmfrenzy2.model.animal.WildAnimal;
import org.example.game_farmfrenzy2.model.game.GameLoop;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.service.WinConditionService;
import org.example.game_farmfrenzy2.model.machine.Machine;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.view.GameView;

import java.util.List;

public class GameController {
    private final GameState gameState;
    private final FarmingService farmingService;
    private final PurchaseService purchaseService;
    private final MachineControlService machineService;
    private final CaptureService captureService;
    private final LogisticsCommandService logisticsService;
    private final WinConditionService winService;
    private GameView view;
    private final GameLoop gameLoop;

    public GameController(int level) {
        this.gameState = new GameState(level);
        this.gameLoop = new GameLoop(gameState);
        this.farmingService = new FarmingService(gameState);
        this.purchaseService = new PurchaseService(gameState);
        this.machineService = new MachineControlService(gameState);
        this.captureService = new CaptureService(gameState);
        this.logisticsService = new LogisticsCommandService(gameState);
        this.winService = new WinConditionService(gameState);
        new UpgradeApplyService().apply(gameState);
        gameLoop.start();
    }

    public void setView(GameView view) {
        this.view = view;
        gameLoop.setOnTick(() -> {
            if (view != null) {
                javafx.application.Platform.runLater(() -> {
                    view.updateGrid();
                    view.updateInfo();
                    checkWinLose();
                });
            }
        });
    }

    public GameState getGameState() { return gameState; }

    public void fillWell() { farmingService.fillWell(); }
    public void plantGrassAt(int r, int c) { farmingService.plantGrassAt(r, c); }
    public void collectProduct(Product p) { farmingService.collectProduct(p); }
    public int sellProduct(Product p) { return farmingService.sellProduct(p); }

    public void buyChicken() { purchaseService.buyChicken(); }
    public void buyCow() { purchaseService.buyCow(); }
    public void buyOstrich() { purchaseService.buyOstrich(); }
    public void buyDog() { purchaseService.buyDog(); }
    public void buyCat() { purchaseService.buyCat(); }

    public void startMachine(Machine m) { machineService.startMachine(m); }
    public void stopMachine(Machine m) { machineService.stopMachine(m); }

    public void captureWildAnimal(WildAnimal a) { captureService.capture(a); }

    public boolean loadVehicle(List<Product> products) { return logisticsService.loadVehicle(products); }
    public void startVehicle() { logisticsService.startVehicle(); }
    public void orderFromCity(ProductType type, int qty) { logisticsService.orderFromCity(type, qty); }
    public void startAirplane() { logisticsService.startAirplane(); }

    public synchronized void checkWinLose() {
        if (gameState.isGameOver()) return;
        if (winService.isWon()) {
            gameState.setWin(true);
            gameLoop.stop();
            int stars = winService.calculateStars();
            int timeSpent = gameState.getConfig().timeLimit - gameState.getTimeLeft();
            if (view != null) view.showEndScreen(true, stars, gameState.getCoins(), timeSpent);
            return;
        }
        if (winService.isLost()) {
            gameState.setLose(true);
            gameLoop.stop();
            if (view != null) view.showEndScreen(false, 0, gameState.getCoins(), 0);
        }
    }

    public void pauseGame() { if (gameLoop != null) gameLoop.pause(); }
    public void resumeGame() { if (gameLoop != null) gameLoop.resume(); }
    public void stopGame() { if (gameLoop != null) gameLoop.stop(); }
}