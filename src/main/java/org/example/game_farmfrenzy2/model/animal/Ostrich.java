package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.*;
import org.example.game_farmfrenzy2.model.product.Feather;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.structure.Grass;

public class Ostrich extends DomesticAnimal {
    public Ostrich(Position position) {
        super(position, 0.6, ProductType.FEATHER);
        this.productionTimer = 15;
    }

    @Override
    public void eat(Grass grass) {
        if (grass != null && !grass.isEaten()) {
            grass.setEaten(true);
            this.hunger = Math.max(0, this.hunger - 20);
            this.productionTimer = 15;
        }
    }

    @Override
    public Product produce() {
        if (this.productionTimer <= 0 && this.hunger < 60) {
            this.productionTimer = 15;
            return new Feather(new Position(this.getPosition().getRow(), this.getPosition().getCol()));
        }
        return null;
    }
}