package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Feather;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.Thread;

import java.util.ArrayList;
import java.util.List;

public class YarnMachine extends Machine {
    private static final int PROCESSING_TIME = 5;
    private static final int CAPACITY = 4;

    public YarnMachine(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof Feather;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();

        int featherCount = 0;
        for (Product input : inputs) {
            if (input instanceof Feather) {
                featherCount++;
            }
        }

        int threadCount = featherCount / 2;
        for (int i = 0; i < threadCount; i++) {
            Position pos = new Position(
                    this.getPosition().getRow(),
                    this.getPosition().getCol()
            );
            outputs.add(new Thread(pos));
        }

        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.FEATHER;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.THREAD;
    }

    @Override
    public int getRequiredInputCount() {
        return 2;
    }
}