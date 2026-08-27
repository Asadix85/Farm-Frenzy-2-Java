package org.example.game_farmfrenzy2.model.game.service;

import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.structure.Grass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalBehaviorService {
    private final GameState gameState;
    private final Random random = new Random();

    public AnimalBehaviorService(GameState gameState) {
        this.gameState = gameState;
    }

    public void tick() {
        updateHunger();
        moveAnimals();
        produce();
        runHelpers();
        gameState.getGrasses().removeIf(Grass::isEaten);
    }

    private void updateHunger() {
        for (Animal animal : gameState.getAnimals()) {
            if (!animal.isAlive()) continue;
            int hunger = animal.getHunger() + 1;
            animal.setHunger(hunger);
            if (hunger >= 100) {
                animal.die();
                Position pos = animal.getPosition();
                if (gameState.getGrid().getCell(pos.getRow(), pos.getCol()) == animal) {
                    gameState.getGrid().clearCell(pos.getRow(), pos.getCol());
                }
            }
        }
    }

    private void moveAnimals() {
        for (Animal animal : new ArrayList<>(gameState.getAnimals())) {
            if (!animal.isAlive()) continue;
            moveAnimal(animal);
        }
    }

    private void moveAnimal(Animal animal) {
        GridManager grid = gameState.getGrid();
        Position pos = animal.getPosition();
        int r = pos.getRow();
        int c = pos.getCol();

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        List<int[]> options = new ArrayList<>();
        for (int[] d : dirs) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (nr < 0 || nr >= GridManager.ROWS || nc < 0 || nc >= GridManager.COLS) continue;
            Object cell = grid.getCell(nr, nc);
            if (cell == null || cell instanceof Grass) {
                options.add(new int[]{nr, nc});
            }
        }
        if (options.isEmpty()) return;

        int[] chosen = options.get(random.nextInt(options.size()));
        int nr = chosen[0];
        int nc = chosen[1];

        if (grid.getCell(r, c) == animal) {
            grid.clearCell(r, c);
        }

        Object target = grid.getCell(nr, nc);
        if (target instanceof Grass) {
            animal.eat((Grass) target);
            gameState.getGrasses().remove(target);
            grid.clearCell(nr, nc);
        }

        animal.setPosition(new Position(nr, nc));
        grid.setCell(nr, nc, animal);
    }

    private void produce() {
        for (Animal animal : gameState.getAnimals()) {
            if (!animal.isAlive() || !(animal instanceof DomesticAnimal)) continue;

            DomesticAnimal domestic = (DomesticAnimal) animal;
            domestic.setProductionTimer(domestic.getProductionTimer() - 1);

            int hungerThreshold = 70;
            if (animal instanceof Cow) hungerThreshold = 65;
            else if (animal instanceof Ostrich) hungerThreshold = 60;

            if (domestic.getProductionTimer() > 0 || domestic.getHunger() >= hungerThreshold) continue;

            Position drop = findAdjacentEmpty(animal.getPosition());
            if (drop == null) drop = findEmptyCell();
            if (drop == null) continue;

            Product product = createProductFor(domestic, drop);
            if (product == null) continue;

            domestic.setProductionTimer(10);
            gameState.getProductsOnGround().add(product);
            gameState.getGrid().setCell(drop.getRow(), drop.getCol(), product);
        }
    }

    private void runHelpers() {
        for (HelperAnimal helper : gameState.getHelperAnimals()) {
            if (helper.isActive()) {
                helper.performDuty(gameState);
            }
        }
    }

    private Product createProductFor(DomesticAnimal domestic, Position pos) {
        if (domestic instanceof Chicken) return new Egg(pos);
        if (domestic instanceof Cow) return new Milk(pos);
        if (domestic instanceof Ostrich) return new Feather(pos);
        return null;
    }

    private Position findAdjacentEmpty(Position pos) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        GridManager grid = gameState.getGrid();
        for (int[] d : dirs) {
            int nr = pos.getRow() + d[0];
            int nc = pos.getCol() + d[1];
            if (nr < 0 || nr >= GridManager.ROWS || nc < 0 || nc >= GridManager.COLS) continue;
            if (!grid.isOccupied(nr, nc)) return new Position(nr, nc);
        }
        return null;
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