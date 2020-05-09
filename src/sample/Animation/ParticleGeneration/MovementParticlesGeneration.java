package sample.Animation.ParticleGeneration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Model.Particle;
import sample.Model.Player;

import java.util.ArrayList;

public class MovementParticlesGeneration {

    private Pane root;
    private Player player;

    private int numberOfParticles;
    private ArrayList<Particle> particles;
    private Timeline launchingParticles;
    private int currentParticle = 0;

    public MovementParticlesGeneration(int numberOfParticles, Pane root, Player player) {
        this.numberOfParticles = numberOfParticles;
        this.root = root;
        this.player = player;
        particles = new ArrayList<>();
        createParticles();
        createAnimation();
    }

    public void play() {
        launchingParticles.play();
    }

    private void createParticles() {
        for (int i = 0; i < numberOfParticles; ++i) {
            Particle particle = new Particle(root, player);
            particles.add(particle);
        }
    }

    private void createAnimation() {
        launchingParticles = new Timeline(new KeyFrame(Duration.seconds(0.25), actionEvent -> {
            particles.get(currentParticle).play();
            currentParticle++;
        }));
        launchingParticles.setCycleCount(numberOfParticles);
    }

}
