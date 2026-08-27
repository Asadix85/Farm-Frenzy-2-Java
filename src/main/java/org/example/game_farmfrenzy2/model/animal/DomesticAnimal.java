package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.product.ProductType;

public abstract class DomesticAnimal extends Animal {
    protected ProductType productType;
    protected int productionTimer;

    public DomesticAnimal(Position position, double speed, ProductType productType) {
        super(position, speed);
        this.productType = productType;
        this.productionTimer = 10;
    }

    public ProductType getProductType() { return productType; }
    public int getProductionTimer() { return productionTimer; }
    public void setProductionTimer(int timer) { this.productionTimer = timer; }

    @Override
    public abstract void eat(Grass grass);

    @Override
    public abstract Product produce();
}