package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-02-02
 */
public class Missile extends PlayerBullet {

    public Missile(int x, int y) {
        super(x, y);
        super.setSpeed(10);
        setTranslateX(x);
        setTranslateY(y);
        setWidth(10);
        setHeight(30);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/missile.png");
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
            setFill(Color.GREEN);
        }
    }

}
