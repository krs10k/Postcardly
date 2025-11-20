package projects.postcardly.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projects.postcardly.PostcardlyApp;
import projects.postcardly.model.Trip;
import projects.postcardly.model.User;
import projects.postcardly.service.DataManager;

import java.io.File;
import java.time.LocalDate;

public class CreateTripController {

    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextArea descriptionArea;
    @FXML private Button selectImageButton;
    @FXML private Label imageFileLabel;
    @FXML private Label errorLabel;
    @FXML private Button backButton;

    private User currentUser;
    private File selectedImageFile;

    @FXML
    public void initialize() {
        // Set default date to today
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(7));

        // Add validation listener for dates
        startDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> validateDates());
        endDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> validateDates());
    }

    private void validateDates() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
                showError("End date must be after start date");
            } else {
                hideError();
            }
        }
    }

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Trip Cover Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) selectImageButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            selectedImageFile = file;
            imageFileLabel.setText(file.getName());
            imageFileLabel.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 13px;");
        }
    }

    @FXML
    private void handleCreateTrip() {
        // Validate required fields
        if (!validateFields()) {
            return;
        }

        // Create the trip
        Trip newTrip = new Trip(
                titleField.getText().trim(),
                locationField.getText().trim(),
                startDatePicker.getValue(),
                endDatePicker.getValue()
        );

        // Set optional fields
        if (!descriptionArea.getText().trim().isEmpty()) {
            newTrip.setDescription(descriptionArea.getText().trim());
        }

        if (selectedImageFile != null) {
            newTrip.setCoverImagePath(selectedImageFile.getAbsolutePath());
        }

        // Get current user from app
        User currentUser = PostcardlyApp.getCurrentUser();

        if (currentUser == null) {
            showError("Error: No user logged in!");
            return;
        }

        // Add trip to user
        currentUser.addTrip(newTrip);

        // Save to disk
        boolean saved = DataManager.saveUser(currentUser);

        if (saved) {
            System.out.println("✓ Trip created and saved: " + newTrip.getTitle());

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Trip Created!");
            alert.setContentText("Your trip \"" + newTrip.getTitle() + "\" has been created successfully!");
            alert.showAndWait();

            // Navigate to trips menu
            navigateToTripsMenu();
        } else {
            showError("Failed to save trip. Please try again.");
        }
    }

    private boolean validateFields() {
        // Check title
        if (titleField.getText() == null || titleField.getText().trim().isEmpty()) {
            showError("Please enter a trip title");
            titleField.requestFocus();
            return false;
        }

        // Check location
        if (locationField.getText() == null || locationField.getText().trim().isEmpty()) {
            showError("Please enter a location");
            locationField.requestFocus();
            return false;
        }

        // Check start date
        if (startDatePicker.getValue() == null) {
            showError("Please select a start date");
            startDatePicker.requestFocus();
            return false;
        }

        // Check end date
        if (endDatePicker.getValue() == null) {
            showError("Please select an end date");
            endDatePicker.requestFocus();
            return false;
        }

        // Check date logic
        if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            showError("End date must be after start date");
            endDatePicker.requestFocus();
            return false;
        }

        hideError();
        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleCancel() {
        // Confirm cancellation if user has entered data
        if (hasUnsavedChanges()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Cancel");
            alert.setHeaderText("Discard changes?");
            alert.setContentText("You have unsaved changes. Are you sure you want to cancel?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    navigateBack();
                }
            });
        } else {
            navigateBack();
        }
    }

    private boolean hasUnsavedChanges() {
        return (titleField.getText() != null && !titleField.getText().trim().isEmpty()) ||
                (locationField.getText() != null && !locationField.getText().trim().isEmpty()) ||
                (descriptionArea.getText() != null && !descriptionArea.getText().trim().isEmpty());
    }

    @FXML
    private void handleBack() {
        handleCancel(); // same logic as cancel
    }

    private void navigateBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/MainMenu.fxml"));
            Parent mainMenuRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene mainMenuScene = new Scene(mainMenuRoot);
            stage.setScene(mainMenuScene);
            stage.setTitle("Postcardly - Main Menu");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToTripsMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/TripsMenu.fxml"));
            Parent tripsMenuRoot = loader.load();

            // TODO: Pass currentUser to TripsMenuController
            // TripsMenuController controller = loader.getController();
            // controller.setUser(currentUser);

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene tripsMenuScene = new Scene(tripsMenuRoot);
            stage.setScene(tripsMenuScene);
            stage.setTitle("Postcardly - My Trips");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // receives user from previous screen
    public void setUser(User user) {
        this.currentUser = user;
    }
}