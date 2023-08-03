package org.cirdles.app.utilities;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.cirdles.Movie;

public class MovieEditor {

    private TableView<Movie> movieTableView;
    private Label welcomeText;

    public MovieEditor(TableView<Movie> movieTableView, Label welcomeText) {
        this.movieTableView = movieTableView;
        this.welcomeText = welcomeText;
    }

    public void handleEditMovie(Movie movie, String newName, int newYear, String newGenre) {
        if (!newName.isEmpty() && newYear >= 1000 && newYear <= 3000 && !newGenre.equals("Select genre")) {
            movie.setName(newName);
            movie.setYear(newYear);
            movie.setGenre(newGenre);
            welcomeText.setText("Movie updated: " + movie.getName());
        } else {
            // Show the problem in welcomeText
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
