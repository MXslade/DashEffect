package sample.Model;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import sample.Animation.JumpAnimation;
import sample.Animation.ParticleGeneration.MovementParticlesGeneration;
import sample.Main;

import java.io.File;

public class Player {

    private Pane root;
    private double height;
    private double width;

    private double leftEdge = Main.LEFT_EDGE;
    private double rightEdge = Main.RIGHT_EDGE;

    private boolean left = true;

    private Rectangle rectangle;
    private JumpAnimation jumpToLeft;
    private JumpAnimation jumpToRight;
    private MovementParticlesGeneration particlesGeneration;
    private Media jumpSoundEffect;
    private MediaPlayer jumpMediaPlayer;

    public Player(Pane root, double height, double width) {
        this.root = root;
        this.height = height;
        this.width = width;
        createRectangle();
        createAnimation();
        createMedia();
    }

    public void jump() {
        if( !connectedWithWall() ){
            return;
        }
        playJumpSound();
        if (left) {
            jumpToRight.play();
        } else {
            jumpToLeft.play();
        }
        left = !left;
    }

    public boolean connectedWithWall(){
        return ( rectangle.getTranslateX() == 0.0 || rectangle.getTranslateX() == 480.0 || rectangle.getTranslateX() == 750.0);
    }

    public boolean connectedWithRightWall() {
        return rectangle.getTranslateX() == 750.0;
    }

    public boolean connectedWithLeftWall() {
        return rectangle.getTranslateX() == 0.0 || rectangle.getTranslateX() == 480.0;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    private void createRectangle() {
        rectangle = new Rectangle();
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setTranslateY(Main.HEIGHT * 2.0 / 3);
        root.getChildren().add(rectangle);
    }

    private void createAnimation() {
        jumpToRight = new JumpAnimation(rectangle, rightEdge - width);
        jumpToLeft = new JumpAnimation(rectangle, leftEdge);
        particlesGeneration = new MovementParticlesGeneration(10, root, this);
        particlesGeneration.play();
    }

    private void createMedia() {
        String jumpAudioFileStr = "./Audio/Dash_Effect_Jump1.mp3";
        jumpSoundEffect = new Media(new File(jumpAudioFileStr).toURI().toString());
        jumpMediaPlayer = new MediaPlayer(jumpSoundEffect);
    }

    private void playJumpSound(){
        jumpMediaPlayer.seek(jumpMediaPlayer.getStartTime());
        jumpMediaPlayer.play();
    }
}
