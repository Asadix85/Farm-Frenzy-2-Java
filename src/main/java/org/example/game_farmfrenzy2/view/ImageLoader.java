package org.example.game_farmfrenzy2.view;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image get(String path) {
        return cache.computeIfAbsent(path, p -> {
            var url = ImageLoader.class.getResource(p);
            if (url == null) {
                System.err.println("Image not found: " + p);
                return null;
            }
            return new Image(url.toExternalForm());
        });
    }
}