package projects.postcardly.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class PostcardMemory extends Memory {

    /* Postcard Properties:
    an image and a message */
    private final StringProperty imagePath;
    private final StringProperty message;

    public PostcardMemory(String title, LocalDate date, String description, String imagePath) {
        super(title, date, description);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.message = new SimpleStringProperty("");
    }

    @Override
    public String getMemoryType() {
        return "Postcard";
    }

    // [Getters and Setters]
    // Image
    public StringProperty imagePathProperty() {
        return imagePath;
    }
    public String getImagePath() {
        return imagePath.get();
    }
    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }
    // Message
    public StringProperty messageProperty() {
        return message;
    }
    public String getMessage() {
        return message.get();
    }
    public void setMessage(String message) {
        this.message.set(message);
    }
}
