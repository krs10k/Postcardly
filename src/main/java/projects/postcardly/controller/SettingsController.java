package projects.postcardly.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import projects.postcardly.model.User;
import projects.postcardly.service.DataManager;

import java.util.Optional;

public class SettingsController {

    // The current app user (must be passed in or fetched from a central place)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            System.out.println("Returning to Main Menu...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/MainMenu.fxml"));
            Parent root = loader.load();

            // Get current stage from event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Postcardly - Main Menu");

            System.out.println("Main Menu loaded successfully!");

        } catch (Exception e) {
            System.out.println("ERROR loading Main Menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearData() {
        System.out.println("Clear all data clicked");

        // Load existing user
        User user = DataManager.loadUser();

        if (user == null) {
            showAlert("No saved data found.", Alert.AlertType.WARNING);
            return;
        }

        // Wipe trips
        user.getTrips().clear();

        // (Optional) wipe memories also:
        // user.getMemories().clear();

        // Save updated user back to disk
        boolean success = DataManager.saveUser(user);

        if (success) {
            showAlert("All trips have been deleted successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Failed to delete data.", Alert.AlertType.ERROR);
        }
    }


    /**
     * Utility method for alerts
     */
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}