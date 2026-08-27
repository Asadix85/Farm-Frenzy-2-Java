package org.example.game_farmfrenzy2.model.game.service;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.product.Thread;

import java.util.Map;

public class LogisticsTickService {
    private final GameState gameState;

    public LogisticsTickService(GameState gameState) {
        this.gameState = gameState;
    }

    public void tick() {
        if (gameState.getVehicle() != null) {
            int earned = gameState.getVehicle().updateTravel();
            if (earned > 0) {
                gameState.setCoins(gameState.getCoins() + earned);
            }
        }

        if (gameState.getAirplane() != null) {
            Map<ProductType, Integer> delivered = gameState.getAirplane().updateFlight();
            if (delivered != null) {
                for (Map.Entry<ProductType, Integer> entry : delivered.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        deliverOne(entry.getKey());
                    }
                }
            }
        }
    }

    private void deliverOne(ProductType type) {
        Position pos = findEmptyCell();
        if (pos != null) {
            Product product = createProduct(type, pos);
            if (product != null) {
                gameState.getProductsOnGround().add(product);
                gameState.getGrid().setCell(pos.getRow(), pos.getCol(), product);
            }
            return;
        }
        Product product = createProduct(type, new Position(0, 0));
        if (product != null && !gameState.getWarehouse().isFull()) {
            gameState.getWarehouse().addProduct(product);
        }
    }

    private Product createProduct(ProductType type, Position pos) {
        switch (type) {
            case EGG: return new Egg(pos);
            case EGG_POWDER: return new EggPowder(pos);
            case MILK: return new Milk(pos);
            case FEATHER: return new Feather(pos);
            case BREAD: return new Bread(pos);
            case BUTTER: return new Butter(pos);
            case THREAD: return new Thread(pos);
            case COLOR: return new Color(pos);
            case CLOTH: return new Cloth(pos);
            case CLOTHES: return new Clothes(pos);
            default: return null;
        }
    }

    private Position findEmptyCell() {
        GridManager grid = gameState.getGrid();
        for (int r = 0; r < GridManager.ROWS; r++) {
            for (int c = 0; c < GridManager.COLS; c++) {
                if (!grid.isOccupied(r, c)) return new Position(r, c);
            }
        }
        return null;
    }
}