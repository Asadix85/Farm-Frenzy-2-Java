package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class Machine {
    private static int nextId = 1;
    private int level = 1;
    private final int id;
    private Position position;
    private boolean running;
    private int processingTime;
    private int currentProgress;
    private int capacity;
    private List<Product> inputs;

    public Machine(Position position, int processingTime, int capacity) {
        this.id = nextId++;
        this.position = position;
        this.running = false;
        this.processingTime = processingTime;
        this.currentProgress = 0;
        this.capacity = capacity;
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public boolean isRunning() { return running; }
    public void setRunning(boolean running) {
        this.running = running;
        if (!running) this.currentProgress = 0;
    }

    public int getProcessingTime() { return processingTime; }
    public int getCurrentProgress() { return currentProgress; }
    public void setCurrentProgress(int progress) { this.currentProgress = progress; }
    public int getCapacity() { return capacity; }

    public List<Product> getInputs() { return inputs; }
    public void setInputs(List<Product> inputs) { this.inputs = inputs; }

    public abstract boolean canProcess(Product input);
    public abstract List<Product> process(List<Product> inputs);
    public abstract ProductType getInputType();
    public abstract ProductType getOutputType();
    public abstract int getRequiredInputCount();

    public Product processSingle(Product input) {
        List<Product> inputs = new ArrayList<>();
        inputs.add(input);
        List<Product> outputs = process(inputs);
        return outputs.isEmpty() ? null : outputs.get(0);
    }

    public void setLevel(int level) { this.level = level; }
    public int getLevel() { return level; }
}