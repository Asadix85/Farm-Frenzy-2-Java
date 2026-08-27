package org.example.game_farmfrenzy2.model.animal;

import org.example.game_farmfrenzy2.model.structure.Grass;
import org.example.game_farmfrenzy2.model.entities.Position;
import org.example.game_farmfrenzy2.model.game.GameState;

public class Dog extends HelperAnimal {
    private Animal target;
    private boolean isGuarding;

    public Dog(Position position) {
        super(position, 0.8);
        this.isGuarding = false;
        this.target = null;
    }

    @Override
    public void performDuty(GameState gameState) {
        WildAnimal nearestWild = findNearestWildAnimal(gameState);

        if (nearestWild != null && !nearestWild.isCaptured()) {
            this.isGuarding = true;
            this.target = null;
            moveOnGrid(gameState, nearestWild.getPosition());

            if (isNear(nearestWild.getPosition())) {
                nearestWild.setAlive(false);
                gameState.removeWildAnimal(nearestWild);
                isGuarding = false;
            }
        } else {
            this.isGuarding = false;
        }
    }

    private WildAnimal findNearestWildAnimal(GameState gameState) {
        WildAnimal nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Object obj : gameState.getGrid().getAllObjects()) {
            if (obj instanceof WildAnimal) {
                WildAnimal wild = (WildAnimal) obj;
                if (wild.isAlive() && !wild.isCaptured()) {
                    double distance = calculateDistance(wild.getPosition());
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearest = wild;
                    }
                }
            }
        }
        return nearest;
    }

    private double calculateDistance(Position target) {
        int dx = this.getPosition().getRow() - target.getRow();
        int dy = this.getPosition().getCol() - target.getCol();
        return Math.sqrt(dx*dx + dy*dy);
    }

    private boolean isNear(Position target) {
        int dx = Math.abs(this.getPosition().getRow() - target.getRow());
        int dy = Math.abs(this.getPosition().getCol() - target.getCol());
        return dx <= 1 && dy <= 1;
    }

    public boolean isGuarding() {
        return isGuarding;
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