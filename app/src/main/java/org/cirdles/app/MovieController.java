package org.cirdles.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.cirdles.*;

import java.io.File;
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
    private ComboBox<String> genreComboBox;

    private Set<Movie> movieSet;

    public MovieController() {
        movieSet = new HashSet<>();
    }

    @FXML
    public void initialize() {

        // Removing focus
        nameField.setFocusTraversable(false);
        releaseField.setFocusTraversable(false);

        // Populate genre options
        genreComboBox.getItems().addAll(
                "Action",
                "Adventure",
                "Comedy",
                "Drama",
                "Fantasy",
                "Horror",
                "Romance",
                "Sci-Fi",
                "Thriller"
        );
    }

    @FXML
    protected void onHelloButtonClick() {
        String name = nameField.getText();
        String releaseStr = releaseField.getText();
        String genre = genreComboBox.getValue();

        if (!name.isEmpty() && !releaseStr.isEmpty() && genre != null) {
            try {
                int release = Integer.parseInt(releaseStr);
                if (release >= 1000 && release <= 3000) {
                    Movie movie = new Movie(name, release, genre);
                    movieSet.add(movie);
                    welcomeText.setText("Movie added: " + movie.getName());
                    nameField.clear();
                    releaseField.clear();
                    genreComboBox.setValue(null);
                } else {
                    welcomeText.setText("Invalid release year!");
                }
            } catch (NumberFormatException e) {
                welcomeText.setText("Invalid release year! Please enter a 4-digit number.");
            }
        } else {
            welcomeText.setText("Please enter movie details!");
        }
    }

    @FXML
    protected void onSaveXMLButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as XML");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

                // Show save file dialog
                Stage stage = (Stage) welcomeText.getScene().getWindow();
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    String filename = file.getPath();
                    XMLSerializer.serializeToXML(movieSet, filename);
                    welcomeText.setText("Movie data exported as XML!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while saving movies as XML!");
            }
        } else {
            welcomeText.setText("No movies to save!");
        }
    }

    @FXML
    protected void onSaveCSVButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as CSV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

                // Show save file dialog
                Stage stage = (Stage) welcomeText.getScene().getWindow();
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    String filename = file.getPath();
                    Movie.serializeSetToCSV(movieSet, filename);
                    welcomeText.setText("Movie data exported as CSV!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while saving movies as CSV!");
            }
        } else {
            welcomeText.setText("No movies to save!");
        }
    }

    @FXML
    protected void onSaveBinaryButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as Binary");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));

                // Show save file dialog
                Stage stage = (Stage) welcomeText.getScene().getWindow();
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    String filename = file.getPath();
                    BinarySerializer.serializeToBinary(movieSet, filename);
                    welcomeText.setText("Movie data saved as binary!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while saving movies as binary!");
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
                "Enter the details of a movie in the respective fields and select a genre from the dropdown menu. " +
                "Click 'Add Movie' to add it to the collection.\n" +
                "Once you have added multiple movies, use the 'File' menu to save the movie data as CSV or XML.\n\n" +
                "For further assistance, please refer to the README.\n" +
                "BY: github.com/mynguy");
        alert.showAndWait();
    }
}