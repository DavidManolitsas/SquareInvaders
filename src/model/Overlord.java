package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-21
 */
public class Overlord extends Circle {

    private boolean attacking;
    private boolean hitLeft;
    private boolean attackComplete;
    private double speed;
    private int hp;

    public Overlord(double centerX, double centerY) {
        super(centerX, centerY, 20, Color.GREY);
        attacking = true;
        hitLeft = false;
        attackComplete = false;
        speed = 3;
        hp = 7;
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/overlord.png");
    }

    public void moveLeft() {
        if (getCenterX() - 20 > 20){
            setCenterX(getCenterX() - speed);
        }
        else {
            setCenterX(20);
        }
    }

    public void moveRight(){
        if(getCenterX() + 20 < 580){
            setCenterX(getCenterX() + speed);
        }
        else {
            setCenterX(580);
        }
    }

    public void attack(){
        if(hitLeft == false){
            moveLeft();
            if(getCenterX() == 20){
                hitLeft = true;
            }
        }
        else {
            moveRight();
            if(getCenterX() == 580) {
                attackComplete = true;
            }
        }
    }

    public EnemyBullet shoot(){
        EnemyBullet bullet = new EnemyBullet((int) getCenterX(), (int) getCenterY(), true);
        bullet.setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/overlordBullet.png");
        return bullet;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isAttackComplete(){
        return attackComplete;
    }

    public void setHp(int damage){
        hp-= damage;
    }

    public int getHp(){
        return hp;
    }


    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.DEEPPINK);
        }
    }
}
