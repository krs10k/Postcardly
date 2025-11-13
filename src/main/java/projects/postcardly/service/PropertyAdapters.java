package projects.postcardly.service;

import com.google.gson.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projects.postcardly.model.Trip;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


/**
 * Custom Gson adapters to serialize/deserialize JavaFX Properties
 */
public class PropertyAdapters {

    // Adapter for StringProperty
    public static class StringPropertyAdapter implements JsonSerializer<StringProperty>, JsonDeserializer<StringProperty> {
        @Override
        public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.get());
        }

        @Override
        public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return new SimpleStringProperty(json.getAsString());
        }
    }

    // Adapter for BooleanProperty
    public static class BooleanPropertyAdapter implements JsonSerializer<BooleanProperty>, JsonDeserializer<BooleanProperty> {
        @Override
        public JsonElement serialize(BooleanProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.get());
        }

        @Override
        public BooleanProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return new SimpleBooleanProperty(json.getAsBoolean());
        }
    }

    // Adapter for IntegerProperty
    public static class IntegerPropertyAdapter implements JsonSerializer<IntegerProperty>, JsonDeserializer<IntegerProperty> {
        @Override
        public JsonElement serialize(IntegerProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.get());
        }

        @Override
        public IntegerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return new SimpleIntegerProperty(json.getAsInt());
        }
    }

    // Adapter for DoubleProperty
    public static class DoublePropertyAdapter implements JsonSerializer<DoubleProperty>, JsonDeserializer<DoubleProperty> {
        @Override
        public JsonElement serialize(DoubleProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.get());
        }

        @Override
        public DoubleProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return new SimpleDoubleProperty(json.getAsDouble());
        }
    }

    // Improved Adapter for ObjectProperty that handles LocalDate properly
    public static class ObjectPropertyAdapter implements JsonSerializer<ObjectProperty<?>>, JsonDeserializer<ObjectProperty<?>> {
        @Override
        public JsonElement serialize(ObjectProperty<?> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.get() == null) {
                return JsonNull.INSTANCE;
            }
            // Serialize the actual value
            return context.serialize(src.get());
        }

        @Override
        public ObjectProperty<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            if (json.isJsonNull()) {
                return new SimpleObjectProperty<>(null);
            }

            // Try to deserialize as LocalDate if it looks like a date
            if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
                String value = json.getAsString();
                // Check if it's a date format (YYYY-MM-DD)
                if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
                    return new SimpleObjectProperty<>(date);
                }
            }

            // Otherwise, deserialize as generic Object
            return new SimpleObjectProperty<>(context.deserialize(json, Object.class));
        }
    }

    public static class TripListAdapter implements JsonSerializer<ObservableList<Trip>>, JsonDeserializer<ObservableList<Trip>> {
        @Override
        public JsonElement serialize(ObservableList<Trip> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return JsonNull.INSTANCE;
            }
            // Convert ObservableList to regular list for serialization
            return context.serialize(new ArrayList<>(src));
        }

        @Override
        public ObservableList<Trip> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            // Deserialize as ArrayList first
            List<Trip> list = context.deserialize(json, new com.google.gson.reflect.TypeToken<ArrayList<Trip>>(){}.getType());
            // Convert to ObservableList
            return FXCollections.observableArrayList(list);
        }
    }

    // Adapter for ObservableList<Memory>
    public static class MemoryListAdapter implements JsonSerializer<ObservableList<projects.postcardly.model.Memory>>, JsonDeserializer<ObservableList<projects.postcardly.model.Memory>> {
        @Override
        public JsonElement serialize(ObservableList<projects.postcardly.model.Memory> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return JsonNull.INSTANCE;
            }
            return context.serialize(new ArrayList<>(src));
        }

        @Override
        public ObservableList<projects.postcardly.model.Memory> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            List<projects.postcardly.model.Memory> list = context.deserialize(json, new com.google.gson.reflect.TypeToken<ArrayList<projects.postcardly.model.Memory>>(){}.getType());
            return FXCollections.observableArrayList(list);
        }
    }

    // Adapter for LocalDate
    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

}