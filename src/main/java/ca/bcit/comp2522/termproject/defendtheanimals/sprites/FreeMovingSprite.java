package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

import ca.bcit.comp2522.termproject.defendtheanimals.utilities.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Represents a sprite that can move in a specific direction.
 * @author Al & Bosco
 * @version 2022
 */
public class FreeMovingSprite extends Sprite {

    /**
     * The maximum mana the FreeMovingSprite object can have.
     */
    public static final int MAX_MANA = 5;

    /**
     * A Vector object representing the FreeMovingSprite's speed.
     */
    protected Vector velocity;

    /**
     * A Direction object representing the FreeMovingSprite's direction.
     */
    protected Direction direction;

    /**
     * A Hashmap where the key is the direction and the value is a list of images.
     */
    protected HashMap<Direction, List<Image>> images;

    /**
     * The Attack that the FreeMovingSprite is performing.
     */
    protected Attack currentAttack;

    /**
     * The list of attacks that the FreeMovingSprite has.
     */
    protected ArrayList<Attack> attacks;

    /**
     * The amount of mana the FreeMovingSprite has.
     */
    protected int mana;

    /**
     * Constructs a FreeMovingSprite object consisting of the sprite image, and the width and height of the image.
     *
     * @param imageDir The image directory of the BasicDemon's sprite images.
     * @param width The width of the BasicDemon.
     * @param height The height of the BasicDemon.
     */
    public FreeMovingSprite(final String imageDir, final double width, final double height) {
        super(imageDir, width, height);
        init();
    }

