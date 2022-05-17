package ca.bcit.comp2522.termproject.defendtheanimals.sprites;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stationary sprite.
 *
 * @author Al & Bosco
 * @version 2022
 */
public class StationarySprite extends Sprite {

    protected List<Image> images;

    /**
     * Constructs a stationary sprite.
     * @param imageDir The image directory of the sprite.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     */
    public StationarySprite(final String imageDir, double width, double height) {
        super(imageDir, width, height);
        images = new ArrayList<>();
        loadImages();
        setImage(images.get(0));
    }

    @Override
    void loadImages() {
        File folder = new File(imageDir);
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                continue;
            }
            images.add(new Image(fileEntry.toURI().toString(),
                    this.boundary.width, this.boundary.height, true, true));

        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        cycle -= deltaTime;
        if (cycle < 0) {
            int nextImageIndex = (images.indexOf(currentImage) + 1) % images.size();
            setImage(images.get(nextImageIndex));
            cycle = ANIMATION_CYCLE;
        }
    }

}
