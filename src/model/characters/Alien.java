package model.characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import model.bullets.EnemyBullet;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
public class Alien extends Enemy {

    private int hp; //health points
    private boolean hitLeft = false;
    private boolean hitRight = true;

    public Alien(int x, int y) {
        super(x, y, 30, 30, Color.BLACK);
        this.hp = 5;
        setTranslateX(x);
        setTranslateY(y);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/greenAlien.png");
    }

    @Override
    public void moveLeft() {
        if (getTranslateX() - getWidth() > 0) {
            setTranslateX(getTranslateX() - getWidth());
        }
        else {
            setTranslateX(0);
        }

        if(getTranslateX() == 0){
            hitLeft = true;
            hitRight = false;
        }
    }

    @Override
    public void moveRight() {
        if (getTranslateX() + getWidth() < 600 - getWidth()) {
            setTranslateX(getTranslateX() + getWidth());

        }
        else {
            setTranslateX(600 - getWidth());
        }

        if(getTranslateX() ==  600 - getWidth()){
            hitLeft = false;
            hitRight = true;
        }

    }

    public void moveDown(){
        if(getTranslateY() + 20 < 720) {
            setTranslateY(getTranslateY() + 20);
        }
        else {
            setTranslateY(720);
        }
    }

    @Override
    public void setHp(int damage){
        hp-= damage;

        if (hp <= 0){
            setDead(true);
        }
        else if(hp <= 2){
            setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/redAlien.png");
        }
        else if (hp > 2 && hp < 5){
            setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/yellowAlien.png");
        }
    }

    public int getHp() {
        return hp;
    }

    public EnemyBullet shoot(){
        EnemyBullet bullet = new EnemyBullet((int) this.getTranslateX() + 10, (int) this.getTranslateY());
        return bullet;
    }

    public boolean isHitLeft() {
        return hitLeft;
    }

    public void setHitLeft(boolean hitLeft) {
        this.hitLeft = hitLeft;
    }

    public boolean isHitRight() {
        return hitRight;
    }

    public void setHitRight(boolean hitRight) {
        this.hitRight = hitRight;
    }

    public void setColor(Color color){
        super.setFill(color);
    }

    public void setImage(String src) {
       try {
           FileInputStream input = new FileInputStream(src);
           Image image = new Image(input);
           ImagePattern imagePattern = new ImagePattern(image);
           setFill(imagePattern);
       } catch (FileNotFoundException fnfe){
           if(hp == 2){
               setColor(Color.DARKRED);
           }
           else if (hp > 2 && hp < 5){
               setColor(Color.ORANGE);
           }
           else {
               setColor(Color.GREEN);
           }
        }
    }
}
