package projects.postcardly.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import projects.postcardly.PostcardlyApp;
import projects.postcardly.model.Trip;
import projects.postcardly.model.User;
import projects.postcardly.service.DataManager;

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
            // TEST: Try to load saved user
            User user = DataManager.loadUser();

            if (user == null) {
                // No saved user, create a new one
                System.out.println("=== FIRST LAUNCH: Creating new user ===");
                user = new User("Test", "User", "testuser", "test@email.com", "Testing the app!");

                // Add a test trip so we can see if it persists
                user.addTrip(new Trip("Test Trip", "Test Location",
                        java.time.LocalDate.now(),
                        java.time.LocalDate.now().plusDays(3)));

                System.out.println("User created with " + user.getTotalTrips() + " trip");

                // Save the new user
                boolean saved = DataManager.saveUser(user);
                System.out.println("Save successful: " + saved);
            } else {
                // User was loaded from disk!
                System.out.println("=== USER LOADED FROM DISK ===");
                System.out.println("Username: " + user.getUsername());
                System.out.println("Total trips: " + user.getTotalTrips());
            }

            // Set as current user
            PostcardlyApp.setCurrentUser(user);

            System.out.println("Data file location: " + DataManager.getDataFilePath());
            System.out.println("===============================");

            // Navigate to main menu
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