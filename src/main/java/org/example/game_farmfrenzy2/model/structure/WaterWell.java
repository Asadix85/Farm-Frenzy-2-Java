package org.example.game_farmfrenzy2.model.structure;

import org.example.game_farmfrenzy2.model.entities.Position;

public class WaterWell {
    private Position position;
    private int waterCharges;
    private int fillCost;
    private int fillAmount;
    private int level;

    public WaterWell(Position position) {
        this.position = position;
        this.waterCharges = 0;
        this.fillCost = 5;
        this.fillAmount = 5;
        this.level = 1;
    }

    public Position getPosition() { return position; }

    public int getWaterCharges() { return waterCharges; }

    public int getFillCost() { return fillCost; }

    public int getFillAmount() { return fillAmount; }

    public int getLevel() { return level; }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
        this.fillAmount = 5 + (this.level - 1) * 2;
        this.fillCost = Math.max(3, 5 - (this.level - 1));
    }

    public boolean fill(int availableCoins) {
        if (availableCoins < fillCost) return false;
        waterCharges += fillAmount;
        return true;
    }

    public boolean useWater() {
        if (waterCharges <= 0) return false;
        waterCharges--;
        return true;
    }
}