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



/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */

public class GameOver extends BorderPane {
    private CombatView combat;
    private Stage stage;
    private int highScore;
    private boolean newHighScore;

    public GameOver(Stage stage, CombatView combat, int highScore, boolean newHighScore) {
        this.stage = stage;
        this.combat = combat;
        this.highScore = highScore;
        this.newHighScore = newHighScore;
    }

    public BorderPane getPane() {
        VBox text = new VBox();

        Text gameOver = new Text("GAME OVER");
        gameOver.setFont(Font.font("Courier New", 52));
        gameOver.setFill(Color.WHITE);

        Text newHighScoreText = new Text("");
        if(newHighScore == true){
            newHighScoreText.setText("New High Score!");
            newHighScoreText.setFont(Font.font("Courier New", 32));
            newHighScoreText.setFill(Color.YELLOW);
        }

        Text score = new Text("Score: " + combat.getLevel().getScore());
        score.setFont(Font.font("Courier New", 32));
        score.setFill(Color.WHITE);

        Text retryBt = new Text("Try Again?");
        retryBt.setFont(Font.font("Courier New", 28));
        retryBt.setFill(Color.WHITE);

        retryBt.setCursor(Cursor.HAND);
        retryBt.setOnMouseClicked(click -> {
            Home home = new Home(stage, highScore);
            Scene scene = home.getScene();
            stage.setScene(scene);
            stage.show();
        });

        text.getChildren().addAll(gameOver, newHighScoreText,score, retryBt);
        setCenter(text);
        text.setAlignment(Pos.CENTER);

        return this;
    }
}
