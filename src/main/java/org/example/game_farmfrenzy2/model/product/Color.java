package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Color extends Product {
    public Color(Position position) {
        super(position, 1);
    }

    @Override
    public int getSellPrice() {
        return 20;
    }

    @Override
    public ProductType getType() {
        return ProductType.COLOR;
    }
}