package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import model.bullets.EnemyBullet;
import model.bullets.Explosion;
import model.bullets.PlayerBullet;
import model.characters.Alien;
import model.characters.Overlord;
import model.characters.Player;
import model.collectibles.Life;
import model.collectibles.MissileIcon;
import model.collectibles.OneUp;
import model.collectibles.PowerUp;

/**
 * @author David Manolitsas
 * @project SpaceInvaders
 * @date 2020-01-22
 */
public class Level {

    //level stats
    private int levelNum;
    private final static int ALIENS_PER_ROW = 8;
    private int alienRows;
    private int totalAliens;
    private int lives;
    private int score;
    private int highScore;
    private boolean roundOver;
    //characters
    private ArrayList<Alien> aliens = new ArrayList<>();
    private Player player = new Player(300, 700);
    private Overlord overlord;
    //collectibles
    private OneUp oneUp = null;
    private PowerUp powerUp = null;
    private ArrayList<OneUp> oneUps = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    //player stats
    private ArrayList<Life> lifeArray = new ArrayList<>();
    private ArrayList<MissileIcon> missileIcons = new ArrayList<>();
    //bullets & explosions
    private ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    //sound effects
    private  final static String SOUND_ROOT = "/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/";
    private AudioClip gunSound;
    private AudioClip gunImpact;
    private AudioClip fireball;
    private AudioClip alienGun;
    private AudioClip alienKilled;
    private AudioClip overlordKilled;
    private AudioClip playerHitSound;
    private AudioClip playerKilledSound;
    private AudioClip levelCompleteSound;
    private AudioClip oneUpCollected;
    private AudioClip explosion;
    private AudioClip missile;
    //music
    private MediaPlayer music;

    public Level(int num, int enemyRows, int lives, int score, int highScore) {
        this.levelNum = num;
        this.alienRows = enemyRows;
        this.totalAliens = ALIENS_PER_ROW * alienRows;
        this.lives = lives;
        this.score = score;
        this.roundOver = false;
        addAliens();
        composeSoundEffects();
        setMusic();
    }

    public Level(Player player, int num, int enemyRows, int lives, int score, int highScore) {
        this.player = player;
        this.levelNum = num;
        this.alienRows = enemyRows;
        this.totalAliens = ALIENS_PER_ROW * alienRows;
        this.lives = lives;
        this.score = score;
        this.roundOver = false;
        addAliens();
        composeSoundEffects();
        setMusic();
    }

    public int getLevelNum() {
        return levelNum;
    }

    public MediaPlayer getMusic() {
        return music;
    }

    public void setMusic(MediaPlayer music) {
        this.music = music;
    }

    public int getAlienRows() {
        return alienRows;
    }

