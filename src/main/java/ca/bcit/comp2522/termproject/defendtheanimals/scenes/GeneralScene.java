package ca.bcit.comp2522.termproject.defendtheanimals.scenes;

import ca.bcit.comp2522.termproject.defendtheanimals.Level;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the concept of a scene.
 * @author Al & Bosco
 * @version 2022
 */
public abstract class GeneralScene extends Scene {
    /**
     * The width of the window.
     */
    public static final int GAME_WIDTH = 1280;
    /**
     * The height of the window.
     */
    public static final int GAME_HEIGHT = 640;

    /**
     * The general background music path.
     */
    public static final String GENERAL_BGM = "images/soundEffects/general_bgm.mp3";

    /**
     * The javaFX Pane object for this GeneralScene.
     */
    protected Pane root;
    /**
     * The javaFX GraphicsContext object for this GeneralScene.
     */
    protected GraphicsContext gc;
    /**
     * The set of activeKeys for this GeneralScene.
     */
    protected Set<KeyCode> activeKeys;
    /**
     * The set of releasedKeys for this GeneralScene.
     */
    protected Set<KeyCode> releasedKeys;
    /**
     * The MediaPlayer object used to play music for this GeneralScene.
     */
    protected MediaPlayer mediaPlayer;


    /**
     * Constructs a general scene.
     */
    public GeneralScene() {
        super(new AnchorPane(), GAME_WIDTH, GAME_HEIGHT);

        root = new AnchorPane();
        this.setRoot(root);

        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();


        // Initialize set of currently pressed and released keys
        activeKeys = new HashSet<>();
        releasedKeys = new HashSet<>();
        this.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
        });
        this.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());
            releasedKeys.add(e.getCode());
        });
    }

    /**
     * Draw the scene.
     */
    public abstract void draw();

    /**
     * Plays background music.
     */
    protected void backgroundMusic() {
        this.backgroundMusic(GENERAL_BGM);
    }

    /**
     * Plays background music.
     * @param musicFile The file directory of the sound effect to be played.
     */
    protected void backgroundMusic(final String musicFile) {
        Media h = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }

    /**
     * Plays a sound effect
     * @param soundFile
     */
    protected void playSoundEffect(final String soundFile) {
        Media h = new Media(Paths.get(soundFile).toUri().toString());
        MediaPlayer mp = new MediaPlayer(h);
        mp.setVolume(0.5);
        mp.play();
    }

    /**
     * Stop background music.
     */
    protected void stopMusic() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}
