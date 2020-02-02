package model.characters;

import javafx.scene.paint.Color;
import model.MovableRectangle;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public abstract class Character extends MovableRectangle {
    private Boolean dead = false;

    public Character(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Boolean getDead(){
        return dead;
    }

    public Color getColor(){
        return (Color) super.getFill();
    }

    abstract public void moveLeft();

    abstract public void moveRight();


}
