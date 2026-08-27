package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Cloth;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.Thread;

import java.util.ArrayList;
import java.util.List;

public class Weaving extends Machine {
    private static final int PROCESSING_TIME = 10;
    private static final int CAPACITY = 2;

    public Weaving(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof org.example.game_farmfrenzy2.model.product.Thread;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();
        int threadCount = 0;
        for (Product input : inputs) {
            if (input instanceof Thread) threadCount++;
        }
        int clothCount = threadCount / 2;
        for (int i = 0; i < clothCount; i++) {
            outputs.add(new Cloth(new Position(
                    getPosition().getRow(),
                    getPosition().getCol()
            )));
        }
        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.THREAD;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.CLOTH;
    }

    @Override
    public int getRequiredInputCount() {
        return 2;
    }
}