package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Dash Effect");
        primaryStage.setScene(new Scene(createRoot(), WIDTH, HEIGHT));
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
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
}
