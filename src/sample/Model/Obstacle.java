package sample.Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import sample.Main;

public class Obstacle {

    private Pane root;
    private Player player;

    private Polygon polygon;
    private double size = 60;

    private boolean left;

    private Timeline movementAnimation;

    public Obstacle(Pane root, Player player, boolean left) {
        this.root = root;
        this.player = player;
        this.left = left;
        createPolygon();
        createAnimation();
    }

    public void setTranslateValues(double translateX, double translateY) {
        polygon.setTranslateX(translateX);
        polygon.setTranslateY(translateY);
    }

    public void play() {
        movementAnimation.play();
    }

    private void createPolygon() {
        polygon = new Polygon();
        polygon.getPoints().addAll(0.0, 0.0,
                size, size / 2,
                00.0, size);
        root.getChildren().add(polygon);
    }

    private void createAnimation() {
        movementAnimation = new Timeline(new KeyFrame(Duration.seconds(0.01), actionEvent -> {
            polygon.setTranslateY(polygon.getTranslateY() + 1);
            if (polygon.getTranslateY() > Main.HEIGHT) {
                polygon.setTranslateY(0);
            }
            if (checkForCollision()) {
                System.out.println("YOU LOSE!!!");
            }
        }));
        movementAnimation.setCycleCount(Animation.INDEFINITE);
    }

    private boolean checkForCollision() {
        return player.getRectangle().getBoundsInParent().intersects(polygon.getBoundsInParent());
    }
}
