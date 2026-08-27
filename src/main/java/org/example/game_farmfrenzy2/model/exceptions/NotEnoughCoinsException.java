package org.example.game_farmfrenzy2.model.exceptions;

public class NotEnoughCoinsException extends GameException {
    public NotEnoughCoinsException(int required, int available) {
        super("Not enough coins! Need " + required + ", have " + available + ".");
    }
}