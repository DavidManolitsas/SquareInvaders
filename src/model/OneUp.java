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
 * @date 2020-01-23
 */
public class OneUp extends Circle {

    private boolean fallen = false;
    private double speed;

    public OneUp() {
        super(292.5, 7.0, 5.0, Color.DEEPPINK);
        this. speed = 1.7;
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/life.png");
    }


    public void moveDown(){
        if(getCenterY() + getRadius() < 750 - getRadius()){
            setCenterY(getCenterY() + speed);
        }
        else {
            setCenterY(750 - getRadius());
        }
    }

    public boolean isFallen() {
        return fallen;
    }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
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
