package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.model.exceptions.InvalidActionException;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.machine.Machine;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class MachineControlService {
    private final GameState gameState;

    public MachineControlService(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized void startMachine(Machine machine) {
        if (machine.isRunning())
            throw new InvalidActionException("Machine is already running!");
        List<Product> inputs = findInputs(machine);
        if (inputs == null)
            throw new InvalidActionException("Not enough materials!");
        machine.setInputs(inputs);
        machine.setRunning(true);
        machine.setCurrentProgress(0);
    }

    public synchronized void stopMachine(Machine machine) {
        if (!machine.isRunning())
            throw new InvalidActionException("Machine is not running!");
        machine.setRunning(false);
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
}