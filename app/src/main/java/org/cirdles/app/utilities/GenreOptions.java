package org.cirdles.app.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class that provides a list of predefined movie genres as ObservableList.
 */
public class GenreOptions {

    /**
     * Returns an ObservableList of predefined movie genres.
     *
     * @return An ObservableList containing the following movie genres:
     *         "Action", "Adventure", "Comedy", "Drama", "Fantasy", "Horror",
     *         "Romance", "Sci-Fi", "Thriller"
     */
    public static ObservableList<String> getGenreOptions() {
        return FXCollections.observableArrayList(
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
    }
}