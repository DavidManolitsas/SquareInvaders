package view;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-22
 */

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Alien;
import model.EnemyBullet;
import model.Level;
import model.Life;
import model.OneUp;
import model.Overlord;
import model.Player;
import model.PlayerBullet;

class CombatView extends Pane {
    private final Stage stage;
    private Level level;
    private int highScore;
    //timers
    private double time = 0;
    private double overlordTimer = 0;
    private double oneUpTimer = 0;
    //JavaFx Components
    private Text scoreText;
    private Text levelNumText;
    private Text highScoreText;

    public CombatView(Stage stage, Level level, int highScore) {
        this.stage = stage;
        this.level = level;
        this.highScore = highScore;
        level.setHighScore(this.highScore);

        //play level music
        level.getMusic().setAutoPlay(true);
    }

    public Pane setContent(){
        setPrefSize(600, 750);
        drawAliens();
        drawPlayer();
        drawLevelNum();
        drawScore();
        drawHighScore();
        drawLives(level.getLives());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                redraw();
            }
        };
        timer.start();

        return this;
    }

    private void redraw() {
        //update timers
        time += 0.016;
        overlordTimer += 0.016;
        oneUpTimer += 0.16;

        //if music stops, start song again
        if(!level.getMusic().isAutoPlay()){
            level.getMusic().play();
        }

        if (level.isRoundOver() == false) {
            drawScore();
            drawLives(level.getLives());
            drawPlayerBullets();
            drawEnemyBullets();
            checkGameStatus();

            switch(level.getLevelNum()) {
            case 1:
                levelOneBehaviour();
                break;
            case 2:
                levelTwoBehaviour();
                break;
            case 3:
                levelThreeBehaviour();
                break;
            case 4:
                levelFourBehaviour();
                break;
            case 5:
                levelFiveBehaviour();
                break;
            }
        }
    }

    public void drawAliens() {
        int rows = level.getAlienRows();
        int totalAliens = level.getTotalAliens();
        int alienSpawnCount = totalAliens;
        ArrayList aliens = level.getAliens();

        for (int i = 0; i < totalAliens / rows; i++) {
            for (int j = 0; j < rows; j++) {
                Alien alien = (Alien) aliens.get(alienSpawnCount - 1);
                getChildren().add(alien);
                alienSpawnCount--;
            }
        }
    }

    //Start region: aliens movement
    public void moveAliensDown(){
        //aliens move down
        for (Alien alien : level.getAliens()) {
            alien.moveDown();

            //if enemy reaches the bottom
            if(alien.getTranslateY() == 720){
                playerLoses();
            }
        }
    }

    public void moveAliensLeftRight(){
        if(level.checkAlienPos() == 1){
            //alien at left most
            moveAliensRight();

        }
        else if(level.checkAlienPos() == 0){
           //alien at right most
            moveAliensLeft();
        }
        else {
            if(level.isAlienHitLeft()){
                moveAliensRight();
            }
            else if(level.isAlienHitRight()){
                moveAliensLeft();
            }
        }

    }

    public void moveAliensRight(){
        for (Alien alien: level.getAliens()) {
            alien.moveRight();
            if(alien.isHitRight()){
                level.setAllAliensHitRight(true);
                level.setAllAliensHitLeft(false);
            }
        }
    }

    public void moveAliensLeft(){
        for (Alien alien: level.getAliens()) {
            alien.moveLeft();
            if(alien.isHitLeft()){
                level.setAllAliensHitLeft(true);
                level.setAllAliensHitRight(false);
            }
        }
    }
    //End region: aliens movement

    public void drawPlayer(){
        Player player = level.getPlayer();
        getChildren().add(player);
    }

    //TODO: implement this?
    public void drawOverlord(){
        Overlord overlord = level.getOverlord();
        getChildren().add(overlord);
    }

    public void drawOneUp(){
        if(level.getOneUp() == null) {
            OneUp oneUp = new OneUp();
            level.setOneUp(oneUp);
            level.addElement(level.getOneUps(), oneUp);
            getChildren().add(level.getOneUp());
        }
    }

    public void checkOneUpStatus(){
        if(level.getOneUp() != null){
            OneUp oneUp = level.getOneUp();

            if(oneUp.getCenterY() == 750 - oneUp.getRadius()){
                getChildren().remove(oneUp);
                oneUp.setFallen(true);
            }
            else if (!oneUp.isFallen()){
                for (OneUp life : level.getOneUps()) {
                    if (life.getBoundsInParent().intersects(level.getPlayer().getBoundsInParent())) {
                        life.setFallen(true);
                        getChildren().remove(oneUp);
                        level.addLife();
                        drawLives(level.getLives());
                        level.getOneUpCollected().play();
                        break;
                    }
                }
            }
        }
    }

    public void drawScore(){
        int score = level.getScore();

        //draw score
        if (scoreText == null){
            scoreText = new Text("Score:" + score);
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.font("Courier New", 18));
            getChildren().add(scoreText);
            scoreText.setY(25);
            scoreText.setX(480);
        }
        else {
            getChildren().remove(scoreText);
            scoreText = null;
            drawScore();
        }
    }

    public void drawHighScore(){
        highScoreText = new Text ("High Score:" + highScore);
        highScoreText.setFill(Color.WHITE);
        highScoreText.setFont(Font.font("Courier New", 18));
        getChildren().add(highScoreText);
        highScoreText.setY(25);
        highScoreText.setX(205);

    }

    public void drawLevelNum(){
        int levelNum = level.getLevelNum();

        //draw level number
        levelNumText = new Text("Level " + levelNum);
        levelNumText.setFill(Color.WHITE);
        levelNumText.setFont(Font.font("Courier New", 18));
        getChildren().add(levelNumText);
        levelNumText.setY(25);
        levelNumText.setX(25);
    }

    public void drawLives(int remainingLives){
        //remove all lives
        getChildren().removeAll(level.getLifeArray());
        level.getLifeArray().removeAll(level.getLifeArray());

        //draw lives
        for (int i = 0; i < remainingLives; i++) {
            Life life = new Life(465 - (i * 25), 20, 10);
            getChildren().add(life);
            level.addElement(level.getLifeArray(), life);
        }
    }

    public void drawPlayerBullets(){
        for (PlayerBullet bullet : level.getPlayerBullets()) {
            bullet.moveUp();

            if(bullet.getDead()){
                deleteElement(level.getPlayerBullets(), bullet);
                break;
            }

            //check if bullet hits an Alien
            for(Alien alien: level.getAliens()){
                if(alien.getBoundsInParent().intersects(bullet.getBoundsInParent())){
                    level.getGunImpact().play();
                    bullet.setDead(true);
                    alien.setHp();
                    killDeadAliens();
                    break;
                }
            }

            //check if bullet hits overlord
            if(level.getOverlord() != null && level.getOverlord().getBoundsInParent().intersects(bullet.getBoundsInParent())){
                level.getGunImpact().play();
                bullet.setDead(true);
                level.getOverlord().setHp();
                if(level.getOverlord().getHp()  == 0) {
                    resetOverlord();
                    level.setScore(level.getScore() + 100);
                    level.getOverlordKilled().play();
                }
                break;
            }


        }
    }

    public void drawEnemyBullets(){
        for (EnemyBullet bullet : level.getEnemyBullets()) {
            bullet.moveDown();

            if (bullet.getDead()) {
                deleteElement(level.getEnemyBullets(), bullet);
                break;
            }

            if (bullet.getBoundsInParent().intersects(level.getPlayer().getBoundsInParent())) {
                level.getPlayerHitSound().play();
                bullet.setDead(true);
                level.setLives();
                if (level.getLives() > 0){
                    drawLives(level.getLives());
                    break;
                } else {
                    level.getPlayerKilledSound().play();
                    level.getPlayer().setDead(true);
                    break;
                }
            }

        }
    }

    public void playerShot(PlayerBullet bullet){
        level.addElement(level.getPlayerBullets(), bullet);
        getChildren().add(bullet);
    }

    public void aliensShoot(double freq){
        for (Alien alien: level.getAliens()) {
            if (Math.random() < freq) {
                EnemyBullet bullet = alien.shoot();
                level.getAlienGun().play();
                level.addElement(level.getEnemyBullets(), bullet);
                getChildren().add(bullet);
            }
        }
    }

    public void overlordShot(EnemyBullet fireball) {
        if (Math.random() < 0.09) {
            level.getFireball().play();
            level.addElement(level.getEnemyBullets(), fireball);
            getChildren().add(fireball);
        }
    }

    public <T> void deleteElement(ArrayList<T> list, Shape element){
        list.remove(element);
        getChildren().remove(element);
    }

    public void killDeadAliens(){
        for (Alien alien: level.getAliens()) {
            if (alien.getDead()){
                level.getAlienKilled().play();
                level.setScore(level.getScore() + 10);
                getChildren().remove(alien);
                level.removeElement(level.getAliens(), alien);
                break;
            }
        }
    }

    public void resetOverlord(){
        overlordTimer = 0;
        getChildren().remove(level.getOverlord());
        level.setOverlord(null);
    }

    public void playerWinsRound(){
        level.setRoundOver(true);
        level.getLevelCompleteSound().play();
        level.getMusic().stop();

        if(level.getLevelNum() == 5){
            if(level.getScore() > level.getHighScore()){
                level.setHighScore(level.getScore());
            }

            Win playerWins = new Win(stage, this);
            Scene scene = new Scene(playerWins.getPane(), 600, 750, Color.BLACK);
            stage.setScene(scene);
        } else {

            Level nextLevel = new Level(level.getLevelNum() + 1, level.getAlienRows() + 1, level.getLives(), level.getScore(), level.getHighScore());
            CombatView combatView = new CombatView(stage, nextLevel, level.getHighScore());
            Scene scene = new Scene(combatView.setContent(), Color.BLACK);

            scene.setOnKeyPressed(e1 -> {
                switch (e1.getCode()) {
                case A:
                    nextLevel.getPlayer().moveLeft();
                    break;
                case D:
                    nextLevel.getPlayer().moveRight();
                    break;
                case SPACE:
                    combatView.getLevel().getGunSound().play();
                    combatView.playerShot(nextLevel.getPlayer().shoot());
                    break;
                }
            });

            stage.setScene(scene);
        }


    }

    public void playerLoses(){
        boolean newHighScore = false;
        getChildren().remove(level.getPlayer());
        level.setRoundOver(true);
        level.getMusic().stop();

        if(level.getScore() > level.getHighScore()){
            level.setHighScore(level.getScore());
            newHighScore = true;
        }

        GameOver playerLost = new GameOver(stage, this, level.getHighScore(), newHighScore);
        Scene scene = new Scene(playerLost.getPane(), 600, 750, Color.BLACK);
        stage.setScene(scene);
    }

    public void checkGameStatus(){
        if(level.getPlayer().getDead() == true){
            playerLoses();
        }
        else {
            int remainingEnemies = level.getAliens().size();
            if(remainingEnemies == 0){
                playerWinsRound();
            }
        }
    }

    //start region: level behaviour
    public void levelOneBehaviour(){
        if (time > 1) {
            if (level.getOverlord() != null && level.getOverlord().isAttacking()) {
                level.getOverlord().attack();
            }
        }
        if (time > 2) {
            aliensShoot(0.005);
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
        }
        if (time > 3) {
            moveAliensDown();
            moveAliensLeftRight();
            time = 0;
        }
    }

    public void levelTwoBehaviour(){
        if (time > 1) {
            if (level.getOverlord() != null && level.getOverlord().isAttacking()) {
                level.getOverlord().attack();
            }
        }
        if (time > 2) {
            aliensShoot(0.005);
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
        }
        if (time > 3) {
            moveAliensDown();
            moveAliensLeftRight();
            time = 0;
        }

        //overlord Attack
        if (overlordTimer > 15) {
            if(level.getOverlord() == null) {
                //draw overlord
                Overlord overlord  = new Overlord(580, 65);
                level.setOverlord(overlord);
                getChildren().add(level.getOverlord());

                overlordTimer = 0;
            }
            else {
                if (level.getOverlord().isAttackComplete()){
                    resetOverlord();
                }
            }
        }


    }

    public void levelThreeBehaviour(){
        if (time > 1) {
            if (level.getOverlord() != null && level.getOverlord().isAttacking()) {
                level.getOverlord().attack();
            }
        }
        if (time > 2) {
            aliensShoot(0.006);
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
        }
        if (time > 3) {
            moveAliensLeftRight();
            moveAliensDown();
            time = 0;
        }

        //overlord Attack
        if (overlordTimer > 12) {
            if(level.getOverlord() == null) {
                //draw overlord
                Overlord overlord  = new Overlord(580, 65);
                level.setOverlord(overlord);
                getChildren().add(level.getOverlord());

                overlordTimer = 0;
            }
            else {
                if (level.getOverlord().isAttackComplete()){
                    resetOverlord();
                }
            }
        }
    }

    public void levelFourBehaviour(){
        if (time > 1) {
            if (level.getOverlord() != null && level.getOverlord().isAttacking()) {
                level.getOverlord().attack();
            }
            if(level.getOneUp() != null){
                level.getOneUp().moveDown();
            }
        }
        if (time > 2) {
            aliensShoot(0.006);
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
        }
        if (time > 3) {
            moveAliensLeftRight();
            moveAliensDown();
            time = 0;
        }

        //overlord Attack
        if (overlordTimer > 10) {
            if(level.getOverlord() == null) {
                //draw overlord
                Overlord overlord  = new Overlord(580, 65);
                level.setOverlord(overlord);
                getChildren().add(level.getOverlord());

                overlordTimer = 0;
            }
            else {
                if (level.getOverlord().isAttackComplete()){
                    resetOverlord();
                }
            }
        }

        //1up appears
        if(oneUpTimer > 200){
            drawOneUp();
            oneUpTimer = 0;
        }

        checkOneUpStatus();

        if(level.getOneUp() != null){
            level.getOneUp().moveDown();
        }

    }

    public void levelFiveBehaviour(){
        if (time > 1) {
            if (level.getOverlord() != null && level.getOverlord().isAttacking()) {
                level.getOverlord().attack();
            }
        }
        if (time > 2) {
            aliensShoot(0.007);
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
        }
        if (time > 3) {
            moveAliensDown();
            moveAliensDown();
            moveAliensLeftRight();
            if (level.getOverlord() != null) {
                overlordShot(level.getOverlord().shoot());
            }
            time = 0;
        }

        //overlord Attack
        if (overlordTimer > 7) {
            if(level.getOverlord() == null) {
                //draw overlord
                Overlord overlord  = new Overlord(580, 65);
                level.setOverlord(overlord);
                getChildren().add(level.getOverlord());

                overlordTimer = 0;
            }
            else {
                if (level.getOverlord().isAttackComplete()){
                    resetOverlord();
                }
            }
        }
    }
    //End region: level behaviour

    public Level getLevel() {
        return level;
    }



}
