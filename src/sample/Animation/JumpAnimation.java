package sample.Animation;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JumpAnimation {

    public static double JUMP_DURATION = 0.5;
    public static double GRAVITY_ACCELERATION = 0.4;

    private TranslateTransition animationByX;
    private AnimationTimer animationByY;

    private double startVelocity = -10;

    public JumpAnimation(Rectangle player, double finalXPoint) {
        animationByX = new TranslateTransition();
        animationByX.setNode(player);
        animationByX.setDuration(Duration.seconds(JUMP_DURATION));
        animationByX.setToX(finalXPoint);

        animationByY = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (animationByX.getStatus() == Animation.Status.RUNNING) {
                    startVelocity += GRAVITY_ACCELERATION;
                    player.setY(player.getY() + startVelocity);
                }
            }
        };

    }

    public void play() {
        startVelocity = -10;
        animationByX.play();
        animationByY.start();
    }

}
