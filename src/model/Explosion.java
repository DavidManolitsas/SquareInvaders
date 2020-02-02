package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-02-02
 */
public class Explosion extends Rectangle {

    private boolean dead;

    public Explosion(double x, double y) {
        super(120, 120, Color.GREEN);
        dead = false;
        setTranslateX(x);
        setTranslateY(y);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/explosion.png");
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.GREEN);
        }
    }

}
