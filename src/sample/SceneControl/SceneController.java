package sample.SceneControl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.MainMenuController;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    private Stage stage;
    private Scene mainMenuScene;
    private GameScene gameScene;
    private MainMenuController mainMenuController;

    private Pane root;

    public SceneController(Stage stage) {
        this.stage = stage;
        createMainMenuScene();
        createGameScene();
    }

    public void showMainMenu() {
        stage.setScene(mainMenuScene);
        stage.show();
    }

    private void createMainMenuScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane menuRoot = fxmlLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("./sample/testing.fxml")));
            mainMenuController = (MainMenuController) fxmlLoader.getController();
            mainMenuScene = new Scene(menuRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGameScene() {
        gameScene = new GameScene(stage);
    }
}
