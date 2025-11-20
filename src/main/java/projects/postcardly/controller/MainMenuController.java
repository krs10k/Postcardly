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
        try {
            System.out.println("Loading Trip Create Menu...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/CreateTrip.fxml"));
            Parent createTripRoot = loader.load();

            Stage stage = (Stage) createTripButton.getScene().getWindow();
            Scene createTripScene = new Scene(createTripRoot);
            stage.setScene(createTripScene);
            stage.setTitle("Postcardly - Create your Trip");

            System.out.println("Trip Create Menu Loaded successfully!");

        } catch (Exception e) {
            System.out.println("ERROR loading Trip Create Menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        try {
            System.out.println("Loading Settings Menu...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/SettingsScreen.fxml"));
            Parent settingsRoot = loader.load();

            Stage stage = (Stage) settingsButton.getScene().getWindow();
            Scene settingsScene = new Scene(settingsRoot);
            stage.setScene(settingsScene);
            stage.setTitle("Postcardly - Settings");

            System.out.println("Settings Menu loaded successfully!");

        } catch (Exception e) {
            System.out.println("ERROR loading Settings Menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        System.out.println("Exit clicked");
        System.exit(0);
    }
}