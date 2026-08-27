package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.product.Product;
import org.example.game_farmfrenzy2.model.game.GameState;

public class Cat extends HelperAnimal {
    private Product targetProduct;
    private boolean isCollecting;

    public Cat(Position position) {
        super(position, 0.9);
        this.isCollecting = false;
        this.targetProduct = null;
    }

    @Override
    public void performDuty(GameState gameState) {
        Product nearestProduct = findNearestProduct(gameState);

        if (nearestProduct != null) {
            this.isCollecting = true;
            this.targetProduct = nearestProduct;

            moveOnGrid(gameState, nearestProduct.getPosition());

            if (isNear(nearestProduct.getPosition())) {
                collectProduct(gameState, nearestProduct);
                this.isCollecting = false;
                this.targetProduct = null;
            }
        } else {
            this.isCollecting = false;
            this.targetProduct = null;
        }
    }

    private Product findNearestProduct(GameState gameState) {
        Product nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Product product : gameState.getProductsOnGround()) {
            if (product.isOnGround()) {
                double distance = calculateDistance(product.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = product;
                }
            }
        }
        return nearest;
    }

    private void collectProduct(GameState gameState, Product product) {
        Object cell = gameState.getGrid().getCell(
                product.getPosition().getRow(),
                product.getPosition().getCol()
        );
        if (cell == product) {
            gameState.getGrid().clearCell(
                    product.getPosition().getRow(),
                    product.getPosition().getCol()
            );
        }
    }

    private boolean isNear(Position target) {
        int dx = Math.abs(this.getPosition().getRow() - target.getRow());
        int dy = Math.abs(this.getPosition().getCol() - target.getCol());
        return dx <= 1 && dy <= 1;
    }

    private double calculateDistance(Position target) {
        int dx = this.getPosition().getRow() - target.getRow();
        int dy = this.getPosition().getCol() - target.getCol();
        return Math.sqrt(dx*dx + dy*dy);
    }

    public boolean isCollecting() {
        return isCollecting;
    }

    private void moveOnGrid(GameState gameState, Position target) {
        Position from = getPosition();
        int r = from.getRow();
        int c = from.getCol();

        int nr = r + Integer.compare(target.getRow(), r);
        int nc = c + Integer.compare(target.getCol(), c);

        if (nr < 0 || nr >= 6 || nc < 0 || nc >= 5) return;

        Object cell = gameState.getGrid().getCell(nr, nc);
        if (cell != null && !(cell instanceof Grass) && cell != this) return;

        if (gameState.getGrid().getCell(r, c) == this) {
            gameState.getGrid().clearCell(r, c);
        }
        if (cell instanceof Grass) {
            gameState.getGrasses().remove(cell);
            gameState.getGrid().clearCell(nr, nc);
        }

        setPosition(new Position(nr, nc));
        gameState.getGrid().setCell(nr, nc, this);
    }
}