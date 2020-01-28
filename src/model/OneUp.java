package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-23
 */
public class OneUp extends Circle {

    private boolean fallen = false;
    private double speed;

    public OneUp() {
        super(292.5, 7.0, 5.0, Color.TURQUOISE);
        this. speed = 1.5;
    }


    public void moveDown(){
        if(getCenterY() + getRadius() < 750 - getRadius()){
            setCenterY(getCenterY() + speed);
        }
        else {
            setCenterY(750 - getRadius());
        }
    }

    public boolean isFallen() {
        return fallen;
    }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
    }
}
