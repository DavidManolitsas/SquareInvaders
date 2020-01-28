package model;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class Player extends Character {

    private AudioClip gunSound;

    public Player(int x, int y) {
        super(x, y, 35, 35, Color.WHITE);
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void moveLeft() {
        if (getTranslateX() - 10 > 0) {
            setTranslateX(getTranslateX() - 10);
        }
        else {
            setTranslateX(0);
        }
    }

    @Override
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

}
