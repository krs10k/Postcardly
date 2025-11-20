package projects.postcardly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projects.postcardly.model.User;

public class PostcardlyApp extends Application {

    private static Stage primaryStage;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load splash screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projects/postcardly/SplashScreen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Postcardly");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Get the currently logged-in user
     * @return current user, or null if no user is set
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the currently logged-in user
     * @param user The user to set as current
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Current user set to: " + (user != null ? user.getUsername() : "null"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}