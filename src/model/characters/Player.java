package model.characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import model.bullets.Missile;
import model.bullets.PlayerBullet;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class Player extends Polygon {
    private boolean dead = false;
    private boolean poweredUp = false;
    private int speed;
    private int missileCount = 5;

    public Player(int x, int y, double... points) {
        super(points);
        setTranslateX(x);
        setTranslateY(y);
        this.speed = 15;
        getPoints().addAll(17.5, 0.0, 0.0, 35.0, 35.0, 35.0);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/player.png");
    }


    public void moveLeft() {
        if (getTranslateX() - speed > 0) {
            setTranslateX(getTranslateX() - speed);
        }
        else {
            setTranslateX(0);
        }
    }

    public void moveRight() {
        if(getTranslateX() + speed < 565) {
            setTranslateX(getTranslateX() + speed);
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

    public Missile shootMissile(){
        Missile missile = new Missile((int) this.getTranslateX() + 15, (int) this.getTranslateY());

        return missile;
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

    public int getMissileCount() {
        return missileCount;
    }

    public void decreaseMissileCount() {
        this.missileCount--;
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
