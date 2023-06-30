package org.cirdles.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    @FXML
    private ListView<Movie> movieListView;

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

        // Set up custom cell factory to display movie names and genres in the ListView
        movieListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Movie> call(ListView<Movie> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Movie movie, boolean empty) {
                        super.updateItem(movie, empty);
                        if (movie != null) {
                            setText(movie.getName() + " - " + movie.getGenre());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
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

    @FXML
    private void onOpenXMLButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set XML");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Set<Movie> movieSet = (Set<Movie>) XMLSerializer.deserializeFromXML(selectedFile.getPath());
                movieListView.getItems().clear();
                movieListView.getItems().addAll(movieSet);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onOpenBinaryButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set (Binary)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));

        // Show open file dialog
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String filename = file.getPath();

            try {
                Set<Movie> loadedMovieSet = (Set<Movie>) BinarySerializer.deserializeFromBinary(filename);
                if (loadedMovieSet != null && !loadedMovieSet.isEmpty()) {
                    movieSet = loadedMovieSet;
                    movieListView.getItems().clear();
                    movieListView.getItems().addAll(movieSet);
                    welcomeText.setText("Movie set loaded from Binary: " + filename);
                } else {
                    welcomeText.setText("Invalid movie set in the Binary file!");
                }
            } catch (IOException | ClassNotFoundException e) {
                welcomeText.setText("Error occurred while loading movie set from Binary file!");
            }
        }
    }

    @FXML
    protected void onOpenCSVButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set (CSV)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show open file dialog
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String filename = file.getPath();

            try {
                Set<Movie> loadedMovieSet = Movie.deserializeSetFromCSV(filename);
                if (loadedMovieSet != null && !loadedMovieSet.isEmpty()) {
                    movieSet = loadedMovieSet;
                    movieListView.getItems().clear();
                    movieListView.getItems().addAll(movieSet);
                    welcomeText.setText("Movie set loaded from CSV: " + filename);
                } else {
                    welcomeText.setText("Invalid movie set in the CSV file!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while loading movie set from CSV file!");
            }
        }
    }

    private void displayMovieSet() {
        StringBuilder movieNames = new StringBuilder();
        for (Movie movie : movieSet) {
            movieNames.append(movie.getName()).append(", ");
        }

        String displayText = movieNames.toString();
        if (displayText.endsWith(", ")) {
            displayText = displayText.substring(0, displayText.length() - 2);
        }

        welcomeText.setText("Movie Set: " + displayText);
    }
}