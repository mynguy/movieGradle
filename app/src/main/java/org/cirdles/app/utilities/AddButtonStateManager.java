package org.cirdles.app.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class manages the state of the "Add Movie" button in the Movie Application.
 * The button is disabled when any of the required input fields (name, release year, and genre)
 * are empty, and it will be enabled when all the required fields are filled.
 */
public class AddButtonStateManager {

    private Button addButton;
    private TextField nameField;
    private TextField releaseField;
    private ComboBox<String> genreComboBox;

    /**
     * Constructs an AddButtonStateManager with the specified input fields.
     *
     * @param addButton      the "Add Movie" button to manage the state for
     * @param nameField      the text field for entering the movie name
     * @param releaseField   the text field for entering the movie release year
     * @param genreComboBox  the combo box for selecting the movie genre
     */
    public AddButtonStateManager(Button addButton, TextField nameField, TextField releaseField, ComboBox<String> genreComboBox) {
        this.addButton = addButton;
        this.nameField = nameField;
        this.releaseField = releaseField;
        this.genreComboBox = genreComboBox;
        updateAddMovieButtonState();
    }

    /**
     * Updates the state of the "Add Movie" button based on the input fields.
     * The button will be disabled if any of the required input fields (name, release year, and genre)
     * are empty, and it will be enabled when all the required fields are filled.
     */
    public void updateAddMovieButtonState() {
        boolean isNameEmpty = nameField.getText().trim().isEmpty();
        boolean isReleaseEmpty = releaseField.getText().trim().isEmpty();
        boolean isGenreEmpty = genreComboBox.getSelectionModel().isEmpty();

        addButton.setDisable(isNameEmpty || isReleaseEmpty || isGenreEmpty);
    }
}
