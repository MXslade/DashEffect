package sample.Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Particle {
    private Rectangle rectangle;
    private Timeline expandingAnimation;
    private Timeline movementAnimation;
    private RotateTransition rotationAnimation;
    private double initialSize = 1;
    private double finalSize = 20;
    private double xSpeed;
    private double ySpeed;
    private double rotationSpeed;

    private Pane root;
    private Player player;

    public Particle(Pane root, Player player) {
        this.root = root;
        this.player = player;
        createVariables();
        createRectangle();
        createAnimation();
    }

    public void play() {
        expandingAnimation.play();
        movementAnimation.play();
        rotationAnimation.play();
    }

    private void createVariables() {
        Random random = new Random();
        xSpeed = random.nextDouble();
        ySpeed = 1;
        rotationSpeed = 360;
        System.out.println(xSpeed);
    }

    private void createRectangle() {
        rectangle = new Rectangle();
        rectangle.setWidth(initialSize);
        rectangle.setHeight(initialSize);
        root.getChildren().add(rectangle);
    }

    private void createAnimation() {
        expandingAnimation = new Timeline(new KeyFrame(Duration.seconds(0.01), actionEvent -> {
            rectangle.setHeight(rectangle.getHeight() + 0.2);
            rectangle.setWidth(rectangle.getWidth() + 0.2);
            if (rectangle.getHeight() >= finalSize) {
                rectangle.setHeight(initialSize);
                rectangle.setWidth(initialSize);
                rectangle.setTranslateY(player.getRectangle().getTranslateY() + player.getRectangle().getWidth());
                if (!player.connectedWithWall()) {
                    return;
                }
                if (player.connectedWithLeftWall()) {
                    rectangle.setTranslateX(player.getRectangle().getTranslateX());
                } else {
                    rectangle.setTranslateX(player.getRectangle().getTranslateX() + player.getRectangle().getWidth());
                }
            }
        }));

        expandingAnimation.setCycleCount(Animation.INDEFINITE);
        movementAnimation = new Timeline(new KeyFrame(Duration.seconds(0.01), actionEvent -> {
            if (!player.connectedWithWall()) {
                return;
            }
            xSpeed = Math.abs(xSpeed);
            rotationSpeed = Math.abs(ySpeed);
            if (player.connectedWithRightWall()) {
                xSpeed = -1 * xSpeed;
                rotationSpeed = -1 * rotationSpeed;
            }
            rectangle.setTranslateX(rectangle.getTranslateX() + xSpeed);
            rectangle.setTranslateY(rectangle.getTranslateY() + ySpeed);
        }));
        movementAnimation.setCycleCount(Animation.INDEFINITE);

        rotationAnimation = new RotateTransition();
        rotationAnimation.setNode(rectangle);
        rotationAnimation.setByAngle(rotationSpeed);
        rotationAnimation.setDuration(Duration.seconds(2));
        rotationAnimation.setCycleCount(Animation.INDEFINITE);
    }
}
