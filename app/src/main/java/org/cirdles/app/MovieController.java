package org.cirdles.app;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import org.cirdles.*;
import org.cirdles.utilities.file.MovieFileResources;

import java.io.*;
import java.util.*;

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
    private TableView<Movie> movieTableView;
    @FXML
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, Movie> actionColumn;
    @FXML
    private VBox sessionContainer;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label statusLabel;

    private Set<Movie> movieSet;

    public MovieController() {
        movieSet = new TreeSet<>();
    }

    public void initialize() {
        // Removing focus
        nameField.setFocusTraversable(false);
        releaseField.setFocusTraversable(false);

        // Set minimum width for the columns
        actionColumn.setMinWidth(100);
        genreColumn.setMinWidth(35);
        releaseYearColumn.setMinWidth(65);
        nameColumn.setMinWidth(50);

        // Populate genre options
        genreComboBox.getItems().addAll(
                "Select genre",
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

        // Initialize TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Initialize action column
        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button removeButton = new Button("Remove");

            {
                editButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    handleEditMovie(movie);
                });

                removeButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    handleRemoveMovie(movie);
                });
            }

            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);

                if (empty || movie == null) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(5);
                    container.setAlignment(Pos.CENTER);
                    container.getChildren().addAll(editButton, removeButton);
                    setGraphic(container);
                }
            }
        });

        // Populate TableView with movieSet data
        movieTableView.getItems().addAll(movieSet);

        // Disable column sorting arrows
        nameColumn.setSortNode(null);
        releaseYearColumn.setSortNode(null);
        genreColumn.setSortNode(null);

        // Adjust input box padding
        nameField.setStyle("-fx-padding: 5;");
        releaseField.setStyle("-fx-padding: 5;");
    }
    @FXML
    protected void addMovieButtonClicked() {
        String name = nameField.getText();
        String releaseStr = releaseField.getText();
        String genre = genreComboBox.getValue();

        if (!name.isEmpty() && !releaseStr.isEmpty()) {
            try {
                int release = Integer.parseInt(releaseStr);
                if (release >= 1000 && release <= 3000) {
                    if (genre != null && !genre.equals("Select genre")) {
                        Movie movie = new Movie(name, release, genre);
                        movieSet.add(movie);
                        movieTableView.getItems().add(movie); // Add movie to the TableView
                        welcomeText.setText("Movie added: " + movie.getName());
                        nameField.clear();
                        releaseField.clear();

                        genreComboBox.getSelectionModel().select("Select genre");
                    } else {
                        welcomeText.setText("Please select a valid genre!");
                    }
                } else {
                    welcomeText.setText("Invalid release year!");
                }
            } catch (NumberFormatException e) {
                welcomeText.setText("Invalid release year! Please enter a valid number.");
            }
        } else {
            welcomeText.setText("Please enter movie details!");
        }
    }

    private void handleEditMovie(Movie movie) {
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
        nameField.setText(movie.getName());
        TextField releaseField = new TextField();
        releaseField.setPromptText("Release Year");
        releaseField.setText(String.valueOf(movie.getYear()));
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
        genreComboBox.setValue(movie.getGenre());

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
            int selectedIndex = movieTableView.getItems().indexOf(movie);
            movieTableView.getItems().set(selectedIndex, updatedMovie);
            movieSet.remove(movie);
            movieSet.add(updatedMovie);
            welcomeText.setText("Movie updated: " + updatedMovie.getName());
        });
    }

    private void handleRemoveMovie(Movie movie) {
        movieSet.remove(movie);
        movieTableView.getItems().remove(movie);
        welcomeText.setText("Movie removed: " + movie.getName());
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
                    MovieSetWrapper movieSetWrapper = new MovieSetWrapper(movieSet);

                    // Perform the serialization on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        try {

                            // Below is the line that is giving a lot of issue
                            XMLSerializer.serializeToXML(movieSetWrapper, filename);

                            welcomeText.setText("Movie data saved as XML!");
                        } catch (IOException e) {
                            e.printStackTrace();
                            welcomeText.setText("Error occurred while saving movies as XML. See console for details.");
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                welcomeText.setText("Error occurred while saving movies as XML. See console for details.");
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
        alert.setTitle("About");
        alert.setHeaderText("Movie Application");

        VBox content = new VBox();
        content.setSpacing(10);

        Text helpText = new Text("Information\n\n" +
                "Enter the details of a movie in the respective fields and select a genre from the dropdown menu. " +
                "\nClick 'Add Movie' to add it to the collection. Click 'Edit' to edit a movie's data.\n" +
                "Once you have added multiple movies, use the 'Save as' buttons to save the movie data as CSV, XML, or Binary.\n");

        VBox vbox = new VBox();
        vbox.getChildren().add(helpText);
        content.getChildren().add(vbox);

        alert.getDialogPane().setContent(content);

        alert.showAndWait();
    }

    @FXML
    protected void onOpenXMLButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set XML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Set<Movie> movieSet = XMLSerializer.deserializeFromXML(selectedFile.getPath());
                movieTableView.getItems().clear();
                movieTableView.getItems().addAll(movieSet);
                welcomeText.setText("Movie set loaded from XML");

                // Hide the logo
                logoImageView.setVisible(false);

                // Show the session container
                sessionContainer.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Reset genreComboBox
        genreComboBox.getSelectionModel().select("Select genre");
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
                    movieTableView.getItems().clear();
                    movieTableView.getItems().addAll(movieSet);
                    welcomeText.setText("Movie set loaded from Binary");

                    // Hide the logo
                    logoImageView.setVisible(false);

                    // Show the session container
                    sessionContainer.setVisible(true);
                } else {
                    welcomeText.setText("Invalid movie set in the Binary file!");
                }
            } catch (IOException | ClassNotFoundException e) {
                welcomeText.setText("Error occurred while loading movie set from Binary file!");
            }
        }
        // Reset genreComboBox
        genreComboBox.getSelectionModel().select("Select genre");
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
                    movieTableView.getItems().clear();
                    movieTableView.getItems().addAll(movieSet);
                    welcomeText.setText("Movie set loaded from CSV");

                    // Hide the logo
                    logoImageView.setVisible(false);

                    // Show the session container
                    sessionContainer.setVisible(true);
                } else {
                    welcomeText.setText("Invalid movie set in the CSV file!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while loading movie set from CSV file!");
            }

        }
        // Reset genreComboBox
        genreComboBox.getSelectionModel().select("Select genre");
    }

    @FXML
    protected void onNewSessionClicked() {
        // Show the session container
        sessionContainer.setVisible(true);

        // Clear the input fields
        nameField.clear();
        releaseField.clear();
        genreComboBox.getSelectionModel().select("Select genre");

        // Clear the movie table view
        movieTableView.getItems().clear();

        // Hide the logo when "Start New Session" button is clicked
        logoImageView.setVisible(false);
    }

    @FXML
    protected void onCloseSessionClicked() {
        sessionContainer.setVisible(false); // Hide the session container

        // Clear the input fields
        nameField.clear();
        releaseField.clear();
        genreComboBox.setValue(null);

        movieTableView.getItems().clear();

        logoImageView.setVisible(true);

        welcomeText.setText("");
    }

    @FXML
    protected void onQuitClicked() {
        // Get the current stage and close the application
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void openDemonstrationSessionMenuItemAction() {
        try {
            MovieFileResources.initLocalResources();

            String csvFilePath = "MovieResources/movieSetExample.csv";

            // Load the movieSetExample.csv file from the resources folder
            movieSet = Movie.deserializeSetFromCSV(csvFilePath);

            movieTableView.getItems().clear();
            movieTableView.getItems().addAll(movieSet);
            welcomeText.setText("Movie set loaded from CSV");

            logoImageView.setVisible(false);
            sessionContainer.setVisible(true);

            // Reset genreComboBox
            genreComboBox.getSelectionModel().select("Select genre");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void openDocumentation() {
        hostServices.showDocument("https://github.com/mynguy/movieGradle/blob/main/README.md");
    }
}