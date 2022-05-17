package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

import ca.bcit.comp2522.termproject.defendtheanimals.utilities.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a single BasicDemon.
 * @author Al & Bosco
 * @version 2022
 */
public class BasicDemon extends Sprite {

    /**
     * The amount of time the BasicDemon has before it can take damage.
     */
    public static final double IMMUNITY_PERIOD = 0.2;

    /**
     * The starting direction the BasicDemon is facing when it spawns.
     */
    public static final Direction DEMON_DIRECTION = Direction.LEFT;

    /**
     * The health points of the BasicDemon.
     */
    public static final int BASIC_DEMON_HEALTH = 60;
    /**
     * The speed of the BasicDemon.
     */
    public static final int BASIC_DEMON_SPEED = 50;

    /**
     * The width of the BasicDemon's sprite.
     */
    public static final double BASIC_DEMON_WIDTH = 65;

    /**
     * The height of the BasicDemon's sprite.
     */
    public static final double BASIC_DEMON_HEIGHT = 65;

    /**
     * A Vector object representing the BasicDemon's speed.
     */
    protected Vector velocity;

    /**
     * A Direction object representing the BasicDemon's direction.
     */
    protected Direction direction;

    /**
     * A list of images of the BasicDemon's sprites.
     */
    protected List<Image> images;
    /**
     * The health points of the BasicDemon.
     */
    protected int healthPoints;

    /**
     * The time of immunity of the BasicDemon.
     */
    protected double immunityClock;

    /**
     * A boolean representing if the BasicDemon is being attacked.
     */
    protected boolean isUnderAttack;

    private Attack theAttackThatTheDemonIsSuffering;


    /**
     * Constructs a BasicDemon object consisting of the sprite image, and the width and height of the image.
     *
     * @param imageDir The image directory of the BasicDemon's sprite images.
     * @param width The width of the BasicDemon.
     * @param height The height of the BasicDemon.
     */
    public BasicDemon(final String imageDir, final double width, final double height) {
        super(imageDir, width, height);
        this.direction = DEMON_DIRECTION;
        this.healthPoints = BASIC_DEMON_HEALTH;
        this.immunityClock = 0;
        this.isUnderAttack = false;
        this.velocity = new Vector();
        velocity.setLength(BASIC_DEMON_SPEED);
        velocity.setAngle(this.direction);

        images = new ArrayList<>();
        loadImages();
        setImage(images.get(0));

    }

    /**
     * Adds the image sprites of the BasicDemon to the images ArrayList. The images in the images ArrayList
     * is what is going to animate the BasicDemon during game play.
     *
     */
    @Override
    void loadImages() {
        File folder = new File(imageDir);
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                continue;
            }
            images.add(new Image(fileEntry.toURI().toString(),
                    this.boundary.width, this.boundary.height, true, true));
        }
    }

    private void decreaseHealth() {
        this.healthPoints -= this.theAttackThatTheDemonIsSuffering.getAttackType().getDamagePoint();
    }

    /**
     * Sets the isUnderAttack boolean to true.
     * Sets the theAttackThatTheDemonIsSuffering to the current attack BasicDemon is taking.
     *
     * @param attack The attack the BasicDemon is taking.
     */
    public void takeAttack(final Attack attack) {
        isUnderAttack = true;
        theAttackThatTheDemonIsSuffering = attack;
    }

    /**
     * Returns the health points of the BasicDemon.
     *
     * @return the healthPoints as an integer.
     */
    public int getHealthPoints() {
        return healthPoints;
    }


    /**
     * Update the state of the BasicDemon object in the game loop of the GameScene.
     *
     * @param deltaTime the time ticks of the game loop.
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.position.translate(this.velocity.getX() * deltaTime, this.velocity.getY() * deltaTime);
        cycle -= deltaTime;
        if (cycle < 0) {
            int nextImageIndex = (images.indexOf(currentImage) + 1) % images.size();
            setImage(images.get(nextImageIndex));
            cycle = ANIMATION_CYCLE;
        }

        if (isUnderAttack) {
            if (immunityClock == 0) {
                decreaseHealth();
                immunityClock += deltaTime;
            } else if (immunityClock < IMMUNITY_PERIOD) {
                immunityClock += deltaTime;
            } else {
                isUnderAttack = false;
                theAttackThatTheDemonIsSuffering = null;
                immunityClock = 0;
            }

        }
    }
    /**
     * Returns true if this object is equal to the argument.
     * BasicDemons are equal if they have the same velocity, direction, and image.
     *
     * @return true if this object equals to o, else false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        BasicDemon that = (BasicDemon) o;

        if (!velocity.equals(that.velocity)) {
            return false;
        }
        if (direction != that.direction) {
            return false;
        }
        return images.equals(that.images);
    }

    /**
     * Returns a hashCode value for the current BasicDemon object.
     *
     * @return the unique hashCode as an integer
     */
    @Override
    public int hashCode() {
        final int nonZeroPrimeNumber = 31;

        int result = super.hashCode();
        result = nonZeroPrimeNumber * result + velocity.hashCode();
        result = nonZeroPrimeNumber * result + direction.hashCode();
        result = nonZeroPrimeNumber * result + images.hashCode();
        return result;
    }

    /**
     * Renders the BasicDemon object on the screen during game play.
     * @param context the graphics context from JavaFx.
     */
    @Override
    public void render(final GraphicsContext context) {
        final int moveTextThreePixelsLeft = 3;
        context.save();
        context.translate(this.position.getX(), this.position.getY());
        context.translate(-this.currentImage.getWidth() / 2, -this.currentImage.getHeight() / 2);
        context.drawImage(this.currentImage, 0, 0);

        context.setFill(Color.GREEN);
        context.setStroke(Color.WHITE);

        context.fillRect(-moveTextThreePixelsLeft, // left
                -10, // down
                this.healthPoints, // width
                6);
        context.strokeRect(-moveTextThreePixelsLeft, // left
                -10, // down
                this.healthPoints, // width
                6);
        context.fillRect(-moveTextThreePixelsLeft, // left
                -10, // down
                this.healthPoints, // width
                6);

        context.setFill(Color.GREEN);
        context.setStroke(Color.BLUE);
        context.restore();
    }

}
