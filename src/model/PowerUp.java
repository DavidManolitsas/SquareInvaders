package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-01-28
 */
public class PowerUp extends Circle {


    private boolean fallen = false;
    private double speed;

    public PowerUp() {
        super(292.5, 7.0, 5.0, Color.TURQUOISE);
        this. speed = 1.7;
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/powerUp.png");
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
            setFill(Color.TURQUOISE);
        }
    }

}
