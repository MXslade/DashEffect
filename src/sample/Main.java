package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Animation.ObstacleGeneration.ObstacleGeneration;
import sample.Model.Player;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double PLAYGROUND = 0.25;

    private static Stage stage;
    private Pane root;
    private Player player;

    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;

    public static final double LEFT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH;
    public static final double RIGHT_EDGE = (1 - PLAYGROUND) / 2 * WIDTH + WIDTH * PLAYGROUND;

    //Scenes
    private static Scene mainMenuScene;
    private static Scene gameScene;

    // UI
    private Timeline playerScoreTimer;
    private static int playerScore = 0;
    private Label textPlayerScore = new Label();

    //Media
    private static MediaPlayer backgroundMediaPlayer;

    private ImageView imageView;
    private ImageView imageView2;
    private ImageView tempImageView;

    //Timers
    private Timeline bgMovementTimer;
    private int bgSpeed = 1;
    private int bgSpeedWhenPlayerJumps = 4;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Dash Effect");
        createGameScene();

        createMainMenuScene();
        showMainMenuScene();

        createGameScene();

        stage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                playerScore+=2;
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

        createObstacles();

        createMedia();

        createBgMovement();

        createUserScore();
        root.getChildren().add(textPlayerScore);
        return root;
    }

    private void createMainMenuScene(){
        try {
            Parent menuRoot = FXMLLoader.load(getClass().getResource("testing.fxml"));
            mainMenuScene = new Scene(menuRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGameScene(){
        gameScene = new Scene(createRoot(), WIDTH, HEIGHT);
        playerScore = 0;
    }

    public static void showMainMenuScene(){
        stage.setScene(mainMenuScene);
        stage.show();
    }

    public static void showGameScene(){
        stage.setScene(gameScene);
        backgroundMediaPlayer.play();
        stage.show();
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
        player = new Player(root, PLAYER_HEIGHT, PLAYER_WIDTH);
    }

    private void createObstacles() {
        ObstacleGeneration obstacleGeneration = new ObstacleGeneration(root, player, 2);
        obstacleGeneration.play();
    }

    private void createMedia(){
        String backgroundAudioFileStr = "./Audio/Tetris Effect - The Deep_ Yours Forever - Theater Mode (192  kbps).mp3";
        Media backgroundMusic = new Media(new File(backgroundAudioFileStr).toURI().toString());

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
        bgMovementTimer.play();
    }

    private void createUserScore(){
        textPlayerScore.setFont(new Font("Eras Demi ITC", 32));
        textPlayerScore.setTextFill(Color.web("#1c1c26",1.0));
        textPlayerScore.setLayoutX(15);
        textPlayerScore.setLayoutY(20);
        playerScoreTimer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            textPlayerScore.setText( Integer.toString(++playerScore));
        }));
        playerScoreTimer.setCycleCount(Timeline.INDEFINITE);
        playerScoreTimer.play();
    }

    public static void setPlayerScore(int score){
        playerScore = score;
    }
}
