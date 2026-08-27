package org.example.game_farmfrenzy2.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SpriteAnimator {
    private final ImageView imageView;
    private final List<Image> frames = new ArrayList<>();
    private Timeline timeline;
    private int index = 0;

    public SpriteAnimator(ImageView imageView, String... framePaths) {
        this.imageView = imageView;
        for (String path : framePaths) {
            Image img = ImageLoader.get(path);
            if (img != null) {
                frames.add(img);
            }
        }
        if (!frames.isEmpty()) {
            imageView.setImage(frames.get(0));
        }
    }

    public void play(double frameDurationMs) {
        if (frames.size() <= 1) return;
        stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(frameDurationMs), e -> {
            index = (index + 1) % frames.size();
            imageView.setImage(frames.get(index));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}