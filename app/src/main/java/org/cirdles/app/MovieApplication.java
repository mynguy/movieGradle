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
        primaryStage.setMinWidth(320);
        primaryStage.setMinHeight(240);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