    public int getTotalAliens() {
        return totalAliens;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public void addLife(){
        this.lives++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public boolean isRoundOver() {
        return roundOver;
    }

    public void setRoundOver(boolean roundOver) {
        this.roundOver = roundOver;
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public ArrayList<PlayerBullet> getPlayerBullets() {
        return playerBullets;
    }

    public ArrayList<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public Player getPlayer() {
        return player;
    }

    public Overlord getOverlord() {
        return overlord;
    }

    public void setOverlord(Overlord overlord) {
        this.overlord = overlord;
    }

    public OneUp getOneUp() {
        return oneUp;
    }

    public void setOneUp(OneUp oneUp) {
        this.oneUp = oneUp;
    }

    public ArrayList<OneUp> getOneUps() {
        return oneUps;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public ArrayList<Life> getLifeArray() {
        return lifeArray;
    }

    public ArrayList<MissileIcon> getMissileIcons() {
        return missileIcons;
    }

    public <T> void addElement(ArrayList<T> list, Shape element){
        list.add((T) element);
    }

    public <T> void removeElement(ArrayList<T> list, Shape element){
        list.remove(element);
    }

    public void addAliens(){
        for (int i = 0; i < totalAliens / alienRows; i++) {
            for (int j = 0; j < alienRows; j++) {
                Alien alien = new Alien( 60 + i * 65, 45 * (j + 1));
                addElement(aliens, alien);
            }
        }
    }

    //Region start: alien movement
    public int checkAlienPos(){
        //return 1 if aliens at left most, 0 if aliens at right most, -1 if neither
        for (Alien alien : aliens) {
            if(alien.getTranslateX() == 0){
                return 1;
            }
            else if(alien.getTranslateX() == 570){
                return 0;
            }
            else
                return -1;
        }
        return -1;
    }

    public boolean isAlienHitLeft(){
        for (Alien alien: aliens) {
            if(alien.isHitLeft()){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean isAlienHitRight(){
        for (Alien alien: aliens) {
            if(alien.isHitRight()){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public void setAllAliensHitRight(boolean isHit){
        for (Alien alien: aliens) {
            alien.setHitRight(isHit);
        }
    }

    public void setAllAliensHitLeft(boolean isHit){
        for (Alien alien: aliens) {
            alien.setHitLeft(isHit);
        }
    }
    //Region end: alien movement

    //Region Start: sound effects
    public AudioClip getGunSound() {
        return gunSound;
    }

    public AudioClip getGunImpact() {
        return gunImpact;
    }

    public AudioClip getFireball() {
        return fireball;
    }

    public AudioClip getAlienGun() {
        return alienGun;
    }

    public AudioClip getAlienKilled() {
        return alienKilled;
    }

    public AudioClip getOverlordKilled() {
        return overlordKilled;
    }

    public AudioClip getPlayerHitSound() {
        return playerHitSound;
    }

    public AudioClip getPlayerKilledSound() {
        return playerKilledSound;
    }

    public AudioClip getLevelCompleteSound() {
        return levelCompleteSound;
    }

    public AudioClip getOneUpCollected() {
        return oneUpCollected;
    }

    public AudioClip getExplosion() {
        return explosion;
    }

    public AudioClip getMissile() {
        return missile;
    }

    public void composeSoundEffects(){
        //bullet hit alien
        String gunImpactPath =  SOUND_ROOT + "sound/effects/gunImpact.wav";
        gunImpact = new AudioClip(new File(gunImpactPath).toURI().toString());
        gunImpact.setVolume(0.5);

        //overlord shoot
        String fireballPath =  SOUND_ROOT + "sound/effects/fireball.wav";
        fireball = new AudioClip(new File(fireballPath).toURI().toString());
        fireball.setVolume(0.8);

        //aliens shoot
        String alienGunPath =  SOUND_ROOT + "sound/effects/alienGun.wav";
        alienGun = new AudioClip(new File(alienGunPath).toURI().toString());
        alienGun.setVolume(0.25);

        //gun sound
        String gunSoundPath =  SOUND_ROOT + "sound/effects/gun.wav";
        gunSound = new AudioClip(new File(gunSoundPath).toURI().toString());
        gunSound.setVolume(0.3);

        //alien killed
        String alienKilledPath =  SOUND_ROOT + "sound/effects/alienKilled.wav";
        alienKilled = new AudioClip(new File(alienKilledPath).toURI().toString());

        //overlord killed
        String overlordKilledPath = SOUND_ROOT + "sound/effects/overlordKilled.wav";
        overlordKilled = new AudioClip(new File(overlordKilledPath).toURI().toString());

        //player hit
        String playerHitSoundPath = SOUND_ROOT + "sound/effects/playerHit.wav";
        playerHitSound = new AudioClip(new File(playerHitSoundPath).toURI().toString());
        playerHitSound.setVolume(0.75);

        //player killed
        String playerKilledSoundPath = SOUND_ROOT + "sound/effects/playerKilled.wav";
        playerKilledSound = new AudioClip(new File(playerKilledSoundPath).toURI().toString());

        //level complete
        String levelCompleteSoundPath = SOUND_ROOT + "sound/effects/levelComplete.wav";
        levelCompleteSound = new AudioClip(new File(levelCompleteSoundPath).toURI().toString());
        levelCompleteSound.setVolume(0.60);

        //1up Collected
        String oneUpPath = SOUND_ROOT + "sound/effects/1up.wav";
        oneUpCollected = new AudioClip(new File(oneUpPath).toURI().toString());
        oneUpCollected.setVolume(0.85);

        //missile shot
        String missileShotPath = SOUND_ROOT + "sound/effects/missile.wav";
        missile = new AudioClip(new File(missileShotPath).toURI().toString());

        //explosion
        String explosionPath = SOUND_ROOT + "sound/effects/explosion.wav";
        explosion = new AudioClip(new File(explosionPath).toURI().toString());

    }

    public void setMusic(){
        switch(levelNum){
        case 1:
            String levelOneMusicPath = SOUND_ROOT + "sound/music/01_Combat.mp3";
            Media levelOneMedia = new Media(new File(levelOneMusicPath).toURI().toString());
            MediaPlayer levelOneMusic = new MediaPlayer(levelOneMedia);
            levelOneMusic.setVolume(0.8);
            levelOneMusic.setAutoPlay(true);

            setMusic(levelOneMusic);
            break;
            case 2:
                String levelTwoMusicPath = SOUND_ROOT + "sound/music/02_Funk.mp3";
                Media levelTwoMedia = new Media(new File(levelTwoMusicPath).toURI().toString());
                MediaPlayer levelTwoMusic = new MediaPlayer(levelTwoMedia);
                levelTwoMusic.setVolume(0.8);
                levelTwoMusic.setAutoPlay(true);

                setMusic(levelTwoMusic);
                break;
        case 3:
            String levelThreeMusicPath = SOUND_ROOT + "sound/music/03_TheOriginal.mp3";
            Media levelThreeMedia = new Media(new File(levelThreeMusicPath).toURI().toString());
            MediaPlayer levelThreeMusic = new MediaPlayer(levelThreeMedia);
            levelThreeMusic.setVolume(0.8);
            levelThreeMusic.setAutoPlay(true);

            setMusic(levelThreeMusic);
            break;
        case 4:
            String levelFourMusicPath = SOUND_ROOT + "sound/music/04_Destiny.mp3";
            Media levelFourMedia = new Media(new File(levelFourMusicPath).toURI().toString());
            MediaPlayer levelFourMusic = new MediaPlayer(levelFourMedia);
            levelFourMusic.setVolume(0.8);
            levelFourMusic.setAutoPlay(true);

            setMusic(levelFourMusic);
            break;
        case 5:
            String levelFiveMusicPath = SOUND_ROOT + "sound/music/05_BossBattle.mp3";
            Media levelFiveMedia = new Media(new File(levelFiveMusicPath).toURI().toString());
            MediaPlayer levelFiveMusic = new MediaPlayer(levelFiveMedia);
            levelFiveMusic.setVolume(0.8);
            levelFiveMusic.setAutoPlay(true);

            setMusic(levelFiveMusic);
            break;
        }

    }
    //Region end: Sound effects


    public ImagePattern getBackground(){
        try {
            FileInputStream input = new FileInputStream("/Users/david/Develop/IntelliJ-Workspace/SquareInvaders/pictures/background.png");
            Image image = new Image(input);
            ImagePattern imagePattern = new ImagePattern(image);
            return imagePattern;
        } catch (FileNotFoundException fnfe){

        }
        return null;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
