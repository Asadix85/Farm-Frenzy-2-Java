package org.example.game_farmfrenzy2.model.game;

import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.machine.*;
import org.example.game_farmfrenzy2.model.structure.WaterWell;
import org.example.game_farmfrenzy2.model.entities.Position;

public class LevelFactory {

    public static void setup(GameState state, LevelConfig config) {
        Position wellPos = new Position(5, 2);
        WaterWell well = new WaterWell(wellPos);
        state.setWaterWell(well);
        state.getGrid().setCell(5, 2, well);

        spawnAnimals(state, config);
        spawnMachines(state, config);
    }

    private static void spawnAnimals(GameState state, LevelConfig config) {
        int placed = 0;
        for (int i = 0; i < config.startChickens; i++) {
            Position p = borderSlot(state, placed++);
            if (p == null) break;
            Chicken c = new Chicken(p);
            state.getAnimals().add(c);
            state.getGrid().setCell(p.getRow(), p.getCol(), c);
        }
        for (int i = 0; i < config.startCows; i++) {
            Position p = borderSlot(state, placed++);
            if (p == null) break;
            Cow c = new Cow(p);
            state.getAnimals().add(c);
            state.getGrid().setCell(p.getRow(), p.getCol(), c);
        }
        for (int i = 0; i < config.startOstriches; i++) {
            Position p = borderSlot(state, placed++);
            if (p == null) break;
            Ostrich o = new Ostrich(p);
            state.getAnimals().add(o);
            state.getGrid().setCell(p.getRow(), p.getCol(), o);
        }
    }

    private static void spawnMachines(GameState state, LevelConfig config) {
        placeMachine(state, config.hasEggPowderMachine, new Position(5, 0), new EggPowderMachine(new Position(5, 0)));
        placeMachine(state, config.hasBakery, new Position(5, 1), new Bakery(new Position(5, 1)));
        placeMachine(state, config.hasYarnMachine, new Position(5, 3), new YarnMachine(new Position(5, 3)));
        placeMachine(state, config.hasWeaving, new Position(5, 4), new Weaving(new Position(5, 4)));
        placeMachine(state, config.hasButterMachine, new Position(4, 0), new ButterMachine(new Position(4, 0)));
        placeMachine(state, config.hasSewing, new Position(4, 4), new Sewing(new Position(4, 4)));
    }

    private static void placeMachine(GameState state, boolean enabled, Position p, Machine m) {
        if (!enabled) return;
        if (state.getGrid().isOccupied(p.getRow(), p.getCol())) return;
        state.getMachines().add(m);
        state.getGrid().setCell(p.getRow(), p.getCol(), m);
    }

    private static Position borderSlot(GameState state, int index) {
        Position[] slots = {
                new Position(0, 0), new Position(0, 1), new Position(0, 2),
                new Position(0, 3), new Position(0, 4), new Position(1, 0),
                new Position(2, 0), new Position(3, 0)
        };
        if (index < 0 || index >= slots.length) return null;
        Position p = slots[index];
        if (state.getGrid().isOccupied(p.getRow(), p.getCol())) return null;
        return p;
    }
}