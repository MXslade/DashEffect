package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Animation.JumpAnimation;

import java.io.File;

public class Main extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double PLAYGROUND = 0.25;

    private Pane root;

    private Rectangle player;

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;

    public static final double LEFT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH;
    public static final double RIGHT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH + WIDTH * PLAYGROUND;

    private boolean left = true;
    private JumpAnimation jumpToLeft;
    private JumpAnimation jumpToRight;

    Media jumpSoundEffect;
    Media backgroundMusic;
    MediaPlayer jumpMediaPlayer;
    MediaPlayer backgroundMediaPlayer;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dash Effect");
        primaryStage.setScene(new Scene(createRoot(), WIDTH, HEIGHT));
        primaryStage.show();
        backgroundMediaPlayer.play();

        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                playJumpSound();
                if (left) {
                    jumpToRight.play();
                } else {
                    jumpToLeft.play();
                }
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
        player.setY(HEIGHT * 2.0 / 3);
        root.getChildren().add(player);
    }

    private void createAnimation() {
        jumpToRight = new JumpAnimation(player, RIGHT_EDGE - PLAYER_WIDTH);
        jumpToLeft = new JumpAnimation(player, LEFT_EDGE);
    }

    private void createMedia(){
        String jumpAudioFileStr = "./Audio/Dash_Effect_Jump1.mp3";
        String backgroundAudioFileStr = "./Audio/Tetris Effect - The Deep_ Yours Forever - Theater Mode (192  kbps).mp3";


        jumpSoundEffect = new Media(new File(jumpAudioFileStr).toURI().toString());
        backgroundMusic = new Media(new File(backgroundAudioFileStr).toURI().toString());

        jumpMediaPlayer = new MediaPlayer(jumpSoundEffect);
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void playJumpSound(){
        jumpMediaPlayer.seek(jumpMediaPlayer.getStartTime());
        jumpMediaPlayer.play();
    }
}
