package org.cirdles.app.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GenreOptionsUtil {

    public static ObservableList<String> getGenreOptions() {
        return FXCollections.observableArrayList(
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