package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

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
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/bullet.png");
    }

    @Override
    public void moveUp() {
        setTranslateY(getTranslateY() - getSpeed());
    }

    @Override
    public void moveDown() {
        setTranslateY(getTranslateY() + getSpeed());
    }

    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.DARKRED);
        }
    }
}
