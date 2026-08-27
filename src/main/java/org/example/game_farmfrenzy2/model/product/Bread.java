package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Bread extends Product {
    public Bread(Position position) {
        super(position, 2);
    }

    @Override
    public int getSellPrice() {
        return 25;
    }

    @Override
    public ProductType getType() {
        return ProductType.BREAD;
    }
}