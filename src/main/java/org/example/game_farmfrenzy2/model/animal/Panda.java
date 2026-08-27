package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;

public class Panda extends WildAnimal {
    private int attackCooldown;
    private static final int ATTACK_COOLDOWN_MAX = 5;

    public Panda(Position position) {
        super(position, 0.3);
        this.attackCooldown = 0;
    }

    @Override
    public void attack(Animal target) {
        if (attackCooldown <= 0 && target.isAlive()) {
            target.die();
            attackCooldown = ATTACK_COOLDOWN_MAX;
        }
    }

    @Override
    public int getSellPrice() {
        return 50;
    }

    public void updateCooldown() {
        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }

    public boolean canAttack() {
        return attackCooldown <= 0 && isAlive() && !isCaptured();
    }

    public Animal findNearestTarget(GameState gameState) {
        Animal nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Animal animal : gameState.getAnimals()) {
            if (animal.isAlive()) {
                double distance = calculateDistance(animal.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = animal;
                }
            }
        }
        return nearest;
    }

    private double calculateDistance(Position target) {
        int dx = this.getPosition().getRow() - target.getRow();
        int dy = this.getPosition().getCol() - target.getCol();
        return Math.sqrt(dx*dx + dy*dy);
    }
}