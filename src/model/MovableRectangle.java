package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-21
 */
public abstract class MovableRectangle extends Rectangle {

    private int speed;
    private Boolean dead = false;

    public MovableRectangle
       (int x, int y, int w, int h, Color color) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Boolean getDead() {
        return dead;
    }

    public void setDead(Boolean dead) {
        this.dead = dead;
    }

}
