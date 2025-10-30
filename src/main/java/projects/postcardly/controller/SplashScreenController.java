package projects.postcardly.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController {

    @FXML
    private AnchorPane rootContainer;

    @FXML
    public void initialize() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> loadMainMenu());
        pause.play();
    }

    private void loadMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/MainMenu.fxml"));
            Parent mainMenuRoot = loader.load();

            Stage stage = (Stage) rootContainer.getScene().getWindow();
            Scene mainMenuScene = new Scene(mainMenuRoot);
            stage.setScene(mainMenuScene);
            stage.setTitle("Postcardly - Main Menu");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}