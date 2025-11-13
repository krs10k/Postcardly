package projects.postcardly.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import projects.postcardly.model.Memory;
import projects.postcardly.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import projects.postcardly.model.Trip;
import com.google.gson.reflect.TypeToken;

public class DataManager {

    // File location where user data will be saved
    private static final String DATA_DIRECTORY = System.getProperty("user.home") + "/Postcardly";
    private static final String DATA_FILE = DATA_DIRECTORY + "/user_data.json";

    // Gson instance for JSON serialization
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(StringProperty.class, new PropertyAdapters.StringPropertyAdapter())
            .registerTypeAdapter(SimpleStringProperty.class, new PropertyAdapters.StringPropertyAdapter())
            .registerTypeAdapter(BooleanProperty.class, new PropertyAdapters.BooleanPropertyAdapter())
            .registerTypeAdapter(SimpleBooleanProperty.class, new PropertyAdapters.BooleanPropertyAdapter())
            .registerTypeAdapter(IntegerProperty.class, new PropertyAdapters.IntegerPropertyAdapter())
            .registerTypeAdapter(SimpleIntegerProperty.class, new PropertyAdapters.IntegerPropertyAdapter())
            .registerTypeAdapter(DoubleProperty.class, new PropertyAdapters.DoublePropertyAdapter())
            .registerTypeAdapter(SimpleDoubleProperty.class, new PropertyAdapters.DoublePropertyAdapter())
            .registerTypeAdapter(ObjectProperty.class, new PropertyAdapters.ObjectPropertyAdapter())
            .registerTypeAdapter(SimpleObjectProperty.class, new PropertyAdapters.ObjectPropertyAdapter())
            .registerTypeAdapter(new TypeToken<ObservableList<Trip>>(){}.getType(), new PropertyAdapters.TripListAdapter())
            .registerTypeAdapter(new TypeToken<ObservableList<Memory>>(){}.getType(), new PropertyAdapters.MemoryListAdapter())
            .registerTypeAdapter(LocalDate.class, new PropertyAdapters.LocalDateAdapter())  // ← ADD THIS LINE
            .create();
    /**
     * Save user data to disk as JSON
     * @param user The user to save
     * @return true if successful, false if failed
     */
    public static boolean saveUser(User user) {
        if (user == null) {
            System.err.println("Cannot save null user");
            return false;
        }

        try {
            // Create directory if it doesn't exist
            Path directory = Paths.get(DATA_DIRECTORY);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                System.out.println("Created data directory: " + DATA_DIRECTORY);
            }

            // Convert user to JSON
            String json = gson.toJson(user);

            // Write to file
            try (FileWriter writer = new FileWriter(DATA_FILE)) {
                writer.write(json);
            }

            System.out.println("✓ User data saved successfully to: " + DATA_FILE);
            return true;

        } catch (IOException e) {
            System.err.println("✗ Error saving user data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load user data from disk
     * @return User object if found, null if no saved data exists
     */
    public static User loadUser() {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("No saved data found at: " + DATA_FILE);
            return null;
        }

        try (FileReader reader = new FileReader(DATA_FILE)) {
            User user = gson.fromJson(reader, User.class);
            System.out.println("✓ User data loaded successfully: " + user.getUsername());
            return user;

        } catch (IOException e) {
            System.err.println("✗ Error loading user data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if saved user data exists
     * @return true if data file exists, false otherwise
     */
    public static boolean hasSavedData() {
        return new File(DATA_FILE).exists();
    }

    /**
     * Delete saved user data (for testing or reset)
     * @return true if successful, false if failed
     */
    public static boolean deleteSavedData() {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("No data file to delete");
            return true;
        }

        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("✓ Saved data deleted successfully");
        } else {
            System.err.println("✗ Failed to delete saved data");
        }
        return deleted;
    }

    /**
     * Get the path where data is saved (for debugging)
     * @return Full path to data file
     */
    public static String getDataFilePath() {
        return DATA_FILE;
    }
}