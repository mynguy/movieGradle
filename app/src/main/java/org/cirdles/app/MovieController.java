package org.cirdles.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import org.cirdles.Movie;
import org.cirdles.BinarySerializer;
import org.cirdles.XMLSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MovieController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nameField;
    @FXML
    private TextField releaseField;
    @FXML
    private TextField genreField;
    @FXML
    private Button helpButton;

    private Set<Movie> movieSet;

    public MovieController() {
        movieSet = new HashSet<>();
    }
    @FXML
    protected void onHelloButtonClick() {
        String name = nameField.getText();
        String releaseStr = releaseField.getText();
        String genre = genreField.getText();

        if (!name.isEmpty() && !releaseStr.isEmpty() && !genre.isEmpty()) {
            try {
                int release = Integer.parseInt(releaseStr);
                Movie movie = new Movie(name, release, genre);
                movieSet.add(movie);
                welcomeText.setText("Movie added: " + movie.getName());
                nameField.clear();
                releaseField.clear();
                genreField.clear();
            } catch (NumberFormatException e) {
                welcomeText.setText("Invalid release year!");
            }
        } else {
            welcomeText.setText("Please enter movie details!");
        }
    }
    @FXML
    protected void onSaveButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                String filename = "movies.csv";
                Movie.serializeSetToCSV(movieSet, filename);
                welcomeText.setText("Movie data exported to CSV, binary, and XML files!");

                // Serialize to binary
                String binaryFilename = "movies.bin";
                BinarySerializer.serializeToBinary(movieSet, binaryFilename);

                // Serialize to XML
                String xmlFilename = "movies.xml";
                XMLSerializer.serializeToXML(movieSet, xmlFilename);
            } catch (IOException e) {
                welcomeText.setText("Error occurred while saving movies!");
            }
        } else {
            welcomeText.setText("No movies to save!");
        }
    }
    @FXML
    protected void onHelpButtonClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Movie Application");
        alert.setContentText("This is the help documentation for the Movie Application.\n\n" +
                "Enter the details of a movie in the respective fields and click 'Add Movie' to add it to the collection.\n" +
                "Once you have added multiple movies, click 'Save Movies' to export the movie data to CSV, binary, and XML files.\n\n" +
                "For further assistance, please refer to the README.\n" +
                "BY: github.com/mynguy");
        alert.showAndWait();
    }
}
