package org.example.game_farmfrenzy2.model.structure;

import org.example.game_farmfrenzy2.model.product.ProductType;

import java.util.HashMap;
import java.util.Map;

public class Airplane {
    private static int nextId = 1;
    private final int id;
    private int capacity;
    private int speed;
    private Map<ProductType, Integer> orders;
    private boolean isFlying;
    private int flightProgress;
    private int level;

    public Airplane(int capacity, int speed) {
        this.id = nextId++;
        this.capacity = capacity;
        this.speed = speed;
        this.orders = new HashMap<>();
        this.isFlying = false;
        this.flightProgress = 0;
        this.level = 1;
    }

    public int getId() { return id; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public Map<ProductType, Integer> getOrders() { return orders; }
    public boolean isFlying() { return isFlying; }
    public int getFlightProgress() { return flightProgress; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public boolean placeOrder(ProductType type, int quantity) {
        if (isFlying) return false;

        int totalItems = 0;
        for (int q : orders.values()) {
            totalItems += q;
        }

        if (totalItems + quantity > capacity) return false;

        orders.put(type, orders.getOrDefault(type, 0) + quantity);
        return true;
    }

    public void startFlight() {
        if (orders.isEmpty() || isFlying) return;
        isFlying = true;
        flightProgress = 0;
    }

    public Map<ProductType, Integer> updateFlight() {
        if (!isFlying) return null;

        flightProgress++;
        if (flightProgress >= speed) {
            isFlying = false;
            flightProgress = 0;

            Map<ProductType, Integer> delivered = new HashMap<>(orders);
            orders.clear();
            return delivered;
        }
        return null;
    }

    public void upgrade() {
        if (level < 5) {
            level++;
            capacity += 5;
            speed = Math.max(5, speed - 3);
        }
    }

    public int getUpgradeCost() {
        return level * 75;
    }

    public boolean canUpgrade() {
        return level < 5;
    }

    public int getCurrentLoad() {
        int total = 0;
        for (int q : orders.values()) {
            total += q;
        }
        return total;
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }
}