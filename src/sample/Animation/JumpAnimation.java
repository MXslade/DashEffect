package sample.Animation;

import javafx.animation.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JumpAnimation {

    public static double JUMP_DURATION = 0.5;

    private TranslateTransition animationByX;
    private Timeline animationByY;

    private double startVelocity = -10;

    public JumpAnimation(Rectangle player, double finalXPoint) {
        animationByX = new TranslateTransition();
        animationByX.setNode(player);
        animationByX.setDuration(Duration.seconds(JUMP_DURATION));
        animationByX.setToX(finalXPoint);

        animationByY = new Timeline();
        animationByY.setCycleCount(2);
        animationByY.setAutoReverse(true);
        animationByY.getKeyFrames().add(
                new KeyFrame(Duration.seconds(JUMP_DURATION / 2),
                        new KeyValue(player.translateYProperty(), -50)));
    }

    public void play() {
        animationByX.play();
        animationByY.play();
    }

}
