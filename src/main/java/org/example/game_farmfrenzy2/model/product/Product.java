package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public abstract class Product {
    private static int nextId = 1;
    private final int id;
    private Position position;
    private boolean onGround;
    private boolean inWarehouse;
    private int volume;
    private int lifespan;
    private int currentLifespan;

    public Product(Position position, int volume) {
        this.id = nextId++;
        this.position = position;
        this.onGround = true;
        this.inWarehouse = false;
        this.volume = volume;
        this.lifespan = 15;
        this.currentLifespan = 0;
    }
    public int getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) {this.position = position;}
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    public boolean isInWarehouse() { return inWarehouse; }
    public void setInWarehouse(boolean inWarehouse) { this.inWarehouse = inWarehouse; }
    public int getVolume() { return volume; }
    public int getLifespan() { return lifespan; }
    public int getCurrentLifespan() { return currentLifespan; }
    public void setCurrentLifespan(int currentLifespan) { this.currentLifespan = currentLifespan; }

    public abstract int getSellPrice();
    public abstract ProductType getType();

    public boolean isExpired() {
        return currentLifespan >= lifespan;
    }
    public void increaseLifespan() {
        currentLifespan++;
    }
}