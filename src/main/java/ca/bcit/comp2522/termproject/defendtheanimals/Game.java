package ca.bcit.comp2522.termproject.defendtheanimals;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
/**
 * Represents a single Game.
 *
 * @author Al & Bosco
 * @version 2022
 */
public class Game extends Application {

    /**
     * The maximum number of scenes.
     */
    public static final int MAX_SCENES = 4;
    /**
     * The number for the login scene.
     */
    public static final int LOGIN_SCENE = 0;
    /**
     * The number for the welcome scene.
     */
    public static final int WELCOME_SCENE = 1;
    /**
     * The number for the game scene.
     */
    public static final int GAME_SCENE = 2;
    /**
     * The number for the end scene.
     */
    public static final int END_SCENE = 3;
    /**
     * The maximum number of levels.
     */
    public static final int MAX_LEVEL = 2;

    @Override
    public void start(final Stage stage) throws IOException, SQLException, ClassNotFoundException {
        GameController gameController = new GameController(stage);
        gameController.startGame();
    }



    public static void main(final String[] args) {
        try {
            launch(args);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}

