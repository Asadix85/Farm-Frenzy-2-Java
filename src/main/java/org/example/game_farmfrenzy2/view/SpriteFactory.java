package org.example.game_farmfrenzy2.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.game_farmfrenzy2.model.animal.*;
import org.example.game_farmfrenzy2.model.machine.*;
import org.example.game_farmfrenzy2.model.product.*;
import org.example.game_farmfrenzy2.model.product.Thread;
import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.structure.WaterWell;

import java.util.Map;

public class SpriteFactory {

    public static ImageView create(Object obj, String key, Map<String, SpriteAnimator> animators) {
        if (obj instanceof WaterWell) return staticImage("/images/game/well.png", 56);
        if (obj instanceof Grass) return staticImage("/images/game/grass.png", 56);
        if (obj instanceof Egg) return staticImage("/images/game/egg.png", 36);
        if (obj instanceof EggPowder) return staticImage("/images/game/egg_powder.png", 40);
        if (obj instanceof Milk) return staticImage("/images/game/milk.png", 40);
        if (obj instanceof Feather) return staticImage("/images/game/feather.png", 40);
        if (obj instanceof Bread) return staticImage("/images/game/bread.png", 40);
        if (obj instanceof Butter) return staticImage("/images/game/butter.png", 40);
        if (obj instanceof Thread) return staticImage("/images/game/thread.png", 36);
        if (obj instanceof Color) return staticImage("/images/game/color.png", 36);
        if (obj instanceof Cloth) return staticImage("/images/game/cloth.png", 40);
        if (obj instanceof Clothes) return staticImage("/images/game/clothes.png", 40);
        if (obj instanceof CapturedAnimal) return staticImage("/images/game/wild_1.png", 48);

        if (obj instanceof Machine) {
            Machine m = (Machine) obj;
            String base = machineBaseName(m);
            String path = m.isRunning()
                    ? "/images/game/" + base + "_running.png"
                    : "/images/game/" + base + "_idle.png";
            ImageView iv = staticImage(path, 60);
            if (iv == null) {
                path = m.isRunning() ? "/images/game/machine_running.png" : "/images/game/machine_idle.png";
                iv = staticImage(path, 60);
            }
            return iv;
        }

        if (obj instanceof Chicken) return animated(key, 60, animators, "/images/game/chicken_1.png", "/images/game/chicken_2.png", "/images/game/chicken_3.png");
        if (obj instanceof Cow) return animated(key, 64, animators, "/images/game/cow_1.png", "/images/game/cow_2.png", "/images/game/cow_3.png");
        if (obj instanceof Ostrich) return animated(key, 64, animators, "/images/game/ostrich_1.png", "/images/game/ostrich_2.png", "/images/game/ostrich_3.png");
        if (obj instanceof Cat) return animated(key, 52, animators, "/images/game/cat_1.png", "/images/game/cat_2.png", "/images/game/cat_3.png");
        if (obj instanceof Dog) return animated(key, 56, animators, "/images/game/dog_1.png", "/images/game/dog_2.png", "/images/game/dog_3.png");
        if (obj instanceof WildAnimal) return animated(key, 56, animators, "/images/game/wild_1.png", "/images/game/wild_2.png", "/images/game/wild_3.png");

        return null;
    }

    private static String machineBaseName(Machine m) {
        if (m instanceof EggPowderMachine) return "machine";
        if (m instanceof Bakery) return "bakery";
        if (m instanceof ButterMachine) return "butter_machine";
        if (m instanceof YarnMachine) return "yarn_machine";
        if (m instanceof Sewing) return "sewing";
        if (m instanceof Weaving) return "weaving";
        return "machine";
    }

    private static ImageView staticImage(String path, double size) {
        Image img = ImageLoader.get(path);
        if (img == null) return null;
        ImageView iv = new ImageView(img);
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iv.setPreserveRatio(true);
        return iv;
    }

    private static ImageView animated(String key, double size, Map<String, SpriteAnimator> animators, String... frames) {
        ImageView iv = new ImageView();
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iv.setPreserveRatio(true);
        SpriteAnimator anim = new SpriteAnimator(iv, frames);
        anim.play(180);
        animators.put(key, anim);
        return iv;
    }
}