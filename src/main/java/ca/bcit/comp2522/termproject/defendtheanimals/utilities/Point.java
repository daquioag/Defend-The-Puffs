package ca.bcit.comp2522.termproject.defendtheanimals.utilities;

import ca.bcit.comp2522.termproject.defendtheanimals.scenes.GeneralScene;

public class Point {
    protected double x;
    protected double y;

    public Point() {
        this.set(0, 0);
    }

    public Point(final double x, final double y) {
        this.set(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void set(final double x, final double y) {
        if (x > GeneralScene.GAME_WIDTH) {
            this.x = GeneralScene.GAME_WIDTH;
        } else if (x < 0) {
            this.x = 0;
        } else {
            this.x = x;
        }

        if (y > GeneralScene.GAME_HEIGHT) {
            this.y = GeneralScene.GAME_HEIGHT;
        } else if (y < 0) {
            this.y = 0;
        } else {
            this.y = y;
        }

    }

    public void translate(double dx, double dy) {
        this.set(this.x + dx, this.y + dy);
    }
}
