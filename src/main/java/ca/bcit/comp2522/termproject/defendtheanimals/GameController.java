package ca.bcit.comp2522.termproject.defendtheanimals;

import ca.bcit.comp2522.termproject.defendtheanimals.scenes.*;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Represents the controller of the game which handles scene changes and manages levels.
 * @author Al & Bosco
 * @version 2022
 */
public class GameController {

    private final Stage stage;
    private final GeneralScene[] scenes = new GeneralScene[Game.MAX_SCENES];
    private Level currentLevel;

    /**
     * Constructs a game controller.
     * @param stage the stage from java fx.
     * @throws SQLException if there is problem connecting to a mysql instance.
     * @throws ClassNotFoundException if the jdbc driver is not found.
     */
    public GameController(final Stage stage) throws SQLException, ClassNotFoundException {
        this.stage = stage;
        this.currentLevel = new Level(1);

        scenes[Game.LOGIN_SCENE] = new LoginScene(this);
        scenes[Game.WELCOME_SCENE] = new WelcomeScene(this);
        scenes[Game.GAME_SCENE] = new GameScene(this, currentLevel);

    }

    /**
     * Starts the game.
     */
    public void startGame() {
        stage.setTitle("Defend The Animals!");
        setScene(Game.LOGIN_SCENE);
        stage.show();
    }

    /**
     * Changes to the welcome scene.
     */
    public void changeToWelcomeScene() {
        setScene(Game.WELCOME_SCENE);
    }

    /**
     * Changes to the game scene.
     */
    public void changeToGameScene() {
        setScene(Game.GAME_SCENE);
    }

    /**
     * Constructs and changes to the end scene.
     * @param hasWon a boolean variable indicating if the player won the current level.
     */
    public void changeToEndScene(boolean hasWon) {
        scenes[Game.END_SCENE] = new EndScene(this, hasWon, currentLevel);
        setScene(Game.END_SCENE);
    }

    /**
     * Starts the next level.
     */
    public void startNextLevel() {
        int levelNum = currentLevel.getLevel();
        int nextLevelNum = levelNum + 1;
        Level level = new Level(nextLevelNum);
        this.currentLevel = level;
        scenes[Game.GAME_SCENE] = new GameScene(this, level);
        setScene(Game.GAME_SCENE);
    }

    /**
     * Replay the current level.
     */
    public void retryCurrentLevel() {
        int levelNum = currentLevel.getLevel();
        Level level = new Level(levelNum);
        this.currentLevel = level;
        scenes[Game.GAME_SCENE] = new GameScene(this, level);
        setScene(Game.GAME_SCENE);
    }

    private void setScene(int sceneNum) {
        stage.setScene(scenes[sceneNum]);
        scenes[sceneNum].draw();
    }

    public void exit() {
        stage.hide();
    }


}
