module projects.postcardly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens projects.postcardly to javafx.fxml;
    exports projects.postcardly;
    exports projects.postcardly.controller;
    opens projects.postcardly.controller to javafx.fxml;
}