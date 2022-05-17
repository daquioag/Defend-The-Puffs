package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

import ca.bcit.comp2522.termproject.defendtheanimals.utilities.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

/**
 * Represents a single Sprite.
 *
 * @author Al & Bosco
 * @version 2022
 */
public abstract class Sprite {

    /**
     * The amount of time for each animation cycle.
     */
    public static final double ANIMATION_CYCLE = 0.1;

    /**
     * The position of this Sprite.
     */
    protected Point position;

    /**
     * The amount of time for each animation cycle for this Sprite.
     */
    protected Rectangle boundary;

    /**
     * The amount of time after the game starts.
     */
    protected double elapsedTime;

    /**
     * The image directory for this Sprite.
     */
    protected String imageDir;

    /**
     * The current image for this Sprite.
     */
    protected Image currentImage;

    /**
     * The amount of time for each animation cycle.
     */
    protected double cycle = ANIMATION_CYCLE;

    /**
     * The Media PLayer object used to play sound effects.
     */
    protected MediaPlayer mediaPlayer;

    /**
     * Constructs a Sprite object consisting of the sprite image, and the width and height of the image.
     *
     * @param imageDir The image directory of the BasicDemon's sprite images.
     * @param width The width of the BasicDemon.
     * @param height The height of the BasicDemon.
     */
    public Sprite(final String imageDir, final double width, final double height) {
        this.position = new Point();
        this.boundary = new Rectangle(0, 0, width, height);
        this.elapsedTime = 0;
        this.imageDir = imageDir;
    }

    abstract void loadImages();

    /**
     * Sets this Sprite's current image to the image parameter.
     *
     * @param image the image to be set as this Sprite's current image.
     */
    public void setImage(final Image image) {
        this.currentImage = image;
    }

    /**
     * Returns this Sprite's X coordinate.
     *
     * @return this Sprite's X coordinate as a double.
     */
    public double getPositionX() {
        return this.position.getX();
    }

    /**
     * Returns this Sprite's Y coordinate.
     *
     * @return this Sprite's Y coordinate as a double.
     */
    public double getPositionY() {
        return this.position.getY();
    }

    /**
     * Modifies this Sprite's X and Y coordinate.
     *
     * @param x this Sprite's new x coordinate
     * @param y this Sprite's new y coordinate
     */
    public void setPosition(final double x, final double y) {
        this.position.set(x, y);
    }

    /**
     * Returns this Sprite's elapsedTime.
     *
     * @return this Sprite's elapsedTime as a double.
     */
    public double getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Returns this Sprite's boundary.
     *
     * @return this Sprite's boundary as a Rectangle.
     */
    public Rectangle getBoundary() {
        this.boundary.setPosition(this.position.getX(), this.position.getY());
        return this.boundary;
    }

    /**
     * Evaluates if this Sprite object overlaps with another Sprite Object.
     *
     * @param other the other Sprite Object that may overlap with this Sprite object.
     * @return true if the boundaries of this Sprite Object overlaps with another Sprite object.
     */
    public boolean overlaps(final Sprite other) {
        return this.getBoundary().overlaps(other.getBoundary());
    }

    /**
     * Updates the elpased time based on the deltaTime parameter.
     *
     * @param deltaTime the time that passed after the game starts.
     */
    public void update(final double deltaTime) {
        this.elapsedTime += deltaTime;
    }
    /**
     * Renders the Sprite object on the screen during game play.
     * @param context the graphics context from JavaFx.
     */
    public void render(final GraphicsContext context) {
        context.save();
        context.translate(this.position.getX(), this.position.getY());
        context.translate(-this.currentImage.getWidth()/2, -this.currentImage.getHeight()/2);
        context.drawImage(this.currentImage, 0,0);
        context.restore();
    }

    /**
     * Returns true if this object is equal to the argument.
     * Sprites are equal if they have the same ElapsedTime, position, boundary, and currentImage.
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

        Sprite sprite = (Sprite) o;

        if (Double.compare(sprite.getElapsedTime(), getElapsedTime()) != 0) {
            return false;
        }
        if (!position.equals(sprite.position)) {
            return false;
        }
        if (!getBoundary().equals(sprite.getBoundary())) {
            return false;
        }
        return currentImage.equals(sprite.currentImage);
    }

    /**
     * Returns a hashCode value for the current Sprite object.
     *
     * @return result the unique hashCode as an integer
     */
    @Override
    public int hashCode() {
        long temp;
        final int nonZeroPrimeNumber = 31;
        int result = position.hashCode();
        result = nonZeroPrimeNumber * result + getBoundary().hashCode();
        result = nonZeroPrimeNumber * result + currentImage.hashCode();
        temp = Double.doubleToLongBits(getElapsedTime());
        result = nonZeroPrimeNumber * result + (int) (temp ^ (temp >>> nonZeroPrimeNumber));
        return result;
    }
}

class Rectangle {

    /**
     * The X coordinate of this Rectangle.
     */
    double x;

    /**
     * The Y coordinate of this Rectangle.
     */
    double y;

    /**
     * The width of this Rectangle.
     */
    double width;

    /**
     * The height of this Rectangle.
     */
    double height;

    Rectangle() {
        this.setPosition(0, 0);
        this.setSize(1, 1);
    }

    Rectangle(final double x, final double y, final double w, final double h) {
        this.setPosition(x, y);
        this.setSize(w, h);
    }

    public void setPosition(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(final double w, final double h) {
        this.width = w;
        this.height = h;
    }

    public boolean overlaps(final Rectangle other) {
        boolean noOverlap =
                this.x + this.width < other.x || other.x + other.width < this.x
                        || this.y + this.height < other.y || other.y + other.height < this.y;

        return !noOverlap;
    }


}
