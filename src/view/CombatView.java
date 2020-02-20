package view;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-22
 */

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HighScoreDatabase;
import model.Level;
import model.bullets.EnemyBullet;
import model.bullets.Explosion;
import model.bullets.Missile;
import model.bullets.PlayerBullet;
import model.characters.Alien;
import model.characters.Overlord;
import model.characters.Player;
import model.collectibles.Life;
import model.collectibles.MissileIcon;
import model.collectibles.OneUp;
import model.collectibles.PickUp;
import model.collectibles.PowerUp;

class CombatView extends Pane {
    private final Stage stage;
    private Level level;
    private int highScore;
    //timers
    private double time = 0;
    private double overlordTimer = 0;
    private double pickUpTimer = 0;
    private double explosionTimer = 0;
    //JavaFx Components
    private Text scoreText;
    private Text levelNumText;
    private Text highScoreText;
    //database
    private HighScoreDatabase DB;
    //animation timer
    private AnimationTimer timer;

    public CombatView(Stage stage, Level level, int highScore, HighScoreDatabase DB) {
        this.stage = stage;
        this.level = level;
        this.highScore = highScore;
        this.DB = DB;
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
        drawMissileIcons(level.getPlayer().getMissileCount());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                redraw();
            }
        };
        timer.start();

        return this;
    }

    private void runTimers(){
        time += 0.016;
        overlordTimer += 0.016;
        pickUpTimer += 0.16;
        explosionTimer += 0.16;
    }

    private void redraw() {
        //update timers
        runTimers();

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

            if(explosionTimer > 1) {
                eraseExplosions();
            }

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

    public double findLowestAlien(){
        double lowestPoint = level.getAliens().get(0).getY();

        for (Alien alien: level.getAliens()) {
            if (alien.getTranslateY() > lowestPoint){
                lowestPoint = alien.getTranslateY();
            }
        }
        System.out.println(lowestPoint);
        return lowestPoint;
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

    //Region start: pickups
    public void dropPickUp(int chance){
        Random rand = new Random();
        int num = rand.nextInt(100);

        //drop rate
        if(num < chance){
            //Choose a random pickup
            PickUp pickUp = level.getPickUps().get(rand.nextInt(level.getPickUps().size()));

            if(pickUp instanceof PowerUp){
                level.addElement(level.getFallingPickUps(), pickUp);
                getChildren().add(pickUp);
            }
            else if (pickUp instanceof OneUp){
                level.addElement(level.getFallingPickUps(), pickUp);
                getChildren().add(pickUp);
            }

        }

    }

    public void checkFallingPickUps() {
        for (PickUp pickUp : level.getFallingPickUps()) {
            pickUp.moveDown();

            //check if pick up has hit the bottom
            if (pickUp.getCenterY() == 750 - pickUp.getRadius()) {
                deleteElement(level.getFallingPickUps(), pickUp);
                pickUp.setCenterY(0);
                break;
            } else if (!pickUp.isFallen()) {
                if (pickUp.getBoundsInParent().intersects(level.getPlayer().getBoundsInParent())) {
                    deleteElement(level.getFallingPickUps(), pickUp);

                    if (pickUp instanceof OneUp) {
                        level.addLife();
                        drawLives(level.getLives());
                        level.getOneUpCollected().play();
                        pickUp.setCenterY(0);
                        break;
                    } else if (pickUp instanceof PowerUp) {
                        getChildren().remove(pickUp);
                        level.getPlayer().setPoweredUp(true);
                        level.getPlayer().setImage(
                                "/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/playerPowered.png");
                        level.getPlayer().setStrokeWidth(3);
                        pickUp.setCenterY(0);

                        break;
                    }
//                    deleteElement(level.getFallingPickUps(), pickUp);
                }
            }
        }

    }
    //Region end: pickups

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
        highScoreText.setX(210);

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
            Life life = new Life(458 - (i * 20), 10,20, 20);
            getChildren().add(life);
            level.addElement(level.getLifeArray(), life);
        }
    }

    public void drawMissileIcons(int missileCount){
        //draw remaining missiles
        for (int i = 0; i < missileCount; i++) {
            MissileIcon missileIcon = new MissileIcon( 180 - (i * 15),10);
            getChildren().add(missileIcon);
            level.addElement(level.getMissileIcons(), missileIcon);
        }
    }

    public void checkExplosionDamageOnAliens(Explosion explosion) {
        for (Alien alien : level.getAliens()) {
            //missile hits alien
            if (alien.getBoundsInParent().intersects(explosion.getBoundsInParent())) {
                alien.setHp(4);
            }
        }

        explosionTimer = 0;
        explosion.setDead(true);

    }

    public void drawPlayerBullets(){
        for (PlayerBullet bullet : level.getPlayerBullets()) {
            bullet.moveUp();

            if(bullet.getTranslateY() <= 0){
                bullet.setDead(true);
            }

            if(bullet.getDead()){
                deleteElement(level.getPlayerBullets(), bullet);
                break;
            }

            //Missile
            if(bullet instanceof Missile) {
                for (Alien alien : level.getAliens()) {
                    //missile hits alien
                    if (alien.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                        alien.setHp(1);

                        //draw explosion
                        Explosion explosion = new Explosion(bullet.getTranslateX() - 50, bullet.getTranslateY() - 40);
                        getChildren().add(explosion);
                        level.addElement(level.getExplosions(), explosion);

                        //damage
                        bullet.setDead(true);
                        level.getExplosion().play();
                        checkExplosionDamageOnAliens(explosion);
                        killDeadAliens();
                        break;
                    }
                }

                //check if missile hits overlord
                if (level.getOverlord() != null && level.getOverlord().getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    //draw explosion
                    Explosion explosion = new Explosion(bullet.getTranslateX() - 50, bullet.getTranslateY() - 45);
                    getChildren().add(explosion);
                    level.addElement(level.getExplosions(), explosion);

                    bullet.setDead(true);
                    explosion.setDead(true);
                    explosionTimer = 0;
                    level.getExplosion().play();
                    level.getOverlord().setHp(2);

                    //kill overlord and add score
                    if (level.getOverlord().getHp() == 0) {
                        resetOverlord();
                        level.setScore(level.getScore() + 100);
                        level.getOverlordKilled().play();
                    }
                    break;
                }

            }
            //Normal player bullet
            else {
                //check if bullet hits an Alien
                for (Alien alien : level.getAliens()) {
                    if (alien.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                        level.getGunImpact().play();
                        bullet.setDead(true);
                        alien.setHp(1);
                        killDeadAliens();
                        break;
                    }
                }

                //check if bullet hits overlord
                if (level.getOverlord() != null && level.getOverlord().getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    level.getGunImpact().play();
                    bullet.setDead(true);
                    level.getOverlord().setHp(1);
                    if (level.getOverlord().getHp() == 0) {
                        resetOverlord();
                        level.setScore(level.getScore() + 100);
                        level.getOverlordKilled().play();
                    }
                    break;
                }
            }
        }
    }

    public void drawEnemyBullets(){
        for (EnemyBullet bullet : level.getEnemyBullets()) {
            bullet.moveDown();

            if(bullet.getTranslateY() >= 750){
                bullet.setDead(true);
            }

            if (bullet.getDead()) {
                deleteElement(level.getEnemyBullets(), bullet);
                break;
            }

            if (bullet.getBoundsInParent().intersects(level.getPlayer().getBoundsInParent())) {
                level.getPlayerHitSound().play();
                bullet.setDead(true);
                level.decreaseLives();

                if(level.getPlayer().isPoweredUp()){
                    level.getPlayer().setPoweredUp(false);
                    level.getPlayer().setImage("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/player.png");
                }

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

    public void playerShot(ArrayList<PlayerBullet> bullets){
        for (PlayerBullet bullet: bullets) {
            level.addElement(level.getPlayerBullets(), bullet);
            getChildren().add(bullet);
        }
    }

    public void playerShotMissile(Missile missile){
        level.addElement(level.getPlayerBullets(), missile);
        getChildren().add(missile);
        level.getPlayer().decreaseMissileCount();

        for(MissileIcon missileIcon : level.getMissileIcons()){
            getChildren().remove(missileIcon);
        }
        drawMissileIcons(level.getPlayer().getMissileCount());
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

    public void eraseExplosions(){
        for(Explosion explosion : level.getExplosions()){
            if(explosion.isDead()){
                getChildren().remove(explosion);
            }
        }
    }

    public void playerWinsRound(){
        boolean newHighScore = false;
        level.setRoundOver(true);
        level.getLevelCompleteSound().play();
        level.getMusic().stop();

        //player won the GAME
        if(level.getLevelNum() == 5){
            //new high score
            if(level.getScore() > level.getHighScore()){
                DB.newHighScore(level.getScore());
                level.setHighScore(level.getScore());
                newHighScore = true;
            }

            Win playerWins = new Win(stage, this, level.getHighScore(), newHighScore, DB);
            Scene scene = new Scene(playerWins.getPane(), 600, 750, Color.BLACK);
            stage.setScene(scene);
        }
        //Player won the ROUND
        else {
            Level nextLevel = new Level(level.getPlayer(), level.getLevelNum() + 1, level.getAlienRows() + 1, level.getLives(), level.getScore(), level.getHighScore());
            CombatView combatView = new CombatView(stage, nextLevel, level.getHighScore(), DB);
            Scene scene = new Scene(combatView.setContent(), level.getBackground());

            scene.setOnKeyPressed(e1 -> {
                switch (e1.getCode()) {
                case A:
                    nextLevel.getPlayer().moveLeft();
                    break;
                case D:
                    nextLevel.getPlayer().moveRight();
                    break;
                case M:
                    if(level.getPlayer().getMissileCount() > 0) {
                        combatView.playerShotMissile(level.getPlayer().shootMissile());
                        combatView.getLevel().getMissile().play();
                    }
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
            DB.newHighScore(level.getScore());
            level.setHighScore(level.getScore());
            newHighScore = true;
        }

        GameOver playerLost = new GameOver(stage, this, level.getHighScore(), newHighScore, DB);
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

        //power up appears
        if(pickUpTimer > 100){
            if(findLowestAlien() < 400) {
                dropPickUp(50);
            }
            pickUpTimer = 0;
        }
        checkFallingPickUps();

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

        //power up appears
        if(pickUpTimer > 125){
            if(findLowestAlien() < 400) {
                dropPickUp(45);
            }
            pickUpTimer = 0;
        }
        checkFallingPickUps();

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

        //pick-up up appears
        if(pickUpTimer > 150){
            if(findLowestAlien() < 400) {
                dropPickUp(33);
            }
            pickUpTimer = 0;
        }
        checkFallingPickUps();

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

        //pick-up up appears
        if(pickUpTimer > 175){
            if(findLowestAlien() < 400) {
                dropPickUp(15);
            }
            pickUpTimer = 0;
        }
        checkFallingPickUps();

    }
    //End region: level behaviour

    public Level getLevel() {
        return level;
    }

}
