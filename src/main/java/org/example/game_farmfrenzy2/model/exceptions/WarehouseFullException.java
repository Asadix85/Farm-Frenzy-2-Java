package org.example.game_farmfrenzy2.model.exceptions;

public class WarehouseFullException extends GameException {
    public WarehouseFullException() {
        super("Warehouse is full!");
    }
}