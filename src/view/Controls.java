package view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Alien;
import model.OneUp;
import model.Overlord;
import model.Player;
import model.PowerUp;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-22
 */
public class Controls extends BorderPane {

    private Stage stage;
    private Scene prevScene;
    private double bodySize = 22;
    private double headerSize = 42;

    public Controls(Stage stage, Scene prevScene) {
        this.stage = stage;
        this.prevScene = prevScene;
    }

    public BorderPane getPane() {
        VBox controlText = new VBox();
        controlText.setSpacing(10);

        Text controlHeading = new Text("Controls");
        controlHeading.setFont(Font.font("Courier New", headerSize));
        controlHeading.setFill(Color.YELLOW);

        Text controls = new Text("A: Left\nD: Right\nSpace: Shoot\n");
        controls.setFont(Font.font("Courier New", bodySize));
        controls.setFill(Color.YELLOW);

        Text backBt = new Text("\nBack");
        backBt.setFont(Font.font("Courier New", 28));
        backBt.setFill(Color.YELLOW);

        backBt.setCursor(Cursor.HAND);
        backBt.setOnMouseClicked(click1 -> {
            stage.setScene(prevScene);
            stage.show();
        });

        //PLAYER
        //create player HBox
        HBox playerControl = new HBox();
        playerControl.setSpacing(15);
        //create components
        Player player = new Player(0, 0);
        Text playerTxt = new Text("Player");
        playerTxt.setFont(Font.font("Courier New", bodySize));
        playerTxt.setFill(Color.YELLOW);
        //add components
        playerControl.getChildren().addAll(player, playerTxt);
        playerControl.setAlignment(Pos.CENTER);

        //ALIEN
        //create player HBox
        HBox alienControl = new HBox();
        alienControl.setSpacing(15);
        //create components
        Alien alien = new Alien(0, 0);
        Text alienTxt = new Text("Alien");
        alienTxt.setFont(Font.font("Courier New", bodySize));
        alienTxt.setFill(Color.YELLOW);
        //add components
        alienControl.getChildren().addAll(alien, alienTxt);
        alienControl.setAlignment(Pos.CENTER);

        //OVERLORD
        //create player HBox
        HBox overlordControl = new HBox();
        overlordControl.setSpacing(15);
        //create components
        Overlord overlord = new Overlord(0,0);
        Text overlordTxt = new Text("???");
        overlordTxt.setFont(Font.font("Courier New", bodySize));
        overlordTxt.setFill(Color.YELLOW);
        //add components
        overlordControl.getChildren().addAll(overlord, overlordTxt);
        overlordControl.setAlignment(Pos.CENTER);

        //1UP
        HBox oneUpControl = new HBox();
        oneUpControl.setSpacing(15);
        //create components
        OneUp oneUp = new OneUp();
        Text oneUpTxt = new Text("1-up");
        oneUpTxt.setFont(Font.font("Courier New", bodySize));
        oneUpTxt.setFill(Color.YELLOW);
        //add components
        oneUpControl.getChildren().addAll(oneUp, oneUpTxt);
        oneUpControl.setAlignment(Pos.CENTER);

        //PowerUp
        HBox powerUpControl = new HBox();
        powerUpControl.setSpacing(15);
        //create components
        PowerUp powerUp = new PowerUp();
        Text powerUpText = new Text("Power Up");
        powerUpText.setFont(Font.font("Courier New", bodySize));
        powerUpText.setFill(Color.YELLOW);
        //add components
        powerUpControl.getChildren().addAll(powerUp, powerUpText);
        powerUpControl.setAlignment(Pos.CENTER);


        controlText.getChildren().addAll(controlHeading, controls, playerControl, alienControl, overlordControl, oneUpControl, powerUpControl, backBt);

        setCenter(controlText);
        controlText.setAlignment(Pos.CENTER);

        return this;
    }
}
