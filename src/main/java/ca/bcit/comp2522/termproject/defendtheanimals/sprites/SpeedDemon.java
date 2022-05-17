package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

/**
 * Represents a single SpeedDemon.
 * @author Al & Bosco
 * @version 2022
 */
public class SpeedDemon extends BasicDemon {

    /**
     * The health points of this SpeedDemon.
     */
    public static final int SPEED_DEMON_HEALTH = 60;

    /**
     * The width of this SpeedDemon's sprite.
     */
    public static final double SPEED_DEMON_WIDTH = 55;

    /**
     * The height of this SpeedDemon's sprite.
     */
    public static final double SPEED_DEMON_HEIGHT = 55;

    /**
     * Constructs a SpeedDemon object consisting of the sprite image, and the width and height of the image.
     *
     * @param imageDir The image directory of the SpeedDemon's sprite images.
     */
    public SpeedDemon(final String imageDir) {
        super(imageDir, SPEED_DEMON_WIDTH, SPEED_DEMON_HEIGHT);
        this.healthPoints = SPEED_DEMON_HEALTH;
        this.velocity.setLength(BASIC_DEMON_SPEED * 2);
    }

}
