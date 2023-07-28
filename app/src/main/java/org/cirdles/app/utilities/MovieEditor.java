package org.cirdles.app.utilities;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.cirdles.Movie;

import java.util.Optional;
import java.util.Set;

/**
 * A utility class to handle editing and removing movies using dialogs.
 */
public class MovieEditor {

    private TableView<Movie> movieTableView;
    private Set<Movie> movieSet;
    private Label welcomeText;

    /**
     * Constructs a new MovieEditor with the provided TableView, Set of movies, and a Label for displaying messages.
     *
     * @param movieTableView The TableView to display the list of movies.
     * @param movieSet       The Set of movies to be edited and removed.
     * @param welcomeText    The Label to display messages about movie updates and removals.
     */
    public MovieEditor(TableView<Movie> movieTableView, Set<Movie> movieSet, Label welcomeText) {
        this.movieTableView = movieTableView;
        this.movieSet = movieSet;
        this.welcomeText = welcomeText;
    }

    /**
     * Sets the TableView used to display the list of movies.
     *
     * @param movieTableView The TableView to be set.
     */
    public void setMovieTableView(TableView<Movie> movieTableView) {
        this.movieTableView = movieTableView;
    }

    /**
     * Opens a dialog to edit the details of a movie.
     *
     * @param movie The movie to be edited.
     */
    public void handleEditMovie(Movie movie) {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Edit Movie");
        dialog.setHeaderText("Edit Movie Details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

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
        genreComboBox.getItems().addAll(GenreOptions.getGenreOptions());
        genreComboBox.setValue(movie.getGenre());

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Release Year:"), 0, 1);
        gridPane.add(releaseField, 1, 1);
        gridPane.add(new Label("Genre:"), 0, 2);
        gridPane.add(genreComboBox, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(nameField::requestFocus);

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

    /**
     * Removes a movie from the TableView and the Set of movies.
     *
     * @param movie The movie to be removed.
     */
    public void handleRemoveMovie(Movie movie) {
        movieSet.remove(movie);
        movieTableView.getItems().remove(movie);
        welcomeText.setText("Movie removed: " + movie.getName());
    }
}