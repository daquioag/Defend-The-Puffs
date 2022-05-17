package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

/**
 * Represents a tank demon.
 *
 * @author Al & Bosco
 * @version 2022
 */
public class TankDemon extends BasicDemon {

    /**
     * Tank demon health points.
     */
    public static final int TANK_DEMON_HEALTH = 150;
    /**
     * The width of tank demons.
     */
    public static final double TANK_DEMON_WIDTH = 125;
    /**
     * The height of tank demons.
     */
    public static final double TANK_DEMON_HEIGHT = 125;

    /**
     * Constructs a tank demon.
     * @param imageDir The image directory of the tank demon.
     */
    public TankDemon(String imageDir) {
        super(imageDir, TANK_DEMON_WIDTH, TANK_DEMON_HEIGHT);
        this.healthPoints = TANK_DEMON_HEALTH;
    }
}
