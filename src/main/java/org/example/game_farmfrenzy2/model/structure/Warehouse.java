package org.example.game_farmfrenzy2.model.structure;

import org.example.game_farmfrenzy2.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private List<Product> products;
    private int capacity;

    public Warehouse(int capacity) {
        this.products = new ArrayList<>();
        this.capacity = capacity;
    }

    public boolean addProduct(Product product) {
        if (products.size() < capacity) {
            products.add(product);
            product.setOnGround(false);
            product.setInWarehouse(true);
            return true;
        }
        return false;
    }

    public Product removeProduct(Product product) {
        if (products.remove(product)) {
            product.setInWarehouse(false);
            return product;
        }
        return null;
    }

    public List<Product> getProducts() { return products; }
    public int getCurrentVolume() { return products.size(); }
    public int getCapacity() { return capacity; }
    public boolean isFull() { return products.size() >= capacity; }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}