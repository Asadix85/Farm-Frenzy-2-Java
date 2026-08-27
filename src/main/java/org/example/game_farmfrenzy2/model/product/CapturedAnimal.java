package org.example.game_farmfrenzy2.model.product;

import org.example.game_farmfrenzy2.model.entities.Position;

public class CapturedAnimal extends Product {
    private final String animalName;
    private final int sellPrice;

    public CapturedAnimal(Position position, String animalName, int sellPrice) {
        super(position, 2); // حجم ۲
        this.animalName = animalName;
        this.sellPrice = sellPrice;
    }

    @Override
    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public ProductType getType() {
        return ProductType.CAPTURED_ANIMAL;
    }

    public String getAnimalName() {
        return animalName;
    }
}