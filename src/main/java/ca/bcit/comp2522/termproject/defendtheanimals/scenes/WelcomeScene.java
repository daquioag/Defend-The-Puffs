package ca.bcit.comp2522.termproject.defendtheanimals.scenes;

import ca.bcit.comp2522.termproject.defendtheanimals.GameController;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.StationarySprite;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

/**
 * Represents the welcome scene.
 * @author Al & Bosco
 * @version 2022
 */
public class WelcomeScene extends GeneralScene {

    private GameController gameController;
    private StationarySprite background;

    /**
     * Constructs a welcome scene.
     * @param gameController the game controller.
     */
    public WelcomeScene(GameController gameController) {
        super();

        this.gameController = gameController; // tried to put it in GeneralScene but it would crash

        Button startButton = makeButton("Start", 160, 30);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                stopMusic();
                playSoundEffect("images/soundEffects/select.mp3");
                gameController.changeToGameScene();
            }
        });


        Button resumeButton = makeButton("Resume", 160, 30);
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                playSoundEffect("images/soundEffects/select.mp3");
                System.out.println("Not implemented");
            }
        });

        root.getChildren().add(startButton);
        root.getChildren().add(resumeButton);
        AnchorPane.setBottomAnchor(startButton, 50.0);
        AnchorPane.setLeftAnchor(startButton, 100.0);
        AnchorPane.setBottomAnchor(resumeButton, 50.0);
        AnchorPane.setRightAnchor(resumeButton, 100.0);
        background = new StationarySprite("images/level1", GAME_WIDTH, GAME_HEIGHT);
        background.setPosition(GAME_WIDTH/2, GAME_HEIGHT/2);


        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        Font titleFont = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(titleFont);
        gc.setFill(Color.RED);
        gc.fillText("Defend The Animals!", 275, 200);

    }


    /**
     * Draws the welcome scene.
     */
    @Override
    public void draw() {
        backgroundMusic();
        activeKeys.clear();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                background.render(gc);
              if (activeKeys.contains(KeyCode.ESCAPE)) {
                  stopMusic();
                  this.stop();
                  gameController.exit();
              }
            }
        };

        timer.start();
    }


    private Button makeButton(String text, double minWidth, double minHeight) {
        Font normalTextFont = Font.font("Arial", FontWeight.NORMAL, 20);

        Button button = new Button(text);
        button.setFont(normalTextFont);
        button.setMinSize(minWidth, minWidth);
        return button;
    }


}
