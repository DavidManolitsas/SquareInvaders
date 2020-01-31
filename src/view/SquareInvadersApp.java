package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HighScoreDatabase;

public class SquareInvadersApp
        extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Database
        HighScoreDatabase DB = new HighScoreDatabase();
        DB.runConnection();
//        DB.dropTable();
        DB.createHighScoreTable();

        int highScore = DB.getHighScore();

        Home home;
        if(highScore == 0) {
            home = new Home(stage, DB);
        }
        else {
            home = new Home(stage, highScore, DB);
        }
        Scene scene = home.getScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
