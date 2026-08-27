package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Butter extends Product {
    public Butter(Position position) {
        super(position, 2);
    }

    @Override
    public int getSellPrice() {
        return 30;
    }

    @Override
    public ProductType getType() {
        return ProductType.BUTTER;
    }
}