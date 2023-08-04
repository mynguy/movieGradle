package org.cirdles.app.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * A utility class for displaying instructions about the Movie Application.
 */
public class AppInstructions {

    private static final String TITLE = "About";
    private static final String HEADER_TEXT = "Movie Library - Developed by My Nguyen - Github @mynguy";

    private static final String INSTRUCTIONS = "How to Use the Movie Application:\n\n"
            + "1. Enter the details of a movie in the respective fields.\n"
            + "2. Select a genre from the dropdown menu.\n"
            + "3. The movie will be automatically added to the collection.\n"
            + "4. To edit a movie's data, simply click on the respective cell in the table and make changes directly. The changes will be automatically saved.\n"
            + "5. Use the 'Save as' buttons to save the movie data in different formats:\n"
            + "   - Save as XML: Saves the movie data in XML format.\n"
            + "   - Save as CSV: Saves the movie data in CSV format.\n"
            + "   - Save as Binary: Saves the movie data in Binary format.\n"
            + "6. The 'Save as' buttons will be disabled until all the movie details are filled.\n"
            + "7. Click 'Start New Library' to clear the current session and start a new movie library.\n"
            + "8. Click 'Open Demonstration Library' to load a pre-populated demo movie library.\n"
            + "9. Click 'Close Library' to close the current movie library.\n"
            + "10. Click 'Quit' to exit the Movie Application.\n"
            + "11. For more information, refer to the documentation by clicking the 'Documentation' link in the Help menu.";

    /**
     * Shows the instructions about the Movie Application in an alert dialog.
     *
     * @param mainStage the main application window (the root of the scene).
     */
    public static void showInstructions(Stage mainStage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TITLE);
        alert.setHeaderText(HEADER_TEXT);

        TextArea textArea = new TextArea(INSTRUCTIONS);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        double textWidth = textArea.getFont().getSize() * 40;
        double textHeight = textArea.getFont().getSize() * 28;

        textArea.setPrefWidth(textWidth);
        textArea.setPrefHeight(textHeight);

        alert.getDialogPane().setContent(textArea);
        alert.initOwner(mainStage);

        double dialogWidth = textWidth + 40;
        alert.getDialogPane().setMinWidth(dialogWidth);
        alert.showAndWait();
    }
}


