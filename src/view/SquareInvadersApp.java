package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HighScoreDatabase;

public class SquareInvadersApp
        extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HighScoreDatabase highScoreDB = new HighScoreDatabase();
        highScoreDB.runConnection();

        Home home = new Home(stage);
        Scene scene = home.getScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
