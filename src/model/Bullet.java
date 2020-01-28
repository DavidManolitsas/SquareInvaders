package model;

import javafx.scene.paint.Color;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public abstract class Bullet extends MovableRectangle {

    private int speed;
   private boolean dead;

    public Bullet(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
        dead = false;
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

    abstract void moveUp();

    abstract void moveDown();
}
