package org.example.game_farmfrenzy2.model.game.service;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.GridManager;
import org.example.game_farmfrenzy2.model.machine.Machine;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class MachineProcessingService {
    private final GameState gameState;

    public MachineProcessingService(GameState gameState) {
        this.gameState = gameState;
    }

    public void tick() {
        for (Machine machine : gameState.getMachines()) {
            if (!machine.isRunning()) continue;

            int progress = machine.getCurrentProgress() + 1;
            machine.setCurrentProgress(progress);

            if (progress < machine.getProcessingTime()) continue;

            List<Product> inputs = findInputs(machine);
            if (inputs != null && inputs.size() >= machine.getRequiredInputCount()) {
                List<Product> outputs = machine.process(inputs);
                for (Product input : inputs) {
                    gameState.getWarehouse().removeProduct(input);
                }
                for (Product output : outputs) {
                    if (output == null) continue;
                    placeOutput(output, machine.getPosition());
                }
            }

            machine.setRunning(false);
            machine.setCurrentProgress(0);
            Position mp = machine.getPosition();
            gameState.getGrid().setCell(mp.getRow(), mp.getCol(), machine);
        }
    }

    private void placeOutput(Product output, Position machinePos) {
        Position drop = findAdjacentEmpty(machinePos);
        if (drop == null) drop = findEmptyCell();
        if (drop == null) {
            if (!gameState.getWarehouse().isFull()) {
                gameState.getWarehouse().addProduct(output);
            }
            return;
        }
        output.setPosition(drop);
        gameState.getProductsOnGround().add(output);
        gameState.getGrid().setCell(drop.getRow(), drop.getCol(), output);
    }

    private List<Product> findInputs(Machine machine) {
        List<Product> inputs = new ArrayList<>();
        int needed = machine.getRequiredInputCount();
        for (Product p : gameState.getWarehouse().getProducts()) {
            if (machine.canProcess(p) && inputs.size() < needed) {
                inputs.add(p);
            }
        }
        return inputs.size() >= needed ? inputs : null;
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