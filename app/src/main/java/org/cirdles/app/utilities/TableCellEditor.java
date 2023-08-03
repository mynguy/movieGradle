package org.cirdles.app.utilities;

import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.cirdles.Movie;

/**
 * The TableCellEditor class is a utility class that provides methods to configure cell factories and
 * event handlers for a TableView of Movie objects.
 */
public class TableCellEditor {

    private final TableView<Movie> tableView;
    private final MovieEditor movieEditor;

    /**
     * Constructs a new TableCellEditor.
     *
     * @param tableView  the TableView of Movie objects to be edited
     * @param movieEditor  the MovieEditor instance to handle movie editing operations
     */
    public TableCellEditor(TableView<Movie> tableView, MovieEditor movieEditor) {
        this.tableView = tableView;
        this.movieEditor = movieEditor;
    }

    /**
     * Configures the given TableColumn to use a TextFieldTableCell for editing movie names.
     *
     * @param column  the TableColumn to configure
     */
    public void addTextFieldCellFactory(TableColumn<Movie, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<>("name"));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            Movie movie = event.getRowValue();
            String newName = event.getNewValue().trim();
            int newYear = movie.getYear();
            String newGenre = movie.getGenre();
            movieEditor.handleEditMovie(movie, newName, newYear, newGenre);
        });
    }

    /**
     * Configures the given TableColumn to use a TextFieldTableCell with IntegerStringConverter
     * for editing movie release years.
     *
     * @param column  the TableColumn to configure
     */
    public void addIntegerFieldCellFactory(TableColumn<Movie, Integer> column) {
        column.setCellValueFactory(new PropertyValueFactory<>("year"));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(event -> {
            Movie movie = event.getRowValue();
            String newName = movie.getName();
            int newYear = event.getNewValue();
            String newGenre = movie.getGenre();
            movieEditor.handleEditMovie(movie, newName, newYear, newGenre);
        });
    }

    /**
     * Configures the given TableColumn to use a ComboBoxTableCell with genre options
     * for editing movie genres.
     *
     * @param column  the TableColumn to configure
     */
    public void addComboBoxCellFactory(TableColumn<Movie, String> column) {
        column.setCellValueFactory(new PropertyValueFactory<>("genre"));
        column.setCellFactory(ComboBoxTableCell.forTableColumn(GenreOptions.getGenreOptions()));
        column.setOnEditCommit(event -> {
            Movie movie = event.getRowValue();
            String newName = movie.getName();
            int newYear = movie.getYear();
            String newGenre = event.getNewValue();
            movieEditor.handleEditMovie(movie, newName, newYear, newGenre);
        });
    }
}