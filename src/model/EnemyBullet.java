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
public class EnemyBullet extends Bullet {

    private boolean overlordBullet;

    public EnemyBullet(int x, int y) {
        super(x, y, 5, 15, Color.PURPLE);
        setSpeed(7);
        setTranslateX(x);
        setTranslateY(y);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/alienBullet.png");
    }

    public EnemyBullet(int x, int y, boolean overlordBullet) {
        super(x, y,10,10, Color.GOLD);
        setSpeed(9);
        this.overlordBullet = overlordBullet;
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

    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.PURPLE);
        }
    }
}

