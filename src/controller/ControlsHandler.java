package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.Controls;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-22
 */
public class ControlsHandler<T extends Event> implements EventHandler<T> {

    private Stage stage;
    private Scene prevScene;

    public ControlsHandler(Stage stage, Scene prevScene) {
        this.stage = stage;
        this.prevScene = prevScene;
    }


    @Override
    public void handle(T event) {
        Controls controls = new Controls(stage, prevScene);

        Scene scene = new Scene(controls.getPane(), 600, 750, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }
}
