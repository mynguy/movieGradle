package org.cirdles.app.utilities;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.cirdles.Movie;

import java.util.Set;

public class MovieEditor {

    private TableView<Movie> movieTableView;
    private Set<Movie> movieSet;
    private Label welcomeText;

    public MovieEditor(TableView<Movie> movieTableView, Set<Movie> movieSet, Label welcomeText) {
        this.movieTableView = movieTableView;
        this.movieSet = movieSet;
        this.welcomeText = welcomeText;
    }

    public void setMovieTableView(TableView<Movie> movieTableView) {
        this.movieTableView = movieTableView;
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

    public void handleRemoveMovie(Movie movie) {
        movieSet.remove(movie);
        movieTableView.getItems().remove(movie);
        welcomeText.setText("Movie removed: " + movie.getName());
    }
}
