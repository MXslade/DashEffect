package sample;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button StartGameBtn;

    @FXML
    private Button ExitGameBtn;

    @FXML
    void initialize() {
        StartGameBtn.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).hide();


        });
        ExitGameBtn.setOnAction(event->{
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

    }
}