package org.cirdles.app.utilities;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.cirdles.Movie;

/**
 * A utility class for editing movie details in a TableView and displaying messages.
 */
public class MovieEditor {

    private TableView<Movie> movieTableView;
    private Label welcomeText;

    /**
     * Constructs a MovieEditor instance.
     *
     * @param movieTableView The TableView displaying the movie data.
     * @param welcomeText    The Label used to display messages.
     */
    public MovieEditor(TableView<Movie> movieTableView, Label welcomeText) {
        this.movieTableView = movieTableView;
        this.welcomeText = welcomeText;
    }

    /**
     * Handles editing a movie's details and updating the TableView and message Label.
     *
     * @param movie     The movie object to be edited.
     * @param newName   The new name for the movie.
     * @param newYear   The new release year for the movie.
     * @param newGenre  The new genre for the movie.
     */
    public void handleEditMovie(Movie movie, String newName, int newYear, String newGenre) {
        if (!newName.isEmpty() && newYear >= 1000 && newYear <= 3000 && !newGenre.equals("Select genre")) {
            movie.setName(newName);
            movie.setYear(newYear);
            movie.setGenre(newGenre);
            welcomeText.setText("Movie updated: " + movie.getName());
        } else {
            StringBuilder errorMessage = new StringBuilder("Error: ");
            if (newName.isEmpty()) {
                errorMessage.append("Name cannot be empty!");
            }
            if (newYear < 1000 || newYear > 3000) {
                errorMessage.append("Invalid release year!");
            }
            if (newGenre.equals("Select genre")) {
                errorMessage.append("Please select a valid genre!");
            }
            welcomeText.setText(errorMessage.toString());
            movieTableView.refresh();
        }
    }
}
