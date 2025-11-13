module projects.postcardly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

    opens projects.postcardly to javafx.fxml;
    opens projects.postcardly.controller to javafx.fxml;
    opens projects.postcardly.model to com.google.gson;

    exports projects.postcardly;
    exports projects.postcardly.controller;
    exports projects.postcardly.model;
}