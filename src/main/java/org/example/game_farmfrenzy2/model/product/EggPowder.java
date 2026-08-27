package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class EggPowder extends Product {
    public EggPowder(Position position) {
        super(position, 1);
    }
    @Override
    public int getSellPrice() { return 30; }

    @Override
    public ProductType getType() {
        return ProductType.EGG_POWDER;
    }
}
