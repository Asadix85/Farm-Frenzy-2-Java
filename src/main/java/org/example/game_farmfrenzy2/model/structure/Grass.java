package org.example.game_farmfrenzy2.model.structure;

import org.example.game_farmfrenzy2.model.entities.Position;

public class Grass {
    private Position position;
    private boolean eaten;
    private int growthTime;

    public Grass(Position position) {
        this.position = position;
        this.eaten = false;
        this.growthTime = 0;
    }

    public Position getPosition() { return position; }
    public boolean isEaten() { return eaten; }
    public void setEaten(boolean eaten) { this.eaten = eaten; }
}