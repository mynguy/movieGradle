package org.cirdles.app.utilities;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import org.cirdles.Movie;

public class MovieEditorHandler {
    private final MovieEditor movieEditor;

    public MovieEditorHandler(MovieEditor movieEditor) {
        this.movieEditor = movieEditor;
    }

    // Create a cell with a "remove" button for each movie
    public TableCell<Movie, Movie> createRemoveButtonCell() {
        return new TableCell<>() {
            private final Button removeButton = new Button("X");

            {
                removeButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    movieEditor.handleRemoveMovie(movie);
                });
            }

            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);

                if (empty || movie == null) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(5);
                    container.setAlignment(Pos.CENTER_LEFT); // Align the "X" button to the bottom left
                    container.getChildren().addAll(removeButton);
                    setGraphic(container);
                }
            }
        };
    }
}
