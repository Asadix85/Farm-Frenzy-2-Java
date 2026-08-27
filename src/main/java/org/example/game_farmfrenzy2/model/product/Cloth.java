package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Cloth extends Product {
    public Cloth(Position position) {
        super(position, 3);
    }

    @Override
    public int getSellPrice() {
        return 40;
    }

    @Override
    public ProductType getType() {
        return ProductType.CLOTH;
    }
}