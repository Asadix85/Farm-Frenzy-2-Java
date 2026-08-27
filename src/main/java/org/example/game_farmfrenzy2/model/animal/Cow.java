package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.entities.*;
import org.example.game_farmfrenzy2.model.product.Milk;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.structure.Grass;

public class Cow extends DomesticAnimal {
    public Cow(Position position) {
        super(position, 0.4, ProductType.MILK);
        this.productionTimer = 12;
    }

    @Override
    public void eat(Grass grass) {
        if (grass != null && !grass.isEaten()) {
            grass.setEaten(true);
            this.hunger = Math.max(0, this.hunger - 25);
            this.productionTimer = 12;
        }
    }

    @Override
    public Product produce() {
        if (this.productionTimer <= 0 && this.hunger < 65) {
            this.productionTimer = 12;
            return new Milk(new Position(this.getPosition().getRow(), this.getPosition().getCol()));
        }
        return null;
    }
}