package model.collectibles;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-02-20
 */
public abstract class PickUp
        extends Circle {

    private boolean fallen;
    private double speed;

    public PickUp(double centerX, double centerY, double radius, Paint fill, double speed) {
        super(centerX, centerY, radius, fill);
        this.speed = speed;
        this.fallen = false;
    }

    abstract public void moveDown();

    public double getSpeed() {
        return speed;
    }

    public boolean isFallen() {
        return fallen;
    }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
    }
}
