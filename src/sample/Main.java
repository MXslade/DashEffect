package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
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


    ImageView imageView;
    ImageView imageView2;
    ImageView tempImageView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dash Effect");
        primaryStage.setScene(new Scene(createRoot(), WIDTH, HEIGHT));
        primaryStage.show();
        backgroundMediaPlayer.play();


        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                if( !connectedWithWall() ){
                    return;
                }
                moveBG();
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

    private boolean connectedWithWall(){
        return ( player.getTranslateX() == 0.0 || player.getTranslateX() == 480.0 || player.getTranslateX() == 750.0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Pane createRoot() {
        root = new Pane();

        createBackground();
        createBackgroundImage();
        createPlayground();

        createPlayer();

        createAnimation();

        createMedia();

        createBgMovement();


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

    private void createBackgroundImage(){
        imageView = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));
        imageView2 = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));

        imageView.setX(0);
        imageView.setY(0);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(1280);
        root.getChildren().add(imageView);

        imageView2.setX(0);
        imageView2.setY(-1 * imageView.getBoundsInLocal().getHeight());
        imageView2.setPreserveRatio(true);

        imageView2.setFitWidth(1280);
        root.getChildren().add(imageView2);
    }

    private void moveBG(){
        imageView.setY( imageView.getY() + 3);
    }

    private void createBgMovement(){
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(imageView.getY() > imageView.getBoundsInLocal().getHeight()){
                    imageView.setY( -1 * imageView.getBoundsInLocal().getHeight());
                    tempImageView = imageView;
                    imageView = imageView2;
                    imageView2 = tempImageView;
                    return;

                }
                imageView.setY( imageView.getY() + 1);
                imageView2.setY( imageView.getY() - imageView.getBoundsInLocal().getHeight());
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

}
