package org.cirdles.app.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.cirdles.Movie;

/**
 * Utility class to manage the state of save buttons based on input fields and table data.
 */
public class SaveButtonStateManager {

    private Button saveXMLButton;
    private Button saveBinaryButton;
    private Button saveCSVButton;
    private TextField nameField;
    private TextField releaseField;
    private ComboBox<String> genreComboBox;
    private TableView<Movie> movieTableView;

    /**
     * Constructs a new SaveButtonStateManager with the provided components.
     *
     * @param saveXMLButton   The save XML button.
     * @param saveBinaryButton The save Binary button.
     * @param saveCSVButton    The save CSV button.
     * @param nameField        The text field for movie name input.
     * @param releaseField     The text field for release year input.
     * @param genreComboBox    The combo box for genre selection.
     * @param movieTableView   The table view displaying the movies.
     */
    public SaveButtonStateManager(Button saveXMLButton, Button saveBinaryButton, Button saveCSVButton,
                                  TextField nameField, TextField releaseField, ComboBox<String> genreComboBox,
                                  TableView<Movie> movieTableView) {
        this.saveXMLButton = saveXMLButton;
        this.saveBinaryButton = saveBinaryButton;
        this.saveCSVButton = saveCSVButton;
        this.nameField = nameField;
        this.releaseField = releaseField;
        this.genreComboBox = genreComboBox;
        this.movieTableView = movieTableView;
    }

    /**
     * Updates the state of the save buttons based on the input fields and table data.
     * The buttons will be enabled when there is at least one movie in the table and the required input fields
     * (name, release year, genre) are filled. Otherwise, the buttons will be disabled.
     */
    public void updateSaveButtonsState() {
        boolean isTableEmpty = movieTableView.getItems().isEmpty();
        boolean isNameEmpty = nameField.getText().trim().isEmpty();
        boolean isReleaseEmpty = releaseField.getText().trim().isEmpty();
        boolean isGenreEmpty = genreComboBox.getSelectionModel().isEmpty();

        boolean isInputValid = !isNameEmpty && !isReleaseEmpty && !isGenreEmpty;

        saveXMLButton.setDisable(isInputValid || isTableEmpty);
        saveBinaryButton.setDisable(isInputValid || isTableEmpty);
        saveCSVButton.setDisable(isInputValid || isTableEmpty);
    }
}