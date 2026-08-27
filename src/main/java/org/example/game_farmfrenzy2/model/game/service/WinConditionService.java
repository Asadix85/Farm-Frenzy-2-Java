package org.example.game_farmfrenzy2.model.game.service;

import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.game.LevelConfig;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.product.Thread;

public class WinConditionService {
    private final GameState gameState;

    public WinConditionService(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isWon() {
        LevelConfig cfg = gameState.getConfig();
        return countAlive(Chicken.class) >= cfg.targetChickens
                && countAlive(Cow.class) >= cfg.targetCows
                && countAlive(Ostrich.class) >= cfg.targetOstriches
                && countHelpers(Dog.class) >= cfg.targetDogs
                && countHelpers(Cat.class) >= cfg.targetCats
                && countInWarehouse(EggPowder.class) >= cfg.targetEggPowder
                && countInWarehouse(Bread.class) >= cfg.targetBread
                && countInWarehouse(Thread.class) >= cfg.targetThread
                && countInWarehouse(Cloth.class) >= cfg.targetCloth
                && countInWarehouse(Clothes.class) >= cfg.targetClothes
                && countInWarehouse(Butter.class) >= cfg.targetButter
                && gameState.getCoins() >= cfg.targetCoins;
    }

    public boolean isLost() {
        if (gameState.getTimeLeft() <= 0) return true;
        int aliveDomestic = 0;
        for (Animal a : gameState.getAnimals()) {
            if (a.isAlive() && a instanceof DomesticAnimal) aliveDomestic++;
        }
        return aliveDomestic == 0;
    }

    public int calculateStars() {
        LevelConfig cfg = gameState.getConfig();
        int timeSpent = cfg.timeLimit - gameState.getTimeLeft();
        if (timeSpent <= cfg.goldTime) return 3;
        if (timeSpent <= cfg.silverTime) return 2;
        return 1;
    }

    private int countAlive(Class<?> type) {
        int n = 0;
        for (Animal a : gameState.getAnimals()) {
            if (a.isAlive() && type.isInstance(a)) n++;
        }
        return n;
    }

    private int countHelpers(Class<?> type) {
        int n = 0;
        for (HelperAnimal h : gameState.getHelperAnimals()) {
            if (type.isInstance(h)) n++;
        }
        return n;
    }

    private int countInWarehouse(Class<?> type) {
        int n = 0;
        for (Product p : gameState.getWarehouse().getProducts()) {
            if (type.isInstance(p)) n++;
        }
        return n;
    }
}