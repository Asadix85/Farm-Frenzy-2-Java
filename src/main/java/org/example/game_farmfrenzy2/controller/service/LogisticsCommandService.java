package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.model.exceptions.InvalidActionException;
import org.example.game_farmfrenzy2.model.exceptions.NotEnoughCoinsException;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;

import java.util.List;

public class LogisticsCommandService {
    private final GameState gameState;

    public LogisticsCommandService(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized boolean loadVehicle(List<Product> products) {
        if (gameState.getVehicle() == null) return false;
        return gameState.getVehicle().loadProducts(products);
    }

    public synchronized void startVehicle() {
        if (gameState.getVehicle() == null)
            throw new InvalidActionException("Vehicle not available!");
        if (gameState.getVehicle().isEmpty())
            throw new InvalidActionException("Vehicle is empty!");
        gameState.getVehicle().startTravel();
    }

    public synchronized void orderFromCity(ProductType type, int quantity) {
        if (gameState.getAirplane() == null)
            throw new InvalidActionException("Airplane not available!");
        int cost = priceOf(type) * quantity;
        if (gameState.getCoins() < cost)
            throw new NotEnoughCoinsException(cost, gameState.getCoins());
        if (!gameState.getAirplane().placeOrder(type, quantity))
            throw new InvalidActionException("Cannot place order (capacity?)");
        gameState.setCoins(gameState.getCoins() - cost);
    }

    public synchronized void startAirplane() {
        if (gameState.getAirplane() == null)
            throw new InvalidActionException("Airplane not available!");
        if (gameState.getAirplane().isEmpty())
            throw new InvalidActionException("No orders to send!");
        gameState.getAirplane().startFlight();
    }

    private int priceOf(ProductType type) {
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
}