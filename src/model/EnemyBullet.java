package model;

import javafx.scene.paint.Color;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class EnemyBullet extends Bullet {

    public EnemyBullet(int x, int y) {
        super(x, y, 5, 15, Color.PURPLE);
        setSpeed(7);
        setTranslateX(x);
        setTranslateY(y);
    }

    public EnemyBullet(int x, int y, Color color) {
        super(x, y,10,10, color);
        setSpeed(9);
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

