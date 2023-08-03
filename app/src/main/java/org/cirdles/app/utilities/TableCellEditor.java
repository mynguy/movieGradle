package org.cirdles.app.utilities;

import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.cirdles.Movie;

public class TableCellEditor {

    private final TableView<Movie> tableView;
    private final MovieEditor movieEditor;

    public TableCellEditor(TableView<Movie> tableView, MovieEditor movieEditor) {
        this.tableView = tableView;
        this.movieEditor = movieEditor;
    }

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