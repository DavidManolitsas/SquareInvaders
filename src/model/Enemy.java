package model;

import javafx.scene.paint.Color;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-21
 */
public abstract class Enemy extends Character {

    public Enemy(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
    }

    abstract public void moveLeft();

    abstract public void moveRight();

    abstract public void moveDown();

    abstract public void setHp();

    abstract public EnemyBullet shoot();
}
