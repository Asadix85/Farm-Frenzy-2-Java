package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.model.animal.WildAnimal;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.exceptions.InvalidActionException;
import org.example.game_farmfrenzy2.model.exceptions.WarehouseFullException;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.product.CapturedAnimal;

public class CaptureService {
    private final GameState gameState;

    public CaptureService(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized void capture(WildAnimal animal) {
        if (animal == null) throw new InvalidActionException("No animal selected!");
        if (animal.isCaptured()) throw new InvalidActionException("Already captured!");
        if (!animal.isAlive()) throw new InvalidActionException("Animal is dead!");

        animal.setCaptureProgress(animal.getCaptureProgress() + 10);

        if (animal.isCaptured()) {
            if (gameState.getWarehouse().isFull()) {
                animal.setCaptureProgress(0);
                throw new WarehouseFullException();
            }
            Position pos = animal.getPosition();
            CapturedAnimal captured = new CapturedAnimal(
                    new Position(pos.getRow(), pos.getCol()),
                    animal.getClass().getSimpleName(),
                    animal.getSellPrice()
            );
            gameState.getWarehouse().addProduct(captured);
            gameState.removeWildAnimal(animal);
            gameState.getGrid().clearCell(pos.getRow(), pos.getCol());
        }
    }
}