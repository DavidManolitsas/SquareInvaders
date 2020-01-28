package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class Player extends Polygon {
    private int x;
    private int y;
    private boolean dead = false;
    private boolean poweredUp = false;

    public Player(int x, int y, double... points) {
        super(points);
        setTranslateX(x);
        setTranslateY(y);
        getPoints().addAll(17.5, 0.0, 0.0, 35.0, 35.0, 35.0);
        setFill(Color.WHITE);
        setStroke(Color.WHITE);
    }


    public void moveLeft() {
        if (getTranslateX() - 10 > 0) {
            setTranslateX(getTranslateX() - 10);
        }
        else {
            setTranslateX(0);
        }
    }

    public void moveRight() {
        if(getTranslateX() + 10 < 565) {
            setTranslateX(getTranslateX() + 10);
        }
        else {
            setTranslateX(565);
        }
    }

    public PlayerBullet shoot() {
        PlayerBullet bullet = new PlayerBullet((int) this.getTranslateX() + 15, (int) this.getTranslateY());
        return bullet;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean getDead() {
        return dead;
    }

    public boolean isPoweredUp() {
        return poweredUp;
    }

    public void setPoweredUp(boolean poweredUp) {
        this.poweredUp = poweredUp;
    }
}
