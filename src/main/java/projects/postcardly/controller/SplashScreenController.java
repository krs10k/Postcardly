package projects.postcardly.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController {

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label loadingLabel;

    @FXML
    public void initialize() {
        System.out.println("=== SPLASH SCREEN INITIALIZED ===");

        // Simulate loading for 3 seconds, then switch to main menu
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            System.out.println("=== TIMER FINISHED - CALLING loadMainMenu() ===");
            loadMainMenu();
        });
        pause.play();

        System.out.println("=== TIMER STARTED ===");
    }

    private void loadMainMenu() {
        System.out.println("=== loadMainMenu() CALLED ===");

        try {
            System.out.println("Step 1: Attempting to load MainMenu.fxml...");
            System.out.println("Resource URL: " + getClass().getResource("/projects/postcardly/MainMenu.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/MainMenu.fxml"));
            Parent mainMenuRoot = loader.load();

            System.out.println("Step 2: MainMenu.fxml loaded successfully!");

            // Get the current stage
            Stage stage = (Stage) progressIndicator.getScene().getWindow();
            System.out.println("Step 3: Got stage: " + stage);

            // Set the new scene
            Scene mainMenuScene = new Scene(mainMenuRoot);
            stage.setScene(mainMenuScene);
            stage.setTitle("Postcardly - Main Menu");

            System.out.println("Step 4: Scene switched successfully!");

        } catch (Exception e) {
            System.out.println("=== ERROR IN loadMainMenu() ===");
            e.printStackTrace();
        }
    }
}