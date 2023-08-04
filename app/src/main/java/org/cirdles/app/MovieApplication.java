/**
 * Name: My Nguyen
 * MovieApplication.java
 *
 * Main entry point for the Movie Application.
 * Sets up the graphical user interface for the movie library, allowing users to manage movie data.
 * Provides options to add, edit, and delete movies, and save data in various formats (CSV, XML, Binary).
 */

package org.cirdles.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MovieApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/cirdles/app/movie-view.fxml"));
        StackPane root = loader.load();

        MovieController controller = loader.getController();
        controller.setHostServices(getHostServices());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Movie Serialization");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
