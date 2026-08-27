package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.exceptions.InvalidActionException;
import org.example.game_farmfrenzy2.model.exceptions.NotEnoughCoinsException;
import org.example.game_farmfrenzy2.model.exceptions.WarehouseFullException;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.structure.WaterWell;

public class FarmingService {
    private final GameState gameState;

    public FarmingService(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized void fillWell() {
        WaterWell well = gameState.getWaterWell();
        if (well == null) throw new InvalidActionException("Well not found!");
        if (gameState.getCoins() < well.getFillCost())
            throw new NotEnoughCoinsException(well.getFillCost(), gameState.getCoins());
        well.fill(gameState.getCoins());
        gameState.setCoins(gameState.getCoins() - well.getFillCost());
    }

    public synchronized void plantGrassAt(int row, int col) {
        GridManager grid = gameState.getGrid();
        if (!grid.isGrassArea(row, col))
            throw new InvalidActionException("You can only plant grass in the farm area!");
        if (grid.isOccupied(row, col))
            throw new InvalidActionException("This cell is not empty!");
        WaterWell well = gameState.getWaterWell();
        if (well == null || !well.useWater())
            throw new InvalidActionException("Not enough water! Click the well first.");
        Grass grass = new Grass(new Position(row, col));
        grid.setCell(row, col, grass);
        gameState.getGrasses().add(grass);
    }

    public synchronized void collectProduct(Product product) {
        if (gameState.getWarehouse().isFull())
            throw new WarehouseFullException();
        if (!gameState.getProductsOnGround().remove(product))
            throw new InvalidActionException("Product not found on ground!");
        gameState.getWarehouse().addProduct(product);
        int r = product.getPosition().getRow();
        int c = product.getPosition().getCol();
        if (gameState.getGrid().getCell(r, c) == product) {
            gameState.getGrid().clearCell(r, c);
        }
    }

    public synchronized int sellProduct(Product product) {
        if (gameState.getWarehouse().removeProduct(product) != null) {
            int price = product.getSellPrice();
            gameState.setCoins(gameState.getCoins() + price);
            return price;
        }
        return 0;
    }
}