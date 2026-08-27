package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Butter;
import org.example.game_farmfrenzy2.model.product.Milk;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ButterMachine extends Machine {
    private static final int PROCESSING_TIME = 6;
    private static final int CAPACITY = 2;

    public ButterMachine(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof Milk;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();

        int milkCount = 0;
        for (Product input : inputs) {
            if (input instanceof Milk) {
                milkCount++;
            }
        }

        int butterCount = milkCount / 3;
        for (int i = 0; i < butterCount; i++) {
            Position pos = new Position(
                    this.getPosition().getRow(),
                    this.getPosition().getCol()
            );
            outputs.add(new Butter(pos));
        }

        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.MILK;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.BUTTER;
    }

    @Override
    public int getRequiredInputCount() {
        return 3;
    }
}