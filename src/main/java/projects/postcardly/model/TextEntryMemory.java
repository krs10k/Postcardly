package projects.postcardly.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class TextEntryMemory extends Memory {

    /* Text entry:
    A content attibute (The meat of the text entry)
    A mood attribute (The users current feeling at the time of the text entry) */

    private final StringProperty content;
    private final StringProperty mood;

    public TextEntryMemory(String title, LocalDate date, String description, String content) {
        super(title, date, description);
        this.content = new SimpleStringProperty(content);
        this.mood = new SimpleStringProperty("");
    }

    @Override
    public String getMemoryType() {
        return "Journal Entry";
    }

    //[Getters and Setter]
    public StringProperty contentProperty() {
        return content;
    }
    public String getContent() {
        return content.get();
    }
    public void setContent(String content) {
        this.content.set(content);
    }

    // Mood
    public StringProperty moodProperty() {
        return mood;
    }
    public String getMood() {
        return mood.get();
    }
    public void setMood(String mood) {
        this.mood.set(mood);
    }

    // Override to show word count
    @Override
    public String getSummary() {
        int wordCount = content.get().split("\\s+").length;
        return getTitle() + " (" + wordCount + " words)";
    }
}
