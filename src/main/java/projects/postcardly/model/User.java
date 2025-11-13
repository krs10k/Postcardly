package projects.postcardly.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.time.LocalDate;

public class User {

    /*  [Essential Properties]
    username - unique identifier, display name
    email - for identification/contact (even if not sending emails)
    profilePicturePath - file path or image reference
    dateCreated - when the account was created
    lastLogin - timestamp of last app use */
    private final StringProperty username;
    private final StringProperty email;
    private String profilePicturePath;
    private final String dateCreated;
    private String lastLogin;

    /*  [Personal Information]
    firstName / lastName
    bio - short description about the user
    location - home city/country (maybe) */
    private String firstName;
    private String lastName;
    private final StringProperty bio;     // try to keep short
    // private String homeLocation;

    /*  [App-Specific Properties]
    trips - ObservableList<Trip> (collection of all their trips) */
    private final ObservableList<Trip> trips;

    /*  [Preferences/Settings]
    theme - dark mode, light mode, color scheme
    defaultView - how they prefer to see trips (grid, list, timeline)
    autoSave - boolean for auto-saving work */
    private final StringProperty theme;
    private final StringProperty defaultView;
    private final BooleanProperty autoSave;

    /* [Constructor]
    Will only prompt the new User for their name, username, email, pfp, and a bio
    upon registering */
    public User(String firstName, String lastName, String username, String email, String bio) {
        // Initialize regular fields
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = LocalDate.now().toString();
        this.lastLogin = LocalDate.now().toString();
        this.profilePicturePath = null;

        // Initialize properties
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.bio = new SimpleStringProperty(bio);
        this.theme = new SimpleStringProperty("default");
        this.defaultView = new SimpleStringProperty("default");
        this.autoSave = new SimpleBooleanProperty(false);
        this.trips = FXCollections.observableArrayList();
    }

    // Getters and Setter Methods
    // ===== USERNAME =====
    public StringProperty usernameProperty() {
        return username;
    }
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username.set(username);
    }

    // ===== EMAIL =====
    public StringProperty emailProperty() {
        return email;
    }
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getprofilePicturePath() {
        return profilePicturePath;
    }
    public void setprofilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // ===== BIO =====
    public StringProperty bioProperty() {
        return bio;
    }
    public String getBio() {
        return bio.get();
    }
    public void setBio(String bio) {
        this.bio.set(bio);
    }

    // ===== TRIPS =====
    public ObservableList<Trip> getTrips() {
        return trips;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
    }

    public int getTotalTrips() {
        return trips.size();
    }

    public int getTotalMemories() {
        int count = 0;
        for (Trip trip : trips) {
            count += trip.getMemoryCount();
        }
        return count;
    }

    // ===== THEME =====
    public StringProperty themeProperty() {
        return theme;
    }
    public String getTheme() {
        return theme.get();
    }
    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    // ===== DEFAULT VIEW =====
    public StringProperty defaultViewProperty() {
        return defaultView;
    }
    public String getDefaultView() {
        return defaultView.get();
    }
    public void setDefaultView(String defaultView) {
        this.defaultView.set(defaultView);
    }

    // ===== AUTO SAVE =====
    public BooleanProperty autoSaveProperty() {
        return autoSave;
    }
    public boolean isAutoSave() {
        return autoSave.get();
    }
    public void setAutoSave(boolean autoSave) {
        this.autoSave.set(autoSave);
    }

    public File getprofilePicturePathFile() {
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            return new File(profilePicturePath);
        }
        return null;
    }
    @Override
    public String toString() {
        return username.get() + " (" + firstName + " " + lastName + ")";
    }
}