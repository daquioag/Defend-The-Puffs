package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

/**
 * Represents a single Attack.
 *
 * @author Al and Bosco
 * @version 2022
 */
public class Attack extends StationarySprite {

    /**
     * The status of the attack.
     */
    protected boolean stale = false;
    /**
     * The type of attack.
     */
    protected AttackType attackType;

    /**
     * Constructs an Attack of the certain attackType.
     *
     * @param attackType The type of attack.
     */
    public Attack(final AttackType attackType) {
        super(attackType.getImageDir(), attackType.getWidth(), attackType.getHeight());
        this.attackType = attackType;
    }

    /**
     * Returns the attack type.
     *
     * @return the attackType
     */
    public AttackType getAttackType() {
        return attackType;
    }

    /**
     * Calls getTimeToStale after a certain time period.
     * @param deltaTime The timer of the game loop.
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        if (elapsedTime > attackType.getTimeToStale()) {
            stale = true;
        }
    }

    /**
     * Plays the attack's sound effect.
     */
    public void soundEffect() {
        Media h = new Media(Paths.get(attackType.getSoundPath()).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setStartTime(new Duration(700));
        mediaPlayer.play();
    }

    /**
     * Compares the attack object with another.
     *
     * @return true if the object is the same as the current attack, otherwise, false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Attack attack = (Attack) o;

        if (stale != attack.stale) return false;
        return getAttackType() == attack.getAttackType();
    }

    /**
     * Returns a hashCode value for the attacj object.
     *
     * @return the unique hashCode as an integer
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stale ? 1 : 0);
        result = 31 * result + getAttackType().hashCode();
        return result;
    }
}
