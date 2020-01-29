package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-21
 */
public class Life extends Rectangle {

    public Life(double x, double y, double width, double height) {
        super(x, y, width, height);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/life.png");
    }

//    public Life(double centerX, double centerY, double radius) {
//        super(centerX, centerY);
//
//
//    }

    public void setImage(String src) {
        try {
            FileInputStream input = new FileInputStream(src);
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            setFill(imagePattern);
        } catch (FileNotFoundException fnfe){
            setFill(Color.RED);
        }
    }
}
