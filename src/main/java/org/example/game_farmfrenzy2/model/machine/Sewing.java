package org.example.game_farmfrenzy2.model.machine;

import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.ProductType;
import org.example.game_farmfrenzy2.model.product.Cloth;
import org.example.game_farmfrenzy2.model.product.Clothes;
import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Sewing extends Machine {
    private static final int PROCESSING_TIME = 12;
    private static final int CAPACITY = 2;

    public Sewing(Position position) {
        super(position, PROCESSING_TIME, CAPACITY);
    }

    @Override
    public boolean canProcess(Product input) {
        return input instanceof Cloth;
    }

    @Override
    public List<Product> process(List<Product> inputs) {
        List<Product> outputs = new ArrayList<>();

        int clothCount = 0;
        for (Product input : inputs) {
            if (input instanceof Cloth) {
                clothCount++;
            }
        }

        int clothesCount = clothCount / 2;
        for (int i = 0; i < clothesCount; i++) {
            Position pos = new Position(
                    this.getPosition().getRow(),
                    this.getPosition().getCol()
            );
            outputs.add(new Clothes(pos));
        }

        return outputs;
    }

    @Override
    public ProductType getInputType() {
        return ProductType.CLOTH;
    }

    @Override
    public ProductType getOutputType() {
        return ProductType.CLOTHES;
    }

    @Override
    public int getRequiredInputCount() {
        return 2;
    }
}