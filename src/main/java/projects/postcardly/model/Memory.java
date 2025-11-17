package projects.postcardly.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public abstract class Memory {

    // Common properties all memories share
    private final StringProperty title;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty description;
    private final StringProperty location;

    // Constructor
    public Memory(String title, LocalDate date, String description) {
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty("");
    }

    // Abstract method - each memory type implements this differently
    public abstract String getMemoryType();

    // Common behavior - can be overridden if needed
    public String getSummary() {
        return title.get() + " - " + date.get();
    }

    // ===== TITLE =====
    public StringProperty titleProperty() {
        return title;
    }
    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    // ===== DATE =====
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public LocalDate getDate() {
        return date.get();
    }
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    // ===== DESCRIPTION =====
    public StringProperty descriptionProperty() {
        return description;
    }
    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    // ===== LOCATION =====
    public StringProperty locationProperty() {
        return location;
    }
    public String getLocation() {
        return location.get();
    }
    public void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public String toString() {
        return getMemoryType() + ": " + title.get();
    }
}