package org.cirdles.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MovieApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieApplication.class.getResource("movie-view.fxml"));
        VBox root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Movie Serialization");
        stage.setScene(scene);
        stage.setMinWidth(320);
        stage.setMinHeight(240);

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}