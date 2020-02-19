package model.collectibles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-02-02
 */
public class MissileIcon extends Rectangle {

    public MissileIcon(double x, double y) {
        super(x, y, 10, 20);
        setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/missileIcon.png");
    }

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