    private void init() {
        this.velocity = new Vector();
        this.direction = Direction.LEFT;  // shouldn't default to left but idk what else to do
        images = new HashMap<>();
        loadImages();
        setImage(images.get(Direction.LEFT).get(0));
        attacks = new ArrayList<>();
        attacks.add(new Attack(AttackType.FIRE));
        attacks.add(new Attack(AttackType.SLASH));
        attacks.add(new Attack(AttackType.BLUE_BLAST));
        mana = 5;

    }
    /**
     * Adds the image sprites of the FreeMovingSprite to the images HashMap as well as the direction.
     * The images in the images HashMap is what is going to animate the BasicDemon during game play.
     *
     */
    @Override
    void loadImages() {
        File folder = new File(imageDir);
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                for (File imageFile : Objects.requireNonNull(fileEntry.listFiles())) {
                    String dirName = fileEntry.getName();
                    Direction folderDirection = switch (dirName) {
                        case "up" -> Direction.UP;
                        case "left" -> Direction.LEFT;
                        case "right" -> Direction.RIGHT;
                        default -> Direction.DOWN;
                    };
                    List<Image> imagesByDirection = images.get(folderDirection);
                    if (imagesByDirection == null) {
                        imagesByDirection = new ArrayList<>();
                        imagesByDirection.add(new Image(imageFile.toURI().toString(),
                                this.boundary.width, this.boundary.height, true, true));
                        images.put(folderDirection, imagesByDirection);
                    } else {
                        imagesByDirection.add(new Image(imageFile.toURI().toString(),
                                this.boundary.width, this.boundary.height, true, true));
                    }
                }
            }
        }

    }

    /**
     * Returns the velocity of the FreeMovingSprite.
     *
     * @return the velocity as a Vector.
     */
    public Vector getVelocity() {
        return this.velocity;
    }

    /**
     * Returns the direction of the FreeMovingSprite.
     *
     * @return the direction as a Direction.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Returns a boolean whether the FreeMovingSprite is moving or not.
     *
     * @return true if the velocity of this FreeMovingSprite is more than 0, else false.
     */
    public boolean isMoving() {
        return this.velocity.getLength() > 0;
    }

    /**
     * Sets the direction of the FreeMoving Sprite.
     *
     * @param direction the new direction of the FreeMovingSprite.
     */
    public void setDirection(final Direction direction) {
        this.direction = direction;
        velocity.setAngle(direction);
    }

    /**
     * If the attack's mana consumption is more than the current mana of the FreeMovingSprite, do nothing.
     * Stream the attacks, and if the attack is null, do nothing.
     * Subtract the attackType's manaConsumption from the FreeMovingSprite's current mana integer.
     * Render the direction of the attack based on the direction the FreeMovingSprite is facing.
     *
     * @param attackType the type of the attack the FreeMovingSprite is performing.
     * @throws IllegalStateException if there is no direction.
     */
    public void attack(final AttackType attackType) {

        if (mana < attackType.getManaConsumption()) {  // if not enough mena
            return;
        }

        Attack attackSelected = attacks.stream()
                                    .filter(attack -> attack.getAttackType().equals(attackType))
                                    .findFirst().orElse(null);

        if (attackSelected == null) {   // if the attack doesn't exist
            return;
        }

        currentAttack = attackSelected;
        currentAttack.soundEffect();
        mana -= attackType.getManaConsumption();

        switch (getDirection()) {
            case UP -> currentAttack.setPosition(getPositionX(), getPositionY() - 30);
            case DOWN -> currentAttack.setPosition(getPositionX(), getPositionY() + 30);
            case LEFT -> currentAttack.setPosition(getPositionX() - 30, getPositionY());
            case RIGHT -> currentAttack.setPosition(getPositionX() + 30, getPositionY());
            default -> throw new IllegalStateException("Unexpected value: " + getDirection());
        }
    }

    /**
     * Returns a boolean whether the FreeMovingSprite is attacking or not.
     *
     * @return true if the current attacking is not null, else false.
     */
    public boolean isAttacking() {
        return currentAttack != null;
    }

    /**
     * Returns a boolean whether the FreeMovingSprite's current attack overlaps with another sprite.
     *
     * @param sprite the sprite
     * @return true if the current attack image overlaps with the sprite parameter.
     */
    public boolean isInAttackRange(final Sprite sprite) {
        return currentAttack.overlaps(sprite);
    }

    /**
     * Returns the current Attack of this FreeMovingSprite.
     *
     * @return if the FreeMovingSprite is attacking, return the current attack, else null
     */
    public Attack getCurrentAttack() {
        if (isAttacking()) {
            return currentAttack;
        }
        return null;
    }

    /**
     * Increase the current mana of the FreeMovingSprite object by 1.
     */
    public void incrementMena() {
        if (mana < MAX_MANA) {
            mana++;
        }
    }

    /**
     * Update the state of the FreeMovingSprite object in the game loop of the GameScene.
     *
     * @param deltaTime the time ticks of the game loop.
     */
    @Override
    public void update(final double deltaTime) {
        this.elapsedTime += deltaTime;
        this.position.translate(this.velocity.getX() * deltaTime, this.velocity.getY() * deltaTime);
        cycle -= deltaTime;
        if (cycle < 0 && isMoving()) {
            List<Image> directionImages = images.get(direction);
            int currentImageIndex = directionImages.indexOf(currentImage);
            int nextImageIndex = (currentImageIndex + 1) % directionImages.size();
            Image nextImage = directionImages.get(nextImageIndex);
            setImage(nextImage);
            cycle = ANIMATION_CYCLE;
        }

        if (isAttacking()) {
            currentAttack.update(deltaTime);
            if (currentAttack.stale) {
                currentAttack.elapsedTime = 0;
                currentAttack.stale = false;
                currentAttack = null;
            }
        }


    }

    /**
     * Renders the FreeMovingSprite object on the screen during game play.
     * @param context the graphics context from JavaFx.
     */
    @Override
    public void render(final GraphicsContext context) {
        context.save();
        context.translate(this.position.getX(), this.position.getY());
        context.translate(-this.currentImage.getWidth()/2, -this.currentImage.getHeight()/2);
        context.drawImage(this.currentImage, 0,0);

        context.setFill(Color.BLUE);
        context.setStroke(Color.WHITE);

        context.fillRect(-3, // left
                -10, // down
                this.mana * 10, // width
                6);
        context.strokeRect(-3, // left
                -10, // down
                this.mana * 10, // width
                6);
        context.fillRect(-3, // left
                -10, // down
                this.mana * 10, // width
                6);

        context.setFill(Color.GREEN);
        context.setStroke(Color.BLUE);

        context.restore();

        if (isAttacking()) {
            currentAttack.render(context);
        }

    }

    /**
     * Returns true if this object is equal to the argument.
     * FreeMovingSprites are equal if they have the same velocity and direction.
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

        FreeMovingSprite that = (FreeMovingSprite) o;

        if (!getVelocity().equals(that.getVelocity())) {
            return false;
        }
        return getDirection() == that.getDirection();
    }

    /**
     * Returns a hashCode value for the current FreeMovingSprite object.
     *
     * @return result the unique hashCode as an integer
     */
    @Override
    public int hashCode() {
        final int nonZeroPrimeNumber = 31;
        int result = super.hashCode();
        result = nonZeroPrimeNumber * result + getVelocity().hashCode();
        result = nonZeroPrimeNumber * result + getDirection().hashCode();
        return result;
    }
}
