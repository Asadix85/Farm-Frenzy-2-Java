package org.example.game_farmfrenzy2.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundManager {
    private static SoundManager instance;

    private MediaPlayer menuPlayer;
    private MediaPlayer gamePlayer;
    private double musicVolume = 0.8;
    private double soundVolume = 0.8;

    private SoundManager() {
        menuPlayer = createPlayer("/sounds/menu_music.mp3");
        gamePlayer = createPlayer("/sounds/game_music.mp3");
        applyMusicVolume();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private MediaPlayer createPlayer(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Sound not found: " + path);
            return null;
        }
        MediaPlayer player = new MediaPlayer(new Media(url.toExternalForm()));
        player.setCycleCount(MediaPlayer.INDEFINITE);
        return player;
    }

    public void playMenuMusic() {
        stopGameMusic();
        if (menuPlayer != null) {
            menuPlayer.setVolume(musicVolume);
            menuPlayer.play();
        }
    }

    public void playGameMusic() {
        stopMenuMusic();
        if (gamePlayer != null) {
            gamePlayer.setVolume(musicVolume);
            gamePlayer.play();
        }
    }

    public void stopMenuMusic() {
        if (menuPlayer != null) {
            menuPlayer.stop();
        }
    }

    public void stopGameMusic() {
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
    }

    public void stopAll() {
        stopMenuMusic();
        stopGameMusic();
    }

    public void setMusicVolume(double volume) {
        this.musicVolume = Math.max(0, Math.min(1, volume));
        applyMusicVolume();
    }

    public void setSoundVolume(double volume) {
        this.soundVolume = Math.max(0, Math.min(1, volume));
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public double getSoundVolume() {
        return soundVolume;
    }

    private void applyMusicVolume() {
        if (menuPlayer != null) menuPlayer.setVolume(musicVolume);
        if (gamePlayer != null) gamePlayer.setVolume(musicVolume);
    }

    public void playEffect(String path) {
        URL url = getClass().getResource(path);
        if (url == null) return;
        AudioClip clip = new AudioClip(url.toExternalForm());
        clip.setVolume(soundVolume);
        clip.play();
    }
}