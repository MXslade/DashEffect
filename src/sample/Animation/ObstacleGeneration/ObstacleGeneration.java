package sample.Animation.ObstacleGeneration;

import javafx.scene.layout.Pane;
import sample.Model.Obstacle;
import sample.Model.Player;

import java.util.ArrayList;

public class ObstacleGeneration {

    private Pane root;
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private int numberOfObstacles;

    public ObstacleGeneration(Pane root, Player player, int numberOfObstacles) {
        this.root = root;
        this.player = player;
        this.numberOfObstacles = numberOfObstacles;
        createObstacles();
    }

    public void play() {
        for (Obstacle obstacle : obstacles) {
            obstacle.play();
        }
    }

    public void pause() {
        for (Obstacle obstacle : obstacles) {
            obstacle.pause();
        }
    }

    private void createObstacles() {
        obstacles = new ArrayList<>();
        for (int i = 0; i < numberOfObstacles; ++i) {
            Obstacle obstacle = new Obstacle(root, player, true);
            obstacles.add(obstacle);
            obstacle.setTranslateValues(player.getRectangle().getTranslateX(), i * 100);
        }
    }
}
