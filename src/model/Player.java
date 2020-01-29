package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/player.png");
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

    public ArrayList<PlayerBullet> shoot() {
        ArrayList<PlayerBullet> bullets = new ArrayList<>();

        if(poweredUp){
            PlayerBullet bullet1 = new PlayerBullet((int) this.getTranslateX(), (int) this.getTranslateY());
            PlayerBullet bullet2 = new PlayerBullet((int) this.getTranslateX() + 30, (int) this.getTranslateY());

            bullets.add(bullet1);
            bullets.add(bullet2);
         return bullets;
        }
        else {
            PlayerBullet bullet = new PlayerBullet((int) this.getTranslateX() + 15, (int) this.getTranslateY());
            bullets.add(bullet);
            return bullets;
        }
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

    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.WHITE);
        }
    }
}
