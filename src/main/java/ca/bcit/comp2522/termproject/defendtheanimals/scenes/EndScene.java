package ca.bcit.comp2522.termproject.defendtheanimals.scenes;

import ca.bcit.comp2522.termproject.defendtheanimals.Game;
import ca.bcit.comp2522.termproject.defendtheanimals.GameController;
import ca.bcit.comp2522.termproject.defendtheanimals.Level;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.StationarySprite;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the end scene.
 * @author Al & Bosco
 * @version 2022
 */
public class EndScene extends GeneralScene {

    private GameController gameController;
    private boolean hasWon;
    private Level currentLevel;
    private StationarySprite background;

    /**
     * Constructs an end scene.
     * @param gameController The game controller.
     * @param hasWon Boolean value whether the player won the level.
     * @param level The level.
     */
    public EndScene(GameController gameController, boolean hasWon, Level level) {
        super();
        this.gameController = gameController; // tried to put it in GeneralScene but it would crash
        this.hasWon = hasWon;
        this.currentLevel = level;

        if (!hasWon) {
            background = new StationarySprite("images/gameOver", GAME_WIDTH, GAME_HEIGHT);
        } else {
            if (currentLevel.getLevel() == Game.MAX_LEVEL) {
                background = new StationarySprite("images/win", GAME_WIDTH, GAME_HEIGHT);
            } else {
                background = new StationarySprite("images/level2", GAME_WIDTH, GAME_HEIGHT);
            }
        }


        background.setPosition(GAME_WIDTH/2, GAME_HEIGHT/2);
    }

    private void showMessage() {
        String message = createMessage();

        Font titleFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(titleFont);
        gc.setFill(Color.RED);
        gc.fillText(message, 275, 200);

        String actionPrompt = createActionPrompt();

        Font normalTextFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(normalTextFont);
        gc.setFill(Color.RED);
        gc.fillText(actionPrompt, 325, 275);

    }

    private String createMessage() {
        if (!hasWon) {
            return "The animals all died, but don't give up! You can retry the level.";
        }

        if (currentLevel.getLevel() == Game.MAX_LEVEL) {
            return "You won! You successfully saved the animals";
        } else {
            return "Congrats! You passed this level! But brace yourself, There are more challenges ahead.";
        }

    }

    private String createActionPrompt() {
        if (!hasWon) {
            return "Press ENTER to retry the current level or ESC to quit.";
        }

        if (currentLevel.getLevel() == Game.MAX_LEVEL) {
            return "Press ESC to quit.";
        } else {
            return "Press ENTER to enter the next level or ESC to quit.";
        }
    }

    /**
     * Draws the end scene.
     */
    @Override
    public void draw() {
        backgroundMusic();
        activeKeys.clear();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                background.render(gc);

                showMessage();
                if (activeKeys.contains(KeyCode.ENTER)) {
                    stopMusic();
                    playSoundEffect("images/soundEffects/select.mp3");
                    this.stop();
                    if (!hasWon) {
                        gameController.retryCurrentLevel();
                    } else {
                        if (currentLevel.getLevel() != Game.MAX_LEVEL) {
                            gameController.startNextLevel();
                        }
                    }

                } else if (activeKeys.contains(KeyCode.ESCAPE)) {
                    stopMusic();
                    playSoundEffect("images/soundEffects/select.mp3");
                    this.stop();
                    gameController.exit();
                }
            }
        };

        timer.start();
    }

}
