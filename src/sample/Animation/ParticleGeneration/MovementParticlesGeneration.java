package sample.Animation.ParticleGeneration;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import sample.Model.Player;

import java.util.ArrayList;

public class MovementParticlesGeneration {

    private Pane root;
    private Player player;

    private int numberOfParticles;
    private ArrayList<Circle> particles;
    private int initialRadius = 1;
    private int endRadius = 10;

    private Timeline expandingAnimation;
    private Timeline movementAnimation;

    public MovementParticlesGeneration(int numberOfParticles, Pane root, Player player) {
        this.numberOfParticles = numberOfParticles;
        this.root = root;
        this.player = player;
        particles = new ArrayList<>();
        createParticles();
        createAnimation();
    }

    public void play() {
        expandingAnimation.play();
    }

    public int getNumberOfParticles() {
        return numberOfParticles;
    }

    public void setNumberOfParticles(int numberOfParticles) {
        this.numberOfParticles = numberOfParticles;
    }

    public ArrayList<Circle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Circle> particles) {
        this.particles = particles;
    }

    private void createAnimation() {
        expandingAnimation = new Timeline(new KeyFrame(Duration.seconds(0.01), actionEvent -> {
            for (Circle circle : particles) {
                if (!player.connectedWithWall()) {
                    return;
                }
                circle.setRadius(circle.getRadius() + 0.1);
                if (player.connectedWithLeftWall()) {
                    circle.setTranslateX(player.getRectangle().getTranslateX());
                } else {
                    circle.setTranslateX(player.getRectangle().getTranslateX() + player.getRectangle().getWidth());
                }
                if (circle.getRadius() >= endRadius) {
                    circle.setRadius(initialRadius);
                    circle.setCenterY(player.getRectangle().getY() + player.getRectangle().getHeight());
                }
            }
        }));
        expandingAnimation.setCycleCount(Animation.INDEFINITE);
//        movementAnimation = new Timeline(new KeyFrame(Duration.seconds(0.01), actionEvent -> {
//
//        }));
    }

    private void createParticles() {
        for (int i = 0; i < numberOfParticles; ++i) {
            Circle circle = new Circle();
            circle.setRadius(initialRadius);
            particles.add(circle);
            root.getChildren().add(circle);
        }
    }


}
