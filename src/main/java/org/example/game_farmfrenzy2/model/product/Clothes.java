package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Clothes extends Product {
    public Clothes(Position position) {
        super(position, 3);
    }

    @Override
    public int getSellPrice() {
        return 60;
    }

    @Override
    public ProductType getType() {
        return ProductType.CLOTHES;
    }
}