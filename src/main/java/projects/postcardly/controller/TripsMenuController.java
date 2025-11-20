package projects.postcardly.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import projects.postcardly.PostcardlyApp;
import projects.postcardly.model.Trip;
import projects.postcardly.model.User;
import projects.postcardly.service.DataManager;
import java.time.format.DateTimeFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class TripsMenuController {

    @FXML private ScrollPane tripsScrollPane;
    @FXML private FlowPane tripCardsPane;
    @FXML private TextField searchField;
    @FXML private Label tripCountLabel;
    @FXML private Button createTripButton;
    @FXML private Button backButton;

    private User currentUser;
    private ObservableList<Trip> tripList;

    @FXML
    public void initialize() {
        // Get the current user from the app
        User user = PostcardlyApp.getCurrentUser();

        if (user == null) {
            System.err.println("ERROR: No current user set!");
            return;
        }

        this.currentUser = user;

        // Use the user's actual trips
        tripList = FXCollections.observableArrayList(currentUser.getTrips());

        displayTripCards();
        updateTripCount();
        searchField.textProperty().addListener((obs, oldText, newText) -> filterAndDisplayTrips(newText));
        tripsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tripsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void displayTripCards() {
        tripCardsPane.getChildren().clear();

        for (Trip trip : tripList) {
            VBox card = createTripCard(trip);
            tripCardsPane.getChildren().add(card);
        }
    }

    private VBox createTripCard(Trip trip) {
        // Main card container
        VBox card = new VBox(15);
        card.setPrefSize(280, 320);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #34495E; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5); " +
                        "-fx-cursor: hand;"
        );

        // hover effect
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #3d5a70; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 8); " +
                        "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #34495E; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5); " +
                        "-fx-cursor: hand;"
        ));

        // image placeholder
        StackPane imagePlaceholder = new StackPane();
        imagePlaceholder.setPrefSize(240, 140);
        imagePlaceholder.setStyle("-fx-background-radius: 10;");

        if (trip.getCoverImagePath() != null && !trip.getCoverImagePath().isEmpty()) {
            try {
                Image image = new Image(new File(trip.getCoverImagePath()).toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(240);
                imageView.setFitHeight(140);
                imageView.setPreserveRatio(true);


                imagePlaceholder.getChildren().add(imageView);
            } catch (Exception e) {
                System.err.println("Failed to load trip image: " + e.getMessage());
                // fallback to placeholder label
                Label imageLabel = new Label("📸");
                imageLabel.setStyle("-fx-font-size: 40px;");
                imagePlaceholder.getChildren().add(imageLabel);
                imagePlaceholder.setStyle(
                        "-fx-background-color: linear-gradient(to bottom right, #3498DB, #2ECC71); " +
                                "-fx-background-radius: 10;"
                );
            }
        } else {
            // no placeholder
            Label imageLabel = new Label(":(");
            imageLabel.setStyle("-fx-font-size: 40px;");
            imagePlaceholder.getChildren().add(imageLabel);
            imagePlaceholder.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #3498DB, #2ECC71); " +
                            "-fx-background-radius: 10;"
            );
        }

        // Trip title
        Label titleLabel = new Label(trip.getTitle());
        titleLabel.setStyle(
                "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #ECF0F1; " +
                        "-fx-wrap-text: true;"
        );
        titleLabel.setMaxWidth(240);

        // Location
        HBox locationBox = new HBox(5);
        locationBox.setAlignment(Pos.CENTER_LEFT);
        Label locationIcon = new Label("📍");
        Label locationLabel = new Label(trip.getLocation());
        locationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #BDC3C7;");
        locationBox.getChildren().addAll(locationIcon, locationLabel);

        // Date range
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String dateRange = trip.getStartDate().format(formatter) + " - " + trip.getEndDate().format(formatter);
        Label dateLabel = new Label(dateRange);
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #95A5A6;");

        // Memory count
        HBox memoryBox = new HBox(5);
        memoryBox.setAlignment(Pos.CENTER_LEFT);
        Label memoryIcon = new Label("💭");
        Label memoryLabel = new Label(trip.getMemoryCount() + " memories");
        memoryLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #3498DB;");
        memoryBox.getChildren().addAll(memoryIcon, memoryLabel);

        // Spacer to push content
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Delete button
        Button deleteButton = new Button("Delete Trip");
        deleteButton.setStyle(
                "-fx-background-color: #E74C3C; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10;"
        );
        deleteButton.setPrefWidth(240);

        deleteButton.setOnAction(e -> {
            // Prevent the click from triggering card click
            e.consume();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Trip");
            confirm.setHeaderText("Are you sure you want to delete \"" + trip.getTitle() + "\"?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Remove from user
                    currentUser.getTrips().remove(trip);
                    // Save changes
                    DataManager.saveUser(currentUser);
                    // Update list and UI
                    tripList.remove(trip);
                    displayTripCards();
                    updateTripCount();
                }
            });
        });

        // Add click handler to view trip
        card.setOnMouseClicked(e -> handleViewTrip(trip));

        // Add all elements to card
        card.getChildren().addAll(
                imagePlaceholder,
                titleLabel,
                locationBox,
                dateLabel,
                spacer,
                memoryBox,
                deleteButton
        );

        return card;
    }

    private void filterAndDisplayTrips(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            tripList.setAll(currentUser.getTrips());
        } else {
            tripList.setAll(currentUser.getTrips().stream()
                    .filter(trip -> trip.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                            trip.getLocation().toLowerCase().contains(searchText.toLowerCase())).toList());
        }
        displayTripCards();
        updateTripCount();
    }

    private void updateTripCount() {
        int count = tripList.size();
        if (count == 0) {
            tripCountLabel.setText("No trips yet - start your first adventure!");
        } else if (count == 1) {
            tripCountLabel.setText("1 adventure captured");
        } else {
            tripCountLabel.setText(count + " adventures captured");
        }
    }

    @FXML
    private void handleCreateTrip() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/CreateTrip.fxml"));
            Parent createTripRoot = loader.load();

            Stage stage = (Stage) createTripButton.getScene().getWindow();
            Scene createTripScene = new Scene(createTripRoot);
            stage.setScene(createTripScene);
            stage.setTitle("Postcardly - Create New Trip");

        } catch (Exception e) {
            System.err.println("Error loading Create Trip screen:");
            e.printStackTrace();
        }
    }

    private void handleViewTrip(Trip trip) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/TripView.fxml"));
            Parent tripViewRoot = loader.load();

            // Get controller and pass the trip
            TripViewController controller = loader.getController();
            controller.setTrip(trip);

            Stage stage = (Stage) tripCardsPane.getScene().getWindow();
            Scene tripViewScene = new Scene(tripViewRoot);
            stage.setScene(tripViewScene);
            stage.setTitle("Postcardly - " + trip.getTitle());

        } catch (Exception e) {
            System.err.println("Error loading trip view:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
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

    public void setUser(User user) {
        this.currentUser = user;
        tripList.setAll(user.getTrips());
        displayTripCards();
        updateTripCount();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showPlaceholder(StackPane container) {
        container.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #fd5e61, #ffa07a); " +
                        "-fx-background-radius: 10;"
        );
        Label imageLabel = new Label("📸");
        imageLabel.setStyle("-fx-font-size: 40px;");
        container.getChildren().add(imageLabel);
    }

}