package sample.SceneControl;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Animation.ObstacleGeneration.ObstacleGeneration;
import sample.Main;
import sample.Model.Player;

import java.io.File;

public class GameScene {

    private Stage stage;
    private Scene scene;
    private SceneController sceneController;

    private Player player;
    private ObstacleGeneration obstacleGeneration;

    private Pane root;

    private ImageView imageView;
    private ImageView imageView2;
    private ImageView tempImageView;

    private MediaPlayer backgroundMediaPlayer;
    private Timeline bgMovementTimer;
    private int bgSpeed = 1;
    private int bgSpeedWhenPlayerJumps = 4;

    private Timeline playerScoreTimer;
    private int playerScore = 0;

    boolean isPaused;

    public GameScene(Stage stage, SceneController sceneController) {
        this.stage = stage;
        this.sceneController = sceneController;
        scene = new Scene(createRoot(), Main.WIDTH, Main.HEIGHT);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE && !isPaused) {
                player.jump();
            } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                if(isPaused){
                    sceneController.startGame();
                    this.play();
                }else{
                    sceneController.showMainMenu();
                    this.pause();
                }
            }
        });
    }

    public Scene getScene() {
        return scene;
    }

    public void play() {
        isPaused = false;
        player.play();
        obstacleGeneration.play();
        bgMovementTimer.play();
        playerScoreTimer.play();
        backgroundMediaPlayer.play();
    }

    public void pause(){
        isPaused = true;
        player.pause();
        obstacleGeneration.pause();
        bgMovementTimer.pause();
        playerScoreTimer.pause();
        backgroundMediaPlayer.pause();
    }

    private Pane createRoot() {
        root = new Pane();

        createBackgroundImage();

        createPlayground();

        createPlayer();

        createObstacles();

        createMedia();

        createBgMovement();

        createUserScore();

        return root;
    }

    private void createBackgroundImage(){
        imageView = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));
        imageView2 = new ImageView(new Image(new File("./Media/Background_v2.jpg").toURI().toString()));

        imageView.setX(0);
        imageView.setY(0);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(Main.WIDTH);
        root.getChildren().add(imageView);

        imageView2.setX(0);
        imageView2.setY(-1 * imageView.getBoundsInLocal().getHeight());
        imageView2.setPreserveRatio(true);

        imageView2.setFitWidth(Main.WIDTH);
        root.getChildren().add(imageView2);
    }

    private void createPlayground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(Main.WIDTH * Main.PLAYGROUND);
        rectangle.setHeight(Main.HEIGHT);
        rectangle.setY(0);
        rectangle.setX((1 - Main.PLAYGROUND) / 2 * Main.WIDTH);
        rectangle.setFill(Color.GRAY);
        root.getChildren().add(rectangle);
    }

    private void createPlayer() {
        player = new Player(root, Main.PLAYER_HEIGHT, Main.PLAYER_WIDTH);
    }

    private void createObstacles() {
        obstacleGeneration = new ObstacleGeneration(root, player, 2);
        //obstacleGeneration.play();
    }

    private void createMedia(){
        String backgroundAudioFileStr = "./Audio/Tetris Effect - The Deep_ Yours Forever - Theater Mode (192  kbps).mp3";
        Media backgroundMusic = new Media(new File(backgroundAudioFileStr).toURI().toString());

        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void createBgMovement(){
        bgMovementTimer = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
            if(imageView.getY() > imageView.getBoundsInLocal().getHeight()){
                imageView.setY( -1 * imageView.getBoundsInLocal().getHeight());
                tempImageView = imageView;
                imageView = imageView2;
                imageView2 = tempImageView;
                return;
            }
            imageView.setY( imageView.getY() + (player.connectedWithWall() ? bgSpeed : bgSpeedWhenPlayerJumps));
            imageView2.setY( imageView.getY() - imageView.getBoundsInLocal().getHeight());
        }));
        bgMovementTimer.setCycleCount(Timeline.INDEFINITE);
        //bgMovementTimer.play();
    }

    private void createUserScore(){
        Label textPlayerScore = new Label();
        textPlayerScore.setFont(new Font("Eras Demi ITC", 32));
        textPlayerScore.setTextFill(Color.web("#1c1c26",1.0));
        textPlayerScore.setLayoutX(15);
        textPlayerScore.setLayoutY(20);
        playerScoreTimer = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        event -> textPlayerScore.setText( Integer.toString(++playerScore))));
        playerScoreTimer.setCycleCount(Timeline.INDEFINITE);
        //playerScoreTimer.play();
        root.getChildren().add(textPlayerScore);
    }
}
