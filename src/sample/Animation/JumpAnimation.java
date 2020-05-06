package sample.Animation;

import javafx.animation.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.Main;

public class JumpAnimation {

    public static double JUMP_DURATION = 0.5;

    private TranslateTransition animationByX;
    private Timeline animationByY;

    private RotateTransition rotateToRight;
    private RotateTransition rotateToLeft;

    private double finalXPoint;

    public JumpAnimation(Rectangle player, double finalXPoint) {
        this.finalXPoint = finalXPoint;

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

        rotateToRight = new RotateTransition();
        rotateToRight.setNode(player);
        rotateToRight.setByAngle(-270);

        rotateToLeft = new RotateTransition();
        rotateToLeft.setNode(player);
        rotateToLeft.setByAngle(270);
    }

    public void play() {
        animationByX.play();
        animationByY.play();
        if (finalXPoint == Main.LEFT_EDGE) {
            rotateToRight.play();
        } else {
            rotateToLeft.play();
        }
    }

}
