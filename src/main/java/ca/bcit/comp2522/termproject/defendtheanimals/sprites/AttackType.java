package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

/**
 * Different types of attacks that can be used.
 * @author Al & Bosco
 * @version 2022
 */
public enum AttackType {
    FIRE("images/fire", 30, 30, 15, 0, 0.1 ,"images/soundEffects/FIRE.mp3"),
    SLASH("images/slash", 30, 30, 200, 3, 0.1, "images/soundEffects/SLASH.mp3"),
    BLUE_BLAST("images/blueBlast", 200, 200, 30, 5, 1, "images/soundEffects/BLUE_BLAST.mp3");

    private final String imageDir;
    private final double width;
    private final double height;
    private final int damagePoint;
    private final int manaConsumption;
    private final double timeToStale;
    private final String soundPath;

    AttackType(final String imageDir, final double width, final double height, final int damagePoint,
               final int manaConsumption, final double timeToStale, final String soundPath) {
        this.imageDir = imageDir;
        this.width = width;
        this.height = height;
        this.damagePoint = damagePoint;
        this.manaConsumption = manaConsumption;
        this.timeToStale = timeToStale;
        this.soundPath = soundPath;
    }

    /**
     * Returns height of the attackType's sprite.
     *
     * @return the height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns width of the attackType's sprite.
     *
     * @return the width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the damage point of the attack type.
     *
     * @return the damagePoint
     */
    public int getDamagePoint() {
        return damagePoint;
    }

    /**
     * Returns the image directory of the attackType's sprite.
     *
     * @return the imageDir
     */
    public String getImageDir() {
        return imageDir;
    }
    /**
     * Returns the attackType's mana consumption amount.
     *
     * @return the manaConsumption
     */
    public int getManaConsumption() {
        return manaConsumption;
    }

    /**
     * Returns the time for the attack to stay on the screen.
     *
     * @return the timeToStale
     */
    public double getTimeToStale() {
        return timeToStale;
    }

    public String getSoundPath() {
        return soundPath;
    }
}
