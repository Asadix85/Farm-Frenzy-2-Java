package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Feather extends Product {
    public Feather(Position position) {
        super(position, 1);
    }

    @Override
    public int getSellPrice() {
        return 12;
    }

    @Override
    public ProductType getType() {
        return ProductType.FEATHER;
    }
}