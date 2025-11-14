package projects.postcardly.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class PictureMemory extends Memory {

    private final StringProperty imagePath;
    private final StringProperty caption;

    /**
     * Constructor for creating a new PictureMemory
     */
    public PictureMemory(String title, LocalDate date, String description, String imagePath) {
        super(title, date, description);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.caption = new SimpleStringProperty("");
    }

    @Override
    public String getMemoryType() {
        return "Picture";
    }

    // ===== IMAGE PATH =====
    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    // ===== CAPTION =====
    public StringProperty captionProperty() {
        return caption;
    }

    public String getCaption() {
        return caption.get();
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    @Override
    public String getSummary() {
        return "Photo: " + getTitle() + " - " + getDate();
    }
}