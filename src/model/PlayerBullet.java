package model;

import javafx.scene.paint.Color;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class PlayerBullet extends Bullet {

    public PlayerBullet(int x, int y) {
        super(x, y, 5, 5, Color.DARKRED);
        super.setSpeed(5);
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void moveUp() {
        setTranslateY(getTranslateY() - getSpeed());
    }

    @Override
    public void moveDown() {
        setTranslateY(getTranslateY() + getSpeed());
    }
}
