package ca.bcit.comp2522.termproject.defendtheanimals.utilities;

import ca.bcit.comp2522.termproject.defendtheanimals.sprites.Direction;

public class Vector extends Point {

    @Override
    public void set(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public void multiply(double m){
        this.x *= m;
        this.y *= m;
    }

    public double getLength(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void setLength(final double L) {
        double currentLength = this.getLength();
        if (currentLength == 0) {
            this.set(L, 0);
        } else {
            this.multiply(1 / currentLength);
            this.multiply(L);
        }
    }

    public double getAngle(){
        return Math.toDegrees(Math.atan2(this.y, this.x));
    }

    public void setAngle(Direction direction) {
        double angleDegrees = 0;
        switch (direction) {
            case UP:
                angleDegrees = 270;
                break;
            case DOWN:
                angleDegrees = 90;
                break;
            case LEFT:
                angleDegrees = 180;
                break;
            case RIGHT:
                angleDegrees = 0;
                break;
        }
        double L = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = L * Math.cos(angleRadians);
        this.y = L * Math.sin(angleRadians);
    }
}