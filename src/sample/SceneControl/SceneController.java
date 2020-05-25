package sample.SceneControl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene mainMenuScene;
    private GameScene gameScene;
    private MainMenuController mainMenuController;

    public SceneController(Stage stage) {
        this.stage = stage;
        createMainMenuScene();
        createGameScene();
    }

    public void showMainMenu() {
        stage.setScene(mainMenuScene);
        stage.show();
    }

    public void startGame() {
        stage.setScene(gameScene.getScene());
        stage.show();
        gameScene.play();
    }

    public Stage getStage() {
        return stage;
    }

    private void createMainMenuScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/fxmls/testing.fxml"));
            Pane menuRoot = fxmlLoader.load();
            mainMenuController = fxmlLoader.getController();
            mainMenuScene = new Scene(menuRoot);
            mainMenuController.setSceneController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGameScene() {
        gameScene = new GameScene(stage);
    }
}
