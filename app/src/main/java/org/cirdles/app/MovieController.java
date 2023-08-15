/**
 * Name: My Nguyen
 * MovieController.java
 *
 * Description: This class is the controller for the Movie Application GUI.
 * It manages the user interface, interactions, and data processing for the application.
 * The controller initializes the application, handles movie editing, and manages movie data.
 * It allows users to add, edit, and delete movies, and save movie data in different formats (CSV, XML, Binary).
 * Users can start a new movie library, open a demonstration library, and close the current library.
 * The controller also provides help and documentation for using the application.
 */

package org.cirdles.app;

import javafx.application.HostServices;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import org.cirdles.*;
import org.cirdles.app.utilities.*;

import java.io.*;
import java.util.*;

public class MovieController {
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private Label welcomeText;
    @FXML
    private TextField nameField, releaseField;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private TableView<Movie> movieTableView;
    @FXML
    private TableColumn<Movie, String> nameColumn, genreColumn;
    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;
    @FXML
    private VBox sessionContainer;
    @FXML
    private ImageView logoImageView;
    @FXML
    private MenuItem openRecentLibraryMenuItem;
    @FXML
    private Button saveXMLButton, deleteButton, saveCSVButton, saveBinaryButton, addMovieButton;

    private Set<Movie> movieSet;
    private MovieEditor movieEditor;
    private AddButtonStateManager addButtonStateManager;
    private SaveButtonStateManager saveButtonStateManager;
    private HostServices hostServices;
    private Set<Movie> recentLibrarySet = new TreeSet<>();
    public MovieController() {
        movieSet = new TreeSet<>();
    }

