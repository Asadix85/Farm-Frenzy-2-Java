package org.example.game_farmfrenzy2.model.game;

import org.example.game_farmfrenzy2.model.game.service.AnimalBehaviorService;
import org.example.game_farmfrenzy2.model.game.service.LogisticsTickService;
import org.example.game_farmfrenzy2.model.game.service.MachineProcessingService;
import org.example.game_farmfrenzy2.model.game.service.WildAnimalService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLoop {
    private final ScheduledExecutorService scheduler;
    private final GameState gameState;
    private final AnimalBehaviorService animalService;
    private final MachineProcessingService machineService;
    private final WildAnimalService wildService;
    private final LogisticsTickService logisticsService;

    private volatile boolean running;
    private volatile boolean paused;
    private Runnable onTick;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.animalService = new AnimalBehaviorService(gameState);
        this.machineService = new MachineProcessingService(gameState);
        this.wildService = new WildAnimalService(gameState);
        this.logisticsService = new LogisticsTickService(gameState);
        this.running = false;
        this.paused = false;
    }

    public void start() {
        if (running) return;
        running = true;

        scheduler.scheduleAtFixedRate(() -> {
            if (!running || paused) return;

            synchronized (gameState) {
                if (gameState.isGameOver()) return;

                int time = gameState.getTimeLeft() - 1;
                gameState.setTimeLeft(time);
                if (time <= 0) {
                    gameState.setLose(true);
                    stop();
                    if (onTick != null) onTick.run();
                    return;
                }

                animalService.tick();
                machineService.tick();
                wildService.tick();
                logisticsService.tick();
            }

            if (onTick != null) {
                onTick.run();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void setOnTick(Runnable onTick) {
        this.onTick = onTick;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void stop() {
        if (!running) return;
        running = false;
        paused = false;
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}