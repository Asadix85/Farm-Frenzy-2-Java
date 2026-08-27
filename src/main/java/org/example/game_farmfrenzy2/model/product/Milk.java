package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Milk extends Product {
    public Milk(Position position) {
        super(position, 2);
    }

    @Override
    public int getSellPrice() {
        return 15;
    }

    @Override
    public ProductType getType() {
        return ProductType.MILK;
    }
}