    /**
     * Initializes the Movie Application and sets up various components and event listeners.
     * - Creates a MovieEditor and TableCellEditor to handle movie data and cell editing.
     * - Populates the genreComboBox with genre options.
     * - Configures cell factories for name, release year, and genre columns in the TableView.
     * - Sets the TableView to be editable and populates it with movies from the movieSet.
     * - Manages the state of "Save as" buttons with SaveButtonStateManager.
     * - Disables the deleteButton initially, enabling it when a movie is selected in the TableView.
     * - Manages the state of the "Add Movie" button with AddButtonStateManager.
     * - Updates the "Add Movie" button state based on changes in the input fields.
     */
    public void initialize() {
        movieEditor = new MovieEditor(movieTableView, welcomeText);
        TableCellEditor tableCellEditor = new TableCellEditor(movieTableView, movieEditor);

        genreComboBox.getItems().addAll(GenreOptions.getGenreOptions());

        tableCellEditor.addTextFieldCellFactory(nameColumn);
        tableCellEditor.addIntegerFieldCellFactory(releaseYearColumn);
        tableCellEditor.addComboBoxCellFactory(genreColumn);

        movieTableView.setEditable(true);
        movieTableView.getItems().addAll(movieSet);

        saveButtonStateManager = new SaveButtonStateManager(
                saveXMLButton, saveBinaryButton, saveCSVButton, nameField, releaseField, genreComboBox, movieTableView
        );
        saveButtonStateManager.updateSaveButtonsState();
        movieTableView.getItems().addListener((InvalidationListener) observable -> {
            saveButtonStateManager.updateSaveButtonsState();
        });

        deleteButton.setDisable(true);
        movieTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteButton.setDisable(newValue == null);
        });

        addButtonStateManager = new AddButtonStateManager(addMovieButton, nameField, releaseField, genreComboBox);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonStateManager.updateAddMovieButtonState();
        });
        releaseField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonStateManager.updateAddMovieButtonState();
        });
        genreComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            addButtonStateManager.updateAddMovieButtonState();
        });
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

                        recentLibrarySet = new TreeSet<>(movieSet);
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
        saveButtonStateManager.updateSaveButtonsState();

        updateOpenRecentLibraryMenuItemState();
    }

    @FXML
    protected void onSaveXMLButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as XML");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

                Stage stage = (Stage) welcomeText.getScene().getWindow();
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    String filename = file.getPath();
                    MovieSetWrapper movieSetWrapper = new MovieSetWrapper(movieSet);

                    try {
                        XMLSerializer.serializeToXML(movieSetWrapper, filename);
                        welcomeText.setText("Movie data saved as XML!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        welcomeText.setText("Error occurred while saving movies as XML. See console for details.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                welcomeText.setText("Error occurred while saving movies as XML. See console for details.");
            }
        } else {
            welcomeText.setText("No movies to save!");
        }
        recentLibrarySet = new TreeSet<>(movieTableView.getItems());
    }

    @FXML
    protected void onSaveCSVButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as CSV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

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
        recentLibrarySet = new TreeSet<>(movieTableView.getItems());
    }

    @FXML
    protected void onSaveBinaryButtonClick() {
        if (!movieSet.isEmpty()) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Movies as Binary");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));

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
        recentLibrarySet = new TreeSet<>(movieTableView.getItems());
    }

    @FXML
    protected void onHelpButtonClick() {
        Stage mainStage = (Stage) welcomeText.getScene().getWindow();
        AppInstructions.showInstructions(mainStage);
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
                logoImageView.setVisible(false);

                sessionContainer.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        genreComboBox.getSelectionModel().select("Select genre");
        saveButtonStateManager.updateSaveButtonsState();
    }

    @FXML
    protected void onOpenBinaryButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set (Binary)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));

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

                    logoImageView.setVisible(false);
                    sessionContainer.setVisible(true);
                } else {
                    welcomeText.setText("Invalid movie set in the Binary file!");
                }
            } catch (IOException | ClassNotFoundException e) {
                welcomeText.setText("Error occurred while loading movie set from Binary file!");
            }
        }
        genreComboBox.getSelectionModel().select("Select genre");
        saveButtonStateManager.updateSaveButtonsState();
    }

    @FXML
    protected void onOpenCSVButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Movie Set (CSV)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

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
                    logoImageView.setVisible(false);
                    sessionContainer.setVisible(true);
                } else {
                    welcomeText.setText("Invalid movie set in the CSV file!");
                }
            } catch (IOException e) {
                welcomeText.setText("Error occurred while loading movie set from CSV file!");
            }

        }
        genreComboBox.getSelectionModel().select("Select genre");
        saveButtonStateManager.updateSaveButtonsState();
    }

    @FXML
    protected void onNewSessionClicked() {
        boolean shouldSave = !movieSet.isEmpty();

        if (shouldSave) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Do you want to save the current library before starting a new one?",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Save Current Library");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to save the current library before starting a new one?");

            Stage mainStage = (Stage) welcomeText.getScene().getWindow();
            alert.initOwner(mainStage);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                welcomeText.setText("Make sure to save file!");
                return;
            }
        }

        sessionContainer.setVisible(true);
        sessionContainer.requestFocus();

        movieSet.clear();
        nameField.clear();
        releaseField.clear();
        genreComboBox.getSelectionModel().select("Select genre");

        movieTableView.getItems().clear();
        logoImageView.setVisible(false);
        welcomeText.setText("");

        saveButtonStateManager.updateSaveButtonsState();
        updateOpenRecentLibraryMenuItemState();
    }

    @FXML
    protected void onCloseSessionClicked() {

        sessionContainer.setVisible(false);

        nameField.clear();
        releaseField.clear();
        genreComboBox.setValue(null);

        movieTableView.getItems().clear();
        logoImageView.setVisible(true);
        welcomeText.setText("");

        clearRecentLibrary();
        updateOpenRecentLibraryMenuItemState();
    }

    @FXML
    protected void onQuitClicked() {
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void openDemonstrationSessionMenuItemAction() {

        updateOpenRecentLibraryMenuItemState();
        recentLibrarySet = new TreeSet<>(movieTableView.getItems());

        try {
            sessionContainer.requestFocus();

            String csvFilePath = "MovieResources/movieSetExample.csv";
            File csvFile = new File(csvFilePath);

            if (!csvFile.exists()) {
                throw new FileNotFoundException("movieSetExample.csv not found.");
            }

            Set<Movie> loadedMovieSet = Movie.deserializeSetFromCSV(csvFile.getAbsolutePath());
            if (loadedMovieSet != null && !loadedMovieSet.isEmpty()) {
                movieSet = loadedMovieSet;
                movieTableView.getItems().clear();
                movieTableView.getItems().addAll(movieSet);

                welcomeText.setText("Movie set loaded from CSV");
                logoImageView.setVisible(false);
                sessionContainer.setVisible(true);
                genreComboBox.getSelectionModel().select("Select genre");

                saveButtonStateManager.updateSaveButtonsState();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedMovie() {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieSet.remove(selectedMovie);
            movieTableView.getItems().remove(selectedMovie);
            welcomeText.setText(selectedMovie.getName() + " has been deleted.");
        }
    }

    @FXML
    protected void openDocumentation() {
        hostServices.showDocument("https://github.com/mynguy/movieGradle/blob/main/README.md");
    }

    @FXML
    protected void onOpenRecentLibraryClicked() {
        if (recentLibrarySet != null) {
            movieTableView.getItems().clear();
            movieTableView.getItems().addAll(recentLibrarySet);

            sessionContainer.setVisible(true);
            logoImageView.setVisible(false);
            welcomeText.setText("Opened most recent library");
            genreComboBox.getSelectionModel().select("Select genre");

            saveButtonStateManager.updateSaveButtonsState();
        }
    }

    private void updateOpenRecentLibraryMenuItemState() {
        openRecentLibraryMenuItem.setDisable(recentLibrarySet.isEmpty());
    }

    private void clearRecentLibrary() {
        recentLibrarySet.clear();
        updateOpenRecentLibraryMenuItemState();
    }
}