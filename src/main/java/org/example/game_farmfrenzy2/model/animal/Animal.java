package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.Product;

public abstract class Animal {
    private static int nextId = 1;
    private final int id;
    private Position position;
    protected int hunger;
    protected boolean alive;
    protected double speed;

    public Animal(Position position, double speed) {
        this.id = nextId++;
        this.position = position;
        this.hunger = 50;
        this.alive = true;
        this.speed = speed;
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = Math.max(0, Math.min(100, hunger)); }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public double getSpeed() { return speed; }

    public abstract void eat(Grass grass);
    public abstract Product produce();
    public void die() { this.alive = false; }
}