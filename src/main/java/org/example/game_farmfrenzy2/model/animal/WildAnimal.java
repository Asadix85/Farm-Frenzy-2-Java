package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.Position;

public abstract class WildAnimal {
    private static int nextId = 1;
    private final int id;
    private Position position;
    private boolean isCaptured;
    private int captureProgress;
    private boolean isAlive;
    private double speed;

    public WildAnimal(Position position, double speed) {
        this.id = nextId++;
        this.position = position;
        this.isCaptured = false;
        this.captureProgress = 0;
        this.isAlive = true;
        this.speed = speed;
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public boolean isCaptured() { return isCaptured; }
    public void setCaptured(boolean captured) { this.isCaptured = captured; }
    public int getCaptureProgress() { return captureProgress; }
    public void setCaptureProgress(int progress) {
        this.captureProgress = Math.max(0, Math.min(100, progress));
        if (this.captureProgress >= 100) {
            this.isCaptured = true;
        }
    }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
    public double getSpeed() { return speed; }

    public abstract void attack(Animal target);
    public abstract int getSellPrice();
}