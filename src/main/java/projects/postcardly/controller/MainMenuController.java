package projects.postcardly.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        System.out.println("View Trips clicked");
    }

    @FXML
    private void handleCreateTrip() {
        System.out.println("Create Trip clicked");
    }

    @FXML
    private void handleSettings() {
        System.out.println("Settings clicked");
    }

    @FXML
    private void handleExit() {
        System.out.println("Exit clicked");
        System.exit(0);
    }
}