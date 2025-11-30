package projects.postcardly.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projects.postcardly.PostcardlyApp;
import projects.postcardly.model.*;
import projects.postcardly.service.DataManager;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TripViewController {

    // Top - Trip Header
    @FXML private Button backButton;
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
    @FXML private StackPane textEntryPane;
    @FXML private Label textEntryTitleLabel;
    @FXML private Label textEntryDateLabel;
    @FXML private TextArea textEntryContentLabel;
    @FXML private Label textEntryMoodLabel;

    // Bottom - Actions
    @FXML private Button addMemoryButton;

    private Trip currentTrip;
    private ObservableList<Memory> memories;
    private int currentMemoryIndex = 0;

    @FXML
    public void initialize() {
    }

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
            // "no memories" state
            noMemoriesPane.setVisible(true);
            slideshowPane.setVisible(false);
        } else {
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

        // Hide both display types first
        memoryImageView.getParent().setVisible(false);
        textEntryPane.setVisible(false);

        // Display based on memory type
        if (memory instanceof PictureMemory) {
            displayPictureMemory((PictureMemory) memory);
        } else if (memory instanceof TextEntryMemory) {
            displayTextEntryMemory((TextEntryMemory) memory);
        }

        // Update counter
        memoryCounterLabel.setText((index + 1) + " / " + memories.size());
    }

    private void displayPictureMemory(PictureMemory memory) {
        // Show image display
        memoryImageView.getParent().setVisible(true);

        // Display memory details below image
        memoryTitleLabel.setText(memory.getTitle());
        memoryDateLabel.setText(memory.getDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));

        String description = memory.getDescription();
        if (description != null && !description.trim().isEmpty()) {
            memoryDescriptionLabel.setText(description);
            memoryDescriptionLabel.setVisible(true);
        } else {
            memoryDescriptionLabel.setVisible(false);
        }

        loadImage(memory.getImagePath());
    }

    private void displayTextEntryMemory(TextEntryMemory memory) {
        // Show text display
        textEntryPane.setVisible(true);

        // Display text entry details
        textEntryTitleLabel.setText(memory.getTitle());
        textEntryDateLabel.setText(memory.getDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        textEntryContentLabel.setText(memory.getContent());

        // Show mood if present
        String mood = memory.getMood();
        if (mood != null && !mood.trim().isEmpty()) {
            textEntryMoodLabel.setText("Mood: " + mood);
            textEntryMoodLabel.setVisible(true);
        } else {
            textEntryMoodLabel.setVisible(false);
        }

        // Hide the bottom description label (not used for text entries)
        memoryDescriptionLabel.setVisible(false);
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
        // Show dialog to choose memory type
        Alert choiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
        choiceAlert.setTitle("Add Memory");
        choiceAlert.setHeaderText("What type of memory would you like to add?");
        choiceAlert.setContentText("Choose memory type:");

        ButtonType pictureButton = new ButtonType("📷 Picture");
        ButtonType journalButton = new ButtonType("📝 Journal Entry");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        choiceAlert.getButtonTypes().setAll(pictureButton, journalButton, cancelButton);

        choiceAlert.showAndWait().ifPresent(response -> {
            if (response == pictureButton) {
                handleAddPictureMemory();
            } else if (response == journalButton) {
                handleAddJournalEntry();
            }
        });
    }
    private void handleAddPictureMemory() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo for Memory");
        // Only selectable files are .png,.jpg,.gif
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) backButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            PictureMemory newMemory = new PictureMemory(
                    "Memory " + (memories.size() + 1),
                    LocalDate.now(),
                    "A memorable moment from " + currentTrip.getTitle(),
                    file.getAbsolutePath()
            );

            currentTrip.addMemory(newMemory);
            User currentUser = PostcardlyApp.getCurrentUser();
            DataManager.saveUser(currentUser);

            currentMemoryIndex = memories.size() - 1;
            updateMemoryDisplay();

            System.out.println("✓ Picture memory added: " + newMemory.getTitle());
        }
    }
    private void handleAddJournalEntry() {
        // Create title dialog
        TextInputDialog titleDialog = new TextInputDialog("Journal Entry " + (memories.size() + 1));
        titleDialog.setTitle("New Journal Entry");
        titleDialog.setHeaderText("Create a new journal entry");
        titleDialog.setContentText("Entry title:");

        titleDialog.showAndWait().ifPresent(title -> {

            // Create custom dialog with TextArea for content
            Dialog<String> contentDialog = new Dialog<>();
            contentDialog.setTitle("Journal Entry Content");
            contentDialog.setHeaderText("Write your thoughts");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            contentDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            // Text area & live limit
            int WORD_LIMIT = 200;

            TextArea contentArea = new TextArea();
            contentArea.setPromptText("Write your journal entry here...");
            contentArea.setPrefRowCount(10);
            contentArea.setWrapText(true);

            // Word-count
            Label countLabel = new Label("0 / " + WORD_LIMIT + " words");
            VBox box = new VBox(10, contentArea, countLabel);
            contentDialog.getDialogPane().setContent(box);

            // Disable OK until text is valid
            Button okBtn = (Button) contentDialog.getDialogPane().lookupButton(okButton);
            okBtn.setDisable(true);

            // Live word counting
            contentArea.textProperty().addListener((obs, oldText, newText) -> {
                int wordCount = newText.trim().isEmpty()
                        ? 0
                        : newText.trim().split("\\s+").length;

                countLabel.setText(wordCount + " / " + WORD_LIMIT + " words");
                okBtn.setDisable(wordCount > WORD_LIMIT);
            });

            contentDialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return contentArea.getText();
                }
                return null;
            });

            // Handle final content
            contentDialog.showAndWait().ifPresent(content -> {
                TextInputDialog moodDialog = new TextInputDialog();
                moodDialog.setTitle("Mood");
                moodDialog.setHeaderText("How were you feeling?");
                moodDialog.setContentText("Mood (optional):");

                String mood = moodDialog.showAndWait().orElse("");

                TextEntryMemory newMemory = new TextEntryMemory(
                        title,
                        LocalDate.now(),
                        "Journal entry from " + currentTrip.getTitle(),
                        content
                );

                if (!mood.isEmpty()) {
                    newMemory.setMood(mood);
                }

                currentTrip.addMemory(newMemory);
                User currentUser = PostcardlyApp.getCurrentUser();
                DataManager.saveUser(currentUser);

                currentMemoryIndex = memories.size() - 1;
                updateMemoryDisplay();

                System.out.println("✓ Journal entry added: " + newMemory.getTitle());
            });
        });
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