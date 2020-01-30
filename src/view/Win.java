package view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HighScoreDatabase;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */

public class Win extends BorderPane {
    private CombatView combat;
    private Stage stage;
    private HighScoreDatabase DB;

    public Win(Stage stage, CombatView combat, HighScoreDatabase DB) {
        this.stage = stage;
        this.combat = combat;
        this.DB = DB;
    }

    public BorderPane getPane() {
        VBox text = new VBox();
        Text winner = new Text(160, 300, "YOU WIN");
        winner.setFont(Font.font("Courier New", 52));
        winner.setFill(Color.WHITE);

        Text score = new Text("Score: " + combat.getLevel().getScore());
        score.setFont(Font.font("Courier New", 32));
        score.setFill(Color.WHITE);

        Text playAgainBt = new Text("Play Again?");
        playAgainBt.setFont(Font.font("Courier New", 28));
        playAgainBt.setFill(Color.WHITE);

        playAgainBt.setCursor(Cursor.HAND);
        playAgainBt.setOnMouseClicked(click -> {
            Home home = new Home(stage, DB);
            Scene scene = home.getScene();
            stage.setScene(scene);
            stage.show();
        });

        text.getChildren().addAll(winner, score, playAgainBt);
        setCenter(text);
        text.setAlignment(Pos.CENTER);

        return this;
    }
}
