package view;

import java.io.File;

import controller.ControlsHandler;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HighScoreDatabase;
import model.Level;
import model.Player;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-20
 */
class Home {
    private Stage stage;
    private MediaPlayer music;
    private int highScore;
    private final static String SOUND_ROOT = "/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/";
    private HighScoreDatabase DB;

    public Home (Stage stage, int highScore, HighScoreDatabase DB){
        this.stage = stage;
        this.highScore = highScore;
        this.DB = DB;
        composeMusic();
        music.play();
    }

    public Home (Stage stage, HighScoreDatabase DB){
        this.stage = stage;
        this.highScore = 0;
        this.DB = DB;
        composeMusic();
        music.play();
    }

    public Scene getScene(){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600,750, Color.BLACK);

        VBox homeText = new VBox();
        Text heading = new Text("24 Hours Remain");
        heading.setFont(Font.font("Impact", 64));
        heading.setFill(Color.YELLOW);
        Text startBt = new Text("Start");
        startBt.setFont(Font.font("Courier New", 28));
        startBt.setFill(Color.YELLOW);

        startBt.setCursor(Cursor.HAND);
        startBt.setOnMouseClicked(click -> {
            music.stop();
            Level level1 = new Level(1, 1, 3, 0, this.highScore);

            CombatView combatView = new CombatView(stage, level1, highScore, DB);
            Scene combatScene = new Scene(combatView.setContent(), level1.getBackground());
            Player player = combatView.getLevel().getPlayer();

            combatScene.setOnKeyPressed(e1 -> {
                switch (e1.getCode()) {
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case M:
                    if(player.getMissileCount() > 0) {
                        combatView.playerShotMissile(level1.getPlayer().shootMissile());
                        combatView.getLevel().getMissile().play();
                    }
                    break;
                case SPACE:
                    combatView.getLevel().getGunSound().play();
                    combatView.playerShot(level1.getPlayer().shoot());
                    break;
                }
            });

            stage.setScene(combatScene);
            stage.show();
        });


        Text settingBt = new Text("Settings");
        settingBt.setFont(Font.font("Courier New", 28));
        settingBt.setFill(Color.YELLOW);

        //CONTROLS
        ControlsHandler chHandler = new ControlsHandler(stage, scene);
        settingBt.setCursor(Cursor.HAND);
        settingBt.setOnMouseClicked(chHandler);

        Text quitBt = new Text("Quit");
        quitBt.setFont(Font.font("Courier New", 28));
        quitBt.setFill(Color.YELLOW);

        quitBt.setCursor(Cursor.HAND);
        quitBt.setOnMouseClicked(click -> {
            Platform.exit();
        });

        homeText.getChildren().addAll(heading, startBt, settingBt, quitBt);
        root.setCenter(homeText);
        homeText.setAlignment(Pos.CENTER);
        homeText.setSpacing(15);

        return scene;
    }

    private void composeMusic(){
        String musicPath = SOUND_ROOT + "sound/music/00_Home.mp3";
        Media media = new Media(new File(musicPath).toURI().toString());
        music = new MediaPlayer(media);
        music.setVolume(0.8);
    }
}
