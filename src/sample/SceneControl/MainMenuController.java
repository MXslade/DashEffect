package sample.SceneControl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController {

    private SceneController sceneController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startGameBtn;

    @FXML
    private Button exitGameBtn;

    @FXML
    void initialize() {
    }

    public void startGame(MouseEvent mouseEvent) {
        sceneController.startGame();
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void exit(MouseEvent mouseEvent) {
        sceneController.getStage().close();
    }
}