package model.collectibles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-23
 */

public class OneUp extends PickUp {

    public OneUp() {
        super(292.5, 7.0, 5.0, Color.DEEPPINK, 1.7);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/life.png");
    }

    @Override
    public void moveDown(){
        if(getCenterY() + getRadius() < 750 - getRadius()){
            setCenterY(getCenterY() + getSpeed());
        }
        else {
            setCenterY(750 - getRadius());
        }
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
