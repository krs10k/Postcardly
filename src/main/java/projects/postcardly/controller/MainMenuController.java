package projects.postcardly.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button viewTripsButton;
    @FXML
    private Button createTripButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        System.out.println("Main Menu loaded!");
    }

    @FXML
    private void handleViewTrips() {
        try {
            System.out.println("Loading Trips Menu...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/TripsMenu.fxml"));
            Parent tripsMenuRoot = loader.load();

            Stage stage = (Stage) viewTripsButton.getScene().getWindow();
            Scene tripsMenuScene = new Scene(tripsMenuRoot);
            stage.setScene(tripsMenuScene);
            stage.setTitle("Postcardly - My Trips");

            System.out.println("Trips Menu loaded successfully!");

        } catch (Exception e) {
            System.out.println("ERROR loading Trips Menu:");
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCreateTrip() {
        // todo
        System.out.println("Create Trip clicked");
    }

    @FXML
    private void handleSettings() {
        // todo
        System.out.println("Settings clicked");
    }

    @FXML
    private void handleExit() {
        System.out.println("Exit clicked");
        System.exit(0);
    }
}