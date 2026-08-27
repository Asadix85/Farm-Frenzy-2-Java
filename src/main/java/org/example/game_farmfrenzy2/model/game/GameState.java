package org.example.game_farmfrenzy2.model.game;

import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.entities.*;
import org.example.game_farmfrenzy2.model.machine.*;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.structure.*;

import java.util.*;

public class GameState {
    private int coins;
    private int timeLeft;
    private int levelNumber;
    private List<Animal> animals;
    private List<WildAnimal> wildAnimals;
    private List<HelperAnimal> helperAnimals;
    private List<Machine> machines;
    private Vehicle vehicle;
    private Airplane airplane;
    private List<Vehicle> vehicles;
    private List<Airplane> airplanes;
    private List<Product> productsOnGround;
    private List<Grass> grasses;
    private WaterWell waterWell;
    private Warehouse warehouse;
    private GridManager grid;
    private int targetAnimalCount;
    private int targetProductCount;
    private boolean win;
    private boolean lose;
    private boolean gameOver;
    private LevelConfig config;

    public GameState(int level) {
        this.config = LevelConfig.get(level);
        this.coins = config.startCoins;
        this.levelNumber = level;
        this.timeLeft = config.timeLimit;
        this.animals = new ArrayList<>();
        this.wildAnimals = new ArrayList<>();
        this.helperAnimals = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.productsOnGround = new ArrayList<>();
        this.grasses = new ArrayList<>();
        this.vehicle = new Vehicle(10, 15);
        this.airplane = new Airplane(8, 20);
        this.vehicles = new ArrayList<>();
        this.airplanes = new ArrayList<>();
        this.grid = new GridManager();
        this.warehouse = new Warehouse(20);
        this.win = false;
        this.lose = false;
        this.gameOver = false;
        this.targetAnimalCount = config.targetChickens;
        this.targetProductCount = config.targetEggPowder;

        LevelFactory.setup(this, config);
    }

    public synchronized int getCoins() { return coins; }
    public synchronized void setCoins(int coins) { this.coins = Math.max(0, coins); }

    public synchronized int getTimeLeft() { return timeLeft; }
    public synchronized void setTimeLeft(int timeLeft) { this.timeLeft = Math.max(0, timeLeft); }

    public synchronized int getLevelNumber() { return levelNumber; }

    public synchronized List<Animal> getAnimals() { return animals; }
    public synchronized List<WildAnimal> getWildAnimals() { return wildAnimals; }
    public synchronized List<HelperAnimal> getHelperAnimals() { return helperAnimals; }
    public synchronized List<Machine> getMachines() { return machines; }
    public synchronized Vehicle getVehicle() { return vehicle; }
    public synchronized Airplane getAirplane() { return airplane; }
    public synchronized List<Vehicle> getVehicles() { return vehicles; }
    public synchronized List<Airplane> getAirplanes() { return airplanes; }
    public synchronized List<Product> getProductsOnGround() { return productsOnGround; }
    public synchronized List<Grass> getGrasses() { return grasses; }
    public synchronized WaterWell getWaterWell() { return waterWell; }
    public synchronized Warehouse getWarehouse() { return warehouse; }
    public synchronized GridManager getGrid() { return grid; }

    public void setWaterWell(WaterWell waterWell) {
        this.waterWell = waterWell;
    }

    public synchronized int getTargetAnimalCount() { return targetAnimalCount; }
    public synchronized int getTargetProductCount() { return targetProductCount; }

    public synchronized boolean isWin() { return win; }
    public synchronized void setWin(boolean win) { this.win = win; this.gameOver = true; }

    public synchronized boolean isLose() { return lose; }
    public synchronized void setLose(boolean lose) { this.lose = lose; this.gameOver = true; }

    public synchronized boolean isGameOver() { return gameOver; }

    public synchronized void addMachine(Machine machine) {
        machines.add(machine);
        grid.setCell(machine.getPosition().getRow(), machine.getPosition().getCol(), machine);
    }

    public synchronized void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public synchronized void addAirplane(Airplane a) {
        airplanes.add(a);
    }

    public synchronized void addWildAnimal(WildAnimal animal) {
        wildAnimals.add(animal);
        grid.setCell(animal.getPosition().getRow(), animal.getPosition().getCol(), animal);
    }

    public synchronized void addHelperAnimal(HelperAnimal animal) {
        helperAnimals.add(animal);
        grid.setCell(animal.getPosition().getRow(), animal.getPosition().getCol(), animal);
    }

    public synchronized void removeWildAnimal(WildAnimal animal) {
        wildAnimals.remove(animal);
        grid.clearCell(animal.getPosition().getRow(), animal.getPosition().getCol());
    }

    public LevelConfig getConfig() {
        return config;
    }
}