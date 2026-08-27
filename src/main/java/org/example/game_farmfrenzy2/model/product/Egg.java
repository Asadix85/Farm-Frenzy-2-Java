package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Egg extends Product {
    public Egg(Position position) {
        super(position, 1);
    }
    @Override
    public int getSellPrice() { return 10; }

    @Override
    public ProductType getType() {
        return ProductType.EGG;
    }
}
