package org.cirdles.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
}