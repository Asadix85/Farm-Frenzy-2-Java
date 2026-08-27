package org.example.game_farmfrenzy2.model.game;

public class LevelConfig {
    public final int levelNumber;
    public final int startCoins;
    public final int timeLimit;
    public final int goldTime;
    public final int silverTime;

    public final int startChickens;
    public final int startCows;
    public final int startOstriches;

    public final boolean hasEggPowderMachine;
    public final boolean hasBakery;
    public final boolean hasYarnMachine;
    public final boolean hasWeaving;
    public final boolean hasButterMachine;
    public final boolean hasSewing;

    public final int targetChickens;
    public final int targetCows;
    public final int targetOstriches;
    public final int targetDogs;
    public final int targetCats;
    public final int targetEggPowder;
    public final int targetBread;
    public final int targetThread;
    public final int targetCloth;
    public final int targetClothes;
    public final int targetButter;
    public final int targetCoins;

    public final String title;
    public final String goalText;
    public final String hintText;

    public LevelConfig(
            int levelNumber,
            int startCoins,
            int goldTime,
            int silverTime,
            int startChickens,
            int startCows,
            int startOstriches,
            boolean hasEggPowderMachine,
            boolean hasBakery,
            boolean hasYarnMachine,
            boolean hasWeaving,
            boolean hasButterMachine,
            boolean hasSewing,
            int targetChickens,
            int targetCows,
            int targetOstriches,
            int targetDogs,
            int targetCats,
            int targetEggPowder,
            int targetBread,
            int targetThread,
            int targetCloth,
            int targetClothes,
            int targetButter,
            int targetCoins,
            String title,
            String goalText,
            String hintText
    ) {
        this.levelNumber = levelNumber;
        this.startCoins = startCoins;
        this.goldTime = goldTime;
        this.silverTime = silverTime;
        this.timeLimit = silverTime;
        this.startChickens = startChickens;
        this.startCows = startCows;
        this.startOstriches = startOstriches;
        this.hasEggPowderMachine = hasEggPowderMachine;
        this.hasBakery = hasBakery;
        this.hasYarnMachine = hasYarnMachine;
        this.hasWeaving = hasWeaving;
        this.hasButterMachine = hasButterMachine;
        this.hasSewing = hasSewing;
        this.targetChickens = targetChickens;
        this.targetCows = targetCows;
        this.targetOstriches = targetOstriches;
        this.targetDogs = targetDogs;
        this.targetCats = targetCats;
        this.targetEggPowder = targetEggPowder;
        this.targetBread = targetBread;
        this.targetThread = targetThread;
        this.targetCloth = targetCloth;
        this.targetClothes = targetClothes;
        this.targetButter = targetButter;
        this.targetCoins = targetCoins;
        this.title = title;
        this.goalText = goalText;
        this.hintText = hintText;
    }

    public static LevelConfig get(int level) {
        switch (level) {
            case 1:
                return new LevelConfig(
                        1, 25, 90, 120,
                        1, 0, 0,
                        false, false, false, false, false, false,
                        2, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        "Level 1 - Basic Cycle",
                        "Win: Have 2 living chickens at the same time.",
                        "Click well to buy water, plant grass, keep chickens alive and buy a second chicken.\nGold: 1:30 | Silver: 2:00"
                );
            case 2:
                return new LevelConfig(
                        2, 40, 150, 210,
                        2, 0, 0,
                        true, false, false, false, false, false,
                        3, 0, 0, 0, 0,
                        5, 0, 0, 0, 0, 0, 0,
                        "Level 2 - Processing",
                        "Win: 3 living chickens AND 5 Egg Powder in warehouse.",
                        "Collect eggs, process them in Egg Powder Machine.\nGold: 2:30 | Silver: 3:30"
                );
            case 3:
                return new LevelConfig(
                        3, 60, 210, 270,
                        3, 0, 0,
                        true, true, false, false, false, false,
                        0, 0, 0, 1, 1,
                        0, 3, 0, 0, 0, 0, 0,
                        "Level 3 - Threats & Helpers",
                        "Win: Own 1 Dog, 1 Cat, and produce 3 Bread.",
                        "Pandas may attack. Buy Dog/Cat. Use Bakery (Egg Powder -> Bread).\nGold: 3:30 | Silver: 4:30"
                );
            case 4:
                return new LevelConfig(
                        4, 50, 270, 360,
                        2, 0, 0,
                        true, true, true, true, false, false,
                        0, 0, 1, 0, 0,
                        0, 0, 3, 2, 0, 0, 0,
                        "Level 4 - Textile Chain",
                        "Win: 1 living Ostrich, 3 Thread, 2 Cloth.",
                        "Buy Ostrich. Feather -> Thread -> Cloth.\nGold: 4:30 | Silver: 6:00"
                );
            case 5:
                return new LevelConfig(
                        5, 40, 360, 480,
                        0, 0, 1,
                        false, false, true, true, true, true,
                        0, 1, 0, 0, 0,
                        0, 0, 0, 0, 3, 2, 0,
                        "Level 5 - Cattle & Clothing",
                        "Win: 1 living Cow, 3 Clothes, 2 Butter.",
                        "Buy Cow. Milk -> Butter. Cloth -> Clothes (Sewing).\nGold: 6:00 | Silver: 8:00"
                );
            case 6:
                return new LevelConfig(
                        6, 0, 480, 630,
                        1, 1, 1,
                        true, true, true, true, true, true,
                        5, 0, 0, 0, 0,
                        0, 10, 0, 0, 5, 5, 10000,
                        "Level 6 - Final Challenge",
                        "Win: 5 chickens, 10 Bread, 5 Clothes, 5 Butter, and 10,000 coins.",
                        "Manage all chains, pandas, warehouse, dog/cat, vehicle and airplane.\nGold: 8:00 | Silver: 10:30"
                );
            default:
                return get(1);
        }
    }
}