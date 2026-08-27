package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Bread;
import org.example.game_farmfrenzy2.model.product.EggPowder;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Bakery extends Machine {
    private static final int PROCESSING_TIME = 8;
    private static final int CAPACITY = 3;

    public Bakery(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof EggPowder;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();

        int powderCount = 0;
        for (Product input : inputs) {
            if (input instanceof EggPowder) {
                powderCount++;
            }
        }

        int breadCount = powderCount / 2;
        for (int i = 0; i < breadCount; i++) {
            Position pos = new Position(
                    this.getPosition().getRow(),
                    this.getPosition().getCol()
            );
            outputs.add(new Bread(pos));
        }

        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.EGG_POWDER;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.BREAD;
    }

    @Override
    public int getRequiredInputCount() {
        return 2;
    }
}