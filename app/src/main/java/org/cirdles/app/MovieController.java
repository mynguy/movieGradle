package org.cirdles.app;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.cirdles.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovieController {

    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

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
    @FXML
    private VBox sessionContainer;
    @FXML
    private ImageView logoImageView;

    private Set<Movie> movieSet;

    public MovieController() {
        movieSet = new HashSet<>();
    }

    @FXML
    public void initialize() {
        // Removing focus
        nameField.setFocusTraversable(false);
        releaseField.setFocusTraversable(false);

        // Enable multiple selection in the ListView
        movieListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        // Set up custom cell factory to display movie details in the ListView
        movieListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Movie> call(ListView<Movie> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Movie movie, boolean empty) {
                        super.updateItem(movie, empty);
                        if (movie != null) {
                            setText(String.format("Name: %s%nRelease Year: %d%nGenre: %s",
                                    movie.getName(), movie.getYear(), movie.getGenre()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    @FXML
    protected void addMovieButtonClicked() {
        String name = nameField.getText();
        String releaseStr = releaseField.getText();
        String genre = genreComboBox.getValue();

        if (!name.isEmpty() && !releaseStr.isEmpty() && genre != null) {
            try {
                int release = Integer.parseInt(releaseStr);
                if (release >= 1000 && release <= 3000) {
                    Movie movie = new Movie(name, release, genre);
                    movieSet.add(movie);
                    movieListView.getItems().add(movie); // Add movie to the ListView
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Movie Application");

        VBox content = new VBox();
        content.setSpacing(10);

        Text helpText = new Text("This is the help documentation for the Movie Application.\n\n" +
                "Enter the details of a movie in the respective fields and select a genre from the dropdown menu. " +
                "\nClick 'Add Movie' to add it to the collection. Click 'Edit' to edit a movie's data.\n" +
                "Once you have added multiple movies, use the 'Save as' buttons to save the movie data as CSV, XML, or Binary.\n");

        Button readmeButton = new Button("Link to Documentation");
        readmeButton.setOnAction(e -> {
            // Open the link in the default browser
            hostServices.showDocument("https://github.com/mynguy/movieGradle/blob/main/README.md");
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(helpText, readmeButton);
        content.getChildren().add(vbox);

        alert.getDialogPane().setContent(content);

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

    @FXML
    protected void removeButtonClicked() {
        Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieListView.getItems().remove(selectedMovie);
            movieSet.remove(selectedMovie);
            welcomeText.setText("Movie removed: " + selectedMovie.getName());
        } else {
            welcomeText.setText("Please select a movie to remove!");
        }
    }

    @FXML
    protected void onEditButtonClick() {
        Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            Dialog<Movie> dialog = new Dialog<>();
            dialog.setTitle("Edit Movie");
            dialog.setHeaderText("Edit Movie Details");

            // Set the button types
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create the movie details form
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            nameField.setText(selectedMovie.getName());
            TextField releaseField = new TextField();
            releaseField.setPromptText("Release Year");
            releaseField.setText(String.valueOf(selectedMovie.getYear()));
            ComboBox<String> genreComboBox = new ComboBox<>();
            genreComboBox.setPromptText("Genre");
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
            genreComboBox.setValue(selectedMovie.getGenre());

            gridPane.add(new Label("Name:"), 0, 0);
            gridPane.add(nameField, 1, 0);
            gridPane.add(new Label("Release Year:"), 0, 1);
            gridPane.add(releaseField, 1, 1);
            gridPane.add(new Label("Genre:"), 0, 2);
            gridPane.add(genreComboBox, 1, 2);

            dialog.getDialogPane().setContent(gridPane);

            // Request focus on the name field by default
            Platform.runLater(nameField::requestFocus);

            // Convert the result to a movie object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String name = nameField.getText();
                    String releaseStr = releaseField.getText();
                    String genre = genreComboBox.getValue();

                    if (!name.isEmpty() && !releaseStr.isEmpty() && genre != null) {
                        try {
                            int release = Integer.parseInt(releaseStr);
                            if (release >= 1000 && release <= 3000) {
                                return new Movie(name, release, genre);
                            } else {
                                System.out.println("Invalid release year!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid release year! Please enter a 4-digit number.");
                        }
                    } else {
                        System.out.println("Please enter movie details!");
                    }
                }
                return null;
            });

            Optional<Movie> result = dialog.showAndWait();
            result.ifPresent(updatedMovie -> {
                int selectedIndex = movieListView.getSelectionModel().getSelectedIndex();
                movieListView.getItems().set(selectedIndex, updatedMovie);
                movieSet.remove(selectedMovie);
                movieSet.add(updatedMovie);
                welcomeText.setText("Movie updated: " + updatedMovie.getName());
            });
        } else {
            welcomeText.setText("Please select a movie to update!");
        }
    }

    @FXML
    protected void onNewSessionClicked() {
        // Show the session container
        sessionContainer.setVisible(true);

        // Clear the input fields
        nameField.clear();
        releaseField.clear();
        genreComboBox.setValue(null);

        // Clear the movie list view
        movieListView.getItems().clear();

        // Hide the logo when "Start New Session" button is clicked
        logoImageView.setVisible(false);

    }

    @FXML
    protected void onCloseSessionClicked() {
        sessionContainer.setVisible(false);
        // Show the logo
        logoImageView.setVisible(true);
    }

    @FXML
    protected void onQuitClicked() {
        // Get the current stage and close the application
        welcomeText.getScene().getWindow().hide();
    }
}