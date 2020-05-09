package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Model.Player;

import java.io.File;

public class Main extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double PLAYGROUND = 0.25;

    private Pane root;
    private Player player;

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;

    public static final double LEFT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH;
    public static final double RIGHT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH + WIDTH * PLAYGROUND;

    private Media backgroundMusic;
    private MediaPlayer backgroundMediaPlayer;

    private ImageView imageView;
    private ImageView imageView2;
    private ImageView tempImageView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dash Effect");
        primaryStage.setScene(new Scene(createRoot(), WIDTH, HEIGHT));
        primaryStage.show();
        backgroundMediaPlayer.play();


        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                player.jump();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Pane createRoot() {
        root = new Pane();

        //createBackground();
        createBackgroundImage();
        createPlayground();

        createPlayer();

        createMedia();

        createBgMovement();

        return root;
    }

//    private void createBackground() {
//        Rectangle rectangle = new Rectangle();
//        rectangle.setWidth(WIDTH);
//        rectangle.setHeight(HEIGHT);
//        rectangle.setFill(Color.BLUE);
//        root.getChildren().add(rectangle);
//    }

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
        player = new Player(root, PLAYER_HEIGHT, PLAYER_WIDTH);
    }

    private void createMedia(){
        String backgroundAudioFileStr = "./Audio/Tetris Effect - The Deep_ Yours Forever - Theater Mode (192  kbps).mp3";
        backgroundMusic = new Media(new File(backgroundAudioFileStr).toURI().toString());

        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void createBackgroundImage(){
        imageView = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));
        imageView2 = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));

        imageView.setX(0);
        imageView.setY(0);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);

        imageView2.setX(0);
        imageView2.setY(-1 * imageView.getBoundsInLocal().getHeight());
        imageView2.setPreserveRatio(true);

        imageView2.setFitWidth(WIDTH);
        root.getChildren().add(imageView2);
    }

    private void createBgMovement(){
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
            if(imageView.getY() > imageView.getBoundsInLocal().getHeight()){
                imageView.setY( -1 * imageView.getBoundsInLocal().getHeight());
                tempImageView = imageView;
                imageView = imageView2;
                imageView2 = tempImageView;
                return;

            }
            imageView.setY( imageView.getY() + (player.connectedWithWall() ? 1 : 2));
            imageView2.setY( imageView.getY() - imageView.getBoundsInLocal().getHeight());
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
}
