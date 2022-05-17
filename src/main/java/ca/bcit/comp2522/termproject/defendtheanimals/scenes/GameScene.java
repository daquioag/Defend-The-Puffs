package ca.bcit.comp2522.termproject.defendtheanimals.scenes;

import ca.bcit.comp2522.termproject.defendtheanimals.GameController;
import ca.bcit.comp2522.termproject.defendtheanimals.Level;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.*;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the game scene.
 * @author Al & Bosco
 * @version 2022
 */
public class GameScene extends GeneralScene {
    /**
     * The time for demons to spawn during game play in the game scene.
     */
    public static final double SPAWN_FACTOR = 0.01;
    /**
     * The time it takes for the GameScene to load a new frame.
     */
    public static final double DELTA_TIME = 0.01666667;
    /**
     * The number of lives the GameScene starts with.
     * */
    public static final int LIVES = 5;
    private final Random random = new Random();
    private final GameController gameController;
    private final StationarySprite background;
    private final Level level;
    private final String themeMusic;

    private final int numOfWaves;
    private int currentWaveNum;

    private final ArrayList<StationarySprite> lifeLine;

    private final FreeMovingSprite playerCharacter;
    private final ArrayList<BasicDemon> demonList;

    private int score = 0;

    /**
     * Constructs a game scene.
     * @param gameController the game controller.
     * @param level the level.
     */
    public GameScene(final GameController gameController, final Level level) {
        super();
        this.gameController = gameController;  // tried to put it in GeneralScene but it would crash
        String currentMap = "level" + (level.getLevel());
        background = new StationarySprite("images/" + currentMap, GAME_WIDTH, GAME_HEIGHT);
        themeMusic = "images/soundEffects/level" + (level.getLevel()) + ".mp3";
        background.setPosition((double) GAME_WIDTH/2, (double) GAME_HEIGHT/2);

        this.numOfWaves = level.getNumOfWaves();
        this.currentWaveNum = 1;
        this.level = level;
        // make lifeline
        lifeLine = new ArrayList<>();
        for (int n = 0; n < LIVES; n++) {
            StationarySprite life = new StationarySprite("images/jiggly", 50, 50);
            double x = 20;
            double y = n*80 + 220;
            life.setPosition(x,y);
            lifeLine.add(life);
        }


        playerCharacter = createPlayerCharacter();
        demonList = level.getInitialDemonsFromWave(currentWaveNum); // new ArrayList<BasicDemon>();


    }


    /**
     * Draws the game scene.
     */
    @Override
    public void draw() {
        backgroundMusic(themeMusic);
        activeKeys.clear();
        releasedKeys.clear();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {

                if (activeKeys.contains(KeyCode.LEFT)) {
                    playerCharacter.setDirection(Direction.LEFT);
                    playerCharacter.getVelocity().setLength(200);
                } else if (activeKeys.contains(KeyCode.RIGHT)) {
                    playerCharacter.setDirection(Direction.RIGHT);
                    playerCharacter.getVelocity().setLength(200);
                } else if (activeKeys.contains(KeyCode.UP)) {
                    playerCharacter.setDirection(Direction.UP);
                    playerCharacter.getVelocity().setLength(200);
                } else if (activeKeys.contains(KeyCode.DOWN)) {
                    playerCharacter.setDirection(Direction.DOWN);
                    playerCharacter.getVelocity().setLength(200);
                } else {
                    playerCharacter.getVelocity().setLength(0);
                }

                if (releasedKeys.contains(KeyCode.Z)) {
                    playerCharacter.attack(AttackType.FIRE);
                    releasedKeys.clear();
                }

                if (releasedKeys.contains(KeyCode.X)) {
                    playerCharacter.attack(AttackType.SLASH);
                    releasedKeys.clear();
                }

                if (releasedKeys.contains(KeyCode.C)) {
                    playerCharacter.attack(AttackType.BLUE_BLAST);
                    releasedKeys.clear();
                }

                if (activeKeys.contains(KeyCode.ESCAPE)) {
                    mediaPlayer.stop();
                    this.stop();
                    gameController.changeToWelcomeScene();
                }

                if (random.nextDouble() < SPAWN_FACTOR) {
                    if (level.hasDemonsLeftInWave(currentWaveNum)) {
                        demonList.add(level.getDemonFromWave(currentWaveNum));
                    } else {
                        if (currentWaveNum < level.getNumOfWaves()) {
                            currentWaveNum += 1;
                            demonList.addAll(level.getInitialDemonsFromWave(currentWaveNum));
                        }
                    }
                }

                if (currentWaveNum == level.getNumOfWaves() && demonList.size() == 0) {
                    mediaPlayer.stop();
                    this.stop();
                    gameController.changeToEndScene(true);
                }
                if (lifeLine.size() == 0) {
                    mediaPlayer.stop();
                    this.stop();
                    gameController.changeToEndScene(false);
                }


                background.render(gc);

                for (int demonNum = 0; demonNum < demonList.size(); demonNum++) {
                    BasicDemon demon = demonList.get(demonNum);

                    if (playerCharacter.isAttacking() && playerCharacter.isInAttackRange(demon)) {
                        score += 100;
                        demon.takeAttack(playerCharacter.getCurrentAttack());
                        if (demon.getHealthPoints() <= 0) {
                            demonList.remove(demon);
                            playerCharacter.incrementMena();
                        }
                    }
                    for (int lives = 0; lives < lifeLine.size(); lives++) {
                        StationarySprite life = lifeLine.get(lives);
                        if (demon.overlaps(life)) {
                            lifeLine.remove(lives);
                        }
                    }

                    if (demon.getPositionX() < 1) {
                        demonList.remove(demon);
                    }

                    demon.update(DELTA_TIME);
                    demon.render(gc);
                }


                playerCharacter.update(DELTA_TIME);
                playerCharacter.render(gc);


                for (StationarySprite life : lifeLine) {
                    life.update(DELTA_TIME);
                    life.render(gc);
                }

                gc.setFill(Color.WHITE);
                gc.setStroke(Color.GREEN);
                gc.setFont(new Font("Arial Black", 48));
                gc.setLineWidth(3);
                String text = "Score: " + score;
                int textX = 500;
                int textY = 80;
                gc.fillText(text, textX, textY);
                gc.strokeText(text, textX, textY);

            }

        };

        timer.start();
    }

    private FreeMovingSprite createPlayerCharacter() {
        FreeMovingSprite playerCharacter = new FreeMovingSprite("images/characterSprites", 50, 50);
        playerCharacter.setPosition(300, 400);
        return playerCharacter;
    }


}
