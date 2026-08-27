package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Egg;
import org.example.game_farmfrenzy2.model.product.EggPowder;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.*;

public class EggPowderMachine extends Machine {
    private static final int PROCESSING_TIME = 5;
    private static final int CAPACITY = 5;

    public EggPowderMachine(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof Egg;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();
        for (Product input : inputs) {
            if (input instanceof Egg) {
                outputs.add(new EggPowder(new Position(
                        getPosition().getRow(),
                        getPosition().getCol()
                )));
            }
        }
        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.EGG;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.EGG_POWDER;
    }

    @Override
    public int getRequiredInputCount() {
        return 1;
    }
}