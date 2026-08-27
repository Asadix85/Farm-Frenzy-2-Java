package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.exceptions.InvalidActionException;
import org.example.game_farmfrenzy2.model.exceptions.NotEnoughCoinsException;
import org.example.game_farmfrenzy2.model.game.GameState;

public class PurchaseService {
    private final GameState gameState;

    public PurchaseService(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized void buyChicken() {
        buyDomestic(20, pos -> new Chicken(pos));
    }

    public synchronized void buyCow() {
        buyDomestic(50, pos -> new Cow(pos));
    }

    public synchronized void buyOstrich() {
        buyDomestic(40, pos -> new Ostrich(pos));
    }

    public synchronized void buyDog() {
        buyHelper(100, pos -> new Dog(pos));
    }

    public synchronized void buyCat() {
        buyHelper(80, pos -> new Cat(pos));
    }

    private void buyDomestic(int price, java.util.function.Function<Position, Animal> factory) {
        if (gameState.getCoins() < price)
            throw new NotEnoughCoinsException(price, gameState.getCoins());
        Position pos = gameState.getGrid().findEmptyBorderCell();
        if (pos == null)
            throw new InvalidActionException("No empty cell!");
        Animal animal = factory.apply(pos);
        gameState.getAnimals().add(animal);
        gameState.getGrid().setCell(pos.getRow(), pos.getCol(), animal);
        gameState.setCoins(gameState.getCoins() - price);
    }

    private void buyHelper(int price, java.util.function.Function<Position, HelperAnimal> factory) {
        if (gameState.getCoins() < price)
            throw new NotEnoughCoinsException(price, gameState.getCoins());
        Position pos = gameState.getGrid().findEmptyBorderCell();
        if (pos == null)
            throw new InvalidActionException("No empty cell for helper animal!");
        HelperAnimal animal = factory.apply(pos);
        gameState.addHelperAnimal(animal);
        gameState.setCoins(gameState.getCoins() - price);
    }
}