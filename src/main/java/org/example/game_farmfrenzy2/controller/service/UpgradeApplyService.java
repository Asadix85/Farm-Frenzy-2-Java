package org.example.game_farmfrenzy2.controller.service;

import org.example.game_farmfrenzy2.Main;
import org.example.game_farmfrenzy2.model.database.UpgradeRepository;
import org.example.game_farmfrenzy2.model.game.GameState;
import org.example.game_farmfrenzy2.model.machine.Machine;

public class UpgradeApplyService {
    public void apply(GameState gameState) {
        int userId = Main.getInstance().getCurrentUserId();
        if (userId <= 0) return;

        UpgradeRepository repo = new UpgradeRepository();

        int warehouseLv = repo.getUpgradeLevel(userId, "WAREHOUSE");
        gameState.getWarehouse().setCapacity(20 + (warehouseLv - 1) * 10);

        int wellLv = repo.getUpgradeLevel(userId, "WELL");
        if (gameState.getWaterWell() != null) {
            gameState.getWaterWell().setLevel(wellLv);
        }

        int vehicleLv = repo.getUpgradeLevel(userId, "VEHICLE");
        if (gameState.getVehicle() != null) {
            for (int i = 1; i < vehicleLv; i++) gameState.getVehicle().upgrade();
        }

        int airplaneLv = repo.getUpgradeLevel(userId, "AIRPLANE");
        if (gameState.getAirplane() != null) {
            for (int i = 1; i < airplaneLv; i++) gameState.getAirplane().upgrade();
        }

        for (Machine machine : gameState.getMachines()) {
            String key = machine.getClass().getSimpleName().toUpperCase();
            machine.setLevel(repo.getUpgradeLevel(userId, key));
        }
    }
}