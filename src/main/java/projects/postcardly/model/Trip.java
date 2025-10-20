package projects.postcardly.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class Trip {

    // Properties
    private final StringProperty title;
    private final StringProperty location;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final StringProperty description;
    private final StringProperty coverImagePath;
    private final ObservableList<Memory> memories;
    private final BooleanProperty favorite;

    // Constructor
    public Trip(String title, String location, LocalDate startDate, LocalDate endDate) {
        this.title = new SimpleStringProperty(title);
        this.location = new SimpleStringProperty(location);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.description = new SimpleStringProperty("");
        this.coverImagePath = new SimpleStringProperty("");
        this.memories = FXCollections.observableArrayList();
        this.favorite = new SimpleBooleanProperty(false);
    }

    // Title property methods
    public StringProperty titleProperty() {
        return title;
    }
    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    // Location property methods
    public StringProperty locationProperty() {
        return location;
    }
    public String getLocation() {
        return location.get();
    }
    public void setLocation(String location) {
        this.location.set(location);
    }

    // Start Date property methods
    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }
    public LocalDate getStartDate() {
        return startDate.get();
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    // End Date property methods
    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }
    public LocalDate getEndDate() {
        return endDate.get();
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    // Description property methods
    public StringProperty descriptionProperty() {
        return description;
    }
    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    // Cover Image Path property methods
    public StringProperty coverImagePathProperty() {
        return coverImagePath;
    }
    public String getCoverImagePath() {
        return coverImagePath.get();
    }
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath.set(coverImagePath);
    }

    // Memories list
    public ObservableList<Memory> getMemories() {
        return memories;
    }
    public void addMemory(Memory memory) {
        memories.add(memory);
    }
    public void removeMemory(Memory memory) {
        memories.remove(memory);
    }

    // Favorite property methods
    public BooleanProperty favoriteProperty() {
        return favorite;
    }
    public boolean isFavorite() {
        return favorite.get();
    }
    public void setFavorite(boolean favorite) {
        this.favorite.set(favorite);
    }

    // Utility methods
    public int getMemoryCount() {
        return memories.size();
    }
    public long getDurationInDays() {
        if (startDate.get() != null && endDate.get() != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate.get(), endDate.get());
        }
        return 0;
    }

    @Override
    public String toString() {
        return title.get() + " - " + location.get();
    }
}
