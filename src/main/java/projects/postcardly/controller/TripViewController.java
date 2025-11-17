package projects.postcardly.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projects.postcardly.PostcardlyApp;
import projects.postcardly.model.Memory;
import projects.postcardly.model.PictureMemory;
import projects.postcardly.model.Trip;
import projects.postcardly.model.User;
import projects.postcardly.service.DataManager;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TripViewController {

    // Top - Trip Header
    @FXML private Button backButton;
    @FXML private Button testMemoryButton;
    @FXML private Label tripTitleLabel;
    @FXML private Label tripLocationLabel;
    @FXML private Label tripDatesLabel;
    @FXML private Label tripDescriptionLabel;

    // Center - Slideshow
    @FXML private VBox noMemoriesPane;
    @FXML private VBox slideshowPane;
    @FXML private ImageView memoryImageView;
    @FXML private VBox noImagePlaceholder;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    @FXML private Label memoryTitleLabel;
    @FXML private Label memoryDateLabel;
    @FXML private Label memoryDescriptionLabel;
    @FXML private Label memoryCounterLabel;

    // Bottom - Actions
    @FXML private Button addMemoryButton;

    private Trip currentTrip;
    private ObservableList<Memory> memories;
    private int currentMemoryIndex = 0;

    @FXML
    public void initialize() {
    }

    /**
     * Called from TripsMenuController to pass the selected trip
     */
    public void setTrip(Trip trip) {
        this.currentTrip = trip;
        this.memories = trip.getMemories();

        displayTripDetails();
        updateMemoryDisplay();

    }

    private void displayTripDetails() {
        tripTitleLabel.setText(currentTrip.getTitle());
        tripLocationLabel.setText("📍 " + currentTrip.getLocation());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String dateRange = currentTrip.getStartDate().format(formatter) +
                " - " +
                currentTrip.getEndDate().format(formatter);
        tripDatesLabel.setText(dateRange);

        String description = currentTrip.getDescription();
        if (description != null && !description.trim().isEmpty()) {
            tripDescriptionLabel.setText(description);
            tripDescriptionLabel.setVisible(true);
        } else {
            tripDescriptionLabel.setVisible(false);
        }
    }

    private void updateMemoryDisplay() {
        if (memories.isEmpty()) {
            // Show "no memories" state
            noMemoriesPane.setVisible(true);
            slideshowPane.setVisible(false);
        } else {
            // Show slideshow
            noMemoriesPane.setVisible(false);
            slideshowPane.setVisible(true);
            displayMemory(currentMemoryIndex);
            updateNavigationButtons();
        }
    }

    private void displayMemory(int index) {
        if (index < 0 || index >= memories.size()) {
            return;
        }

        Memory memory = memories.get(index);

        // Display memory details
        memoryTitleLabel.setText(memory.getTitle());
        memoryDateLabel.setText(memory.getDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

        String description = memory.getDescription();
        if (description != null && !description.trim().isEmpty()) {
            memoryDescriptionLabel.setText(description);
            memoryDescriptionLabel.setVisible(true);
        } else {
            memoryDescriptionLabel.setVisible(false);
        }

        // Display image if it's a PictureMemory
        if (memory instanceof PictureMemory) {
            PictureMemory pictureMemory = (PictureMemory) memory;
            loadImage(pictureMemory.getImagePath());
        } else {
            // Other memory types - show placeholder
            showNoImagePlaceholder();
        }

        // Update counter
        memoryCounterLabel.setText((index + 1) + " / " + memories.size());
    }

    private void loadImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            showNoImagePlaceholder();
            return;
        }

        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                memoryImageView.setImage(image);
                memoryImageView.setVisible(true);
                noImagePlaceholder.setVisible(false);
            } else {
                showNoImagePlaceholder();
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            showNoImagePlaceholder();
        }
    }

    private void showNoImagePlaceholder() {
        memoryImageView.setVisible(false);
        noImagePlaceholder.setVisible(true);
    }

    private void updateNavigationButtons() {
        // Enable/disable buttons based on position
        previousButton.setDisable(currentMemoryIndex == 0);
        nextButton.setDisable(currentMemoryIndex == memories.size() - 1);

        // Hide buttons if only one memory
        if (memories.size() <= 1) {
            previousButton.setVisible(false);
            nextButton.setVisible(false);
        } else {
            previousButton.setVisible(true);
            nextButton.setVisible(true);
        }
    }

    @FXML
    private void handlePrevious() {
        if (currentMemoryIndex > 0) {
            currentMemoryIndex--;
            displayMemory(currentMemoryIndex);
            updateNavigationButtons();
        }
    }

    @FXML
    private void handleNext() {
        if (currentMemoryIndex < memories.size() - 1) {
            currentMemoryIndex++;
            displayMemory(currentMemoryIndex);
            updateNavigationButtons();
        }
    }

    @FXML
    private void handleAddMemory() {
        // Let user select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo for Memory");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) backButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Create picture memory
            PictureMemory newMemory = new PictureMemory(
                    "Memory " + (memories.size() + 1),
                    LocalDate.now(),
                    "A memorable moment from " + currentTrip.getTitle(),
                    file.getAbsolutePath()
            );

            // Add to trip
            currentTrip.addMemory(newMemory);

            // Save to disk
            User currentUser = PostcardlyApp.getCurrentUser();
            DataManager.saveUser(currentUser);

            // Update display - show the newly added memory
            currentMemoryIndex = memories.size() - 1;
            updateMemoryDisplay();

            System.out.println("✓ Memory added: " + newMemory.getTitle());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/TripsMenu.fxml"));
            Parent tripsMenuRoot = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene tripsMenuScene = new Scene(tripsMenuRoot);
            stage.setScene(tripsMenuScene);
            stage.setTitle("Postcardly - My Trips");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}