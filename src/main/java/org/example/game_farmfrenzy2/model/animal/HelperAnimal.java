package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;

public abstract class HelperAnimal {
    private static int nextId = 1;
    private final int id;
    private Position position;
    private boolean isActive;
    private double speed;

    public HelperAnimal(Position position, double speed) {
        this.id = nextId++;
        this.position = position;
        this.isActive = true;
        this.speed = speed;
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public double getSpeed() { return speed; }

    public abstract void performDuty(GameState gameState);
}