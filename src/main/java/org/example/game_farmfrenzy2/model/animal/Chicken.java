package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.*;
import org.example.game_farmfrenzy2.model.product.Egg;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.structure.Grass;

public class Chicken extends DomesticAnimal {
    public Chicken(Position position) {
        super(position, 0.5, ProductType.EGG);
    }

    @Override
    public void eat(Grass grass) {
        if (grass != null && !grass.isEaten()) {
            grass.setEaten(true);
            this.hunger = Math.max(0, this.hunger - 40);
            this.productionTimer = 8;
        }
    }

    @Override
    public Product produce() {
        if (this.productionTimer <= 0 && this.hunger < 70) {
            this.productionTimer = 10;
            return new Egg(new Position(this.getPosition().getRow(), this.getPosition().getCol()));
        }
        return null;
    }
}