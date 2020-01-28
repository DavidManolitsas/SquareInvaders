package model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-21
 */
public class Life extends Circle {

    public Life(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius, Color.RED);
    }
}
