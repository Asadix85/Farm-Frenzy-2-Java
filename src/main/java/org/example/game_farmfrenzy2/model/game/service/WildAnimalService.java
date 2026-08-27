package org.example.game_farmfrenzy2.model.game.service;

import org.example.game_farmfrenzy2.model.animal.Animal;
import org.example.game_farmfrenzy2.model.animal.Panda;
import org.example.game_farmfrenzy2.model.animal.WildAnimal;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.structure.Grass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WildAnimalService {
    private static final int SPAWN_INTERVAL = 20;
    private static final int MAX_WILD = 2;

    private final GameState gameState;
    private final Random random = new Random();
    private int spawnTimer = 0;

    public WildAnimalService(GameState gameState) {
        this.gameState = gameState;
    }

    public void tick() {
        spawnTimer++;
        if (gameState.getLevelNumber() >= 3
                && spawnTimer >= SPAWN_INTERVAL
                && gameState.getWildAnimals().size() < MAX_WILD) {
            spawn();
            spawnTimer = 0;
        }

        for (WildAnimal wild : new ArrayList<>(gameState.getWildAnimals())) {
            if (!wild.isAlive() || wild.isCaptured()) continue;
            if (!(wild instanceof Panda)) continue;

            Panda panda = (Panda) wild;
            panda.updateCooldown();
            moveTowardsTarget(panda);

            Animal target = panda.findNearestTarget(gameState);
            if (target != null && panda.canAttack() && isAdjacent(panda.getPosition(), target.getPosition())) {
                panda.attack(target);
                if (!target.isAlive()) {
                    Position pos = target.getPosition();
                    if (gameState.getGrid().getCell(pos.getRow(), pos.getCol()) == target) {
                        gameState.getGrid().clearCell(pos.getRow(), pos.getCol());
                    }
                }
            }
        }
    }

    private void spawn() {
        Position pos = findEmptyBorderCell();
        if (pos == null) return;
        gameState.addWildAnimal(new Panda(pos));
    }

    private void moveTowardsTarget(WildAnimal wild) {
        Animal target = null;
        if (wild instanceof Panda) {
            target = ((Panda) wild).findNearestTarget(gameState);
        }
        if (target == null) return;

        Position from = wild.getPosition();
        Position to = target.getPosition();
        int r = from.getRow();
        int c = from.getCol();
        int nr = r;
        int nc = c;

        if (Math.abs(to.getRow() - r) >= Math.abs(to.getCol() - c)) {
            nr = r + Integer.compare(to.getRow(), r);
        } else {
            nc = c + Integer.compare(to.getCol(), c);
        }

        if (nr < 0 || nr >= GridManager.ROWS || nc < 0 || nc >= GridManager.COLS) return;

        GridManager grid = gameState.getGrid();
        Object cell = grid.getCell(nr, nc);
        if (cell != null && !(cell instanceof Grass)) return;

        if (grid.getCell(r, c) == wild) grid.clearCell(r, c);
        if (cell instanceof Grass) {
            gameState.getGrasses().remove(cell);
            grid.clearCell(nr, nc);
        }

        wild.setPosition(new Position(nr, nc));
        grid.setCell(nr, nc, wild);
    }

    private Position findEmptyBorderCell() {
        GridManager grid = gameState.getGrid();
        List<Position> candidates = new ArrayList<>();
        for (int r = 0; r < GridManager.ROWS; r++) {
            for (int c = 0; c < GridManager.COLS; c++) {
                boolean border = r == 0 || r == GridManager.ROWS - 1 || c == 0 || c == GridManager.COLS - 1;
                if (border && !grid.isOccupied(r, c)) {
                    candidates.add(new Position(r, c));
                }
            }
        }
        if (candidates.isEmpty()) return null;
        return candidates.get(random.nextInt(candidates.size()));
    }

    private boolean isAdjacent(Position a, Position b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol()) == 1;
    }
}