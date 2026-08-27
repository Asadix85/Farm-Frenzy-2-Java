package org.example.game_farmfrenzy2.model.structure;

import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private static int nextId = 1;
    private final int id;
    private int capacity;
    private int speed;
    private List<Product> cargo;
    private boolean isTraveling;
    private int travelProgress;
    private int level;

    public Vehicle(int capacity, int speed) {
        this.id = nextId++;
        this.capacity = capacity;
        this.speed = speed;
        this.cargo = new ArrayList<>();
        this.isTraveling = false;
        this.travelProgress = 0;
        this.level = 1;
    }

    public int getId() { return id; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public List<Product> getCargo() { return cargo; }
    public boolean isTraveling() { return isTraveling; }
    public int getTravelProgress() { return travelProgress; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public boolean loadProducts(List<Product> products) {
        if (isTraveling) return false;
        if (cargo.size() + products.size() > capacity) return false;
        cargo.addAll(products);
        return true;
    }

    public void startTravel() {
        if (cargo.isEmpty() || isTraveling) return;
        isTraveling = true;
        travelProgress = 0;
    }

    public int updateTravel() {
        if (!isTraveling) return 0;

        travelProgress++;
        if (travelProgress >= speed) {
            isTraveling = false;
            travelProgress = 0;

            int totalCoins = 0;
            for (Product p : cargo) {
                totalCoins += p.getSellPrice();
            }
            cargo.clear();
            return totalCoins;
        }
        return 0;
    }

    public void upgrade() {
        if (level < 5) {
            level++;
            capacity += 2;
            speed = Math.max(5, speed - 2);
        }
    }

    public int getUpgradeCost() {
        return level * 50;
    }

    public boolean canUpgrade() {
        return level < 5;
    }

    public int getCurrentLoad() {
        return cargo.size();
    }

    public boolean isEmpty() {
        return cargo.isEmpty();
    }
}