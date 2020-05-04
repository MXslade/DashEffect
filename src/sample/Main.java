package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double PLAYGROUND = 0.25;

    private Pane root;

    private Rectangle player;
    private DoubleProperty xPlayer = new SimpleDoubleProperty((1 - PLAYGROUND) / 2 * WIDTH);
    private DoubleProperty yPlayer = new SimpleDoubleProperty(HEIGHT * 2.0 / 3);

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;

    public static final double LEFT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH;
    public static final double RIGHT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH + WIDTH * PLAYGROUND;

    private boolean left = true;
    private AnimationTimer jumpAnimation;


    Media jumpSoundEffect;
    Media backgroundMusic;
    MediaPlayer jumpMediaPlayer;
    MediaPlayer backgroundMediaPlayer;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Dash Effect");
        primaryStage.setScene(new Scene(createRoot(), WIDTH, HEIGHT));
        primaryStage.show();
        backgroundMediaPlayer.play();

        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                //play_jump_sound();
                jumpAnimation.start();
                left = !left;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Pane createRoot() {
        root = new Pane();

        createBackground();

        createPlayground();

        createPlayer();

        createAnimation();

        createMedia();

        return root;
    }

    private void createBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(WIDTH);
        rectangle.setHeight(HEIGHT);
        rectangle.setFill(Color.BLUE);
        root.getChildren().add(rectangle);
    }

    private void createPlayground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(WIDTH * PLAYGROUND);
        rectangle.setHeight(HEIGHT);
        rectangle.setY(0);
        rectangle.setX((1 - PLAYGROUND) / 2 * WIDTH);
        rectangle.setFill(Color.GRAY);
        root.getChildren().add(rectangle);
    }

    private void createPlayer() {
        player = new Rectangle();
        player.setHeight(PLAYER_HEIGHT);
        player.setWidth(PLAYER_WIDTH);
        player.xProperty().bind(xPlayer);
        player.yProperty().bind(yPlayer);
        root.getChildren().add(player);
    }

    private void createAnimation() {
        jumpAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (xPlayer.get() > RIGHT_EDGE - PLAYER_WIDTH) {
                    play_jump_sound();
                    xPlayer.set(RIGHT_EDGE - PLAYER_WIDTH);
                    System.out.println(xPlayer);
                    stop();
                }
                if (xPlayer.get() < LEFT_EDGE) {
                    xPlayer.set(LEFT_EDGE);
                    System.out.println(yPlayer);
                    stop();
                }
                double velocity = (left ? -10 : 10);
                xPlayer.set(xPlayer.get() + velocity);
            }
        };
    }

    private void createMedia(){
        String jumpAudioFileStr = "./Audio/Dash_Effect_Jump1.mp3";
        String backgroundAudioFileStr = "./Audio/Tetris Effect - The Deep_ Yours Forever - Theater Mode (192  kbps).mp3";


        jumpSoundEffect = new Media(new File(jumpAudioFileStr).toURI().toString());
        backgroundMusic = new Media(new File(backgroundAudioFileStr).toURI().toString());

        //jumpMediaPlayer = new MediaPlayer(jumpSoundEffect);
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void play_jump_sound(){
        jumpMediaPlayer = new MediaPlayer(jumpSoundEffect);
        jumpMediaPlayer.play();
    }

    /*
    import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


    String musicFile = "StayTheNight.mp3";     // For example

Media sound = new Media(new File(musicFile).toURI().toString());
MediaPlayer mediaPlayer = new MediaPlayer(sound);
mediaPlayer.play();
 */
}
