package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Thread extends Product {
    public Thread(Position position) {
        super(position, 1);
    }

    @Override
    public int getSellPrice() {
        return 18;
    }

    @Override
    public ProductType getType() {
        return ProductType.THREAD;
    }
}