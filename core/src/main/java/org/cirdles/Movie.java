/**
 * Name: My Nguyen
 * MovieTest.java
 *
 * Description: This class contains unit tests for the Movie class.
 * It tests the equality of movie sets and the serialization and deserialization of movie sets to CSV files.
 */

package org.cirdles;

import javax.xml.bind.annotation.XmlElement;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Movie implements Comparable<Movie>, Serializable {

    private String name;
    private String genre;
    private int release;

    public Movie(String name, int release, String genre) {
        this.name = name;
        this.release = release;
        this.genre = genre;
    }

    public Movie() {
        this("", 0, "");
    }

    /**
     * This method serializes a Set of Movie objects to a CSV file.
     *
     * @param movieSet The Set of Movie objects to be serialized.
     * @param filename The name of the file to which the Movie objects will be serialized.
     * @throws IOException If an error occurs during the file writing process.
     */
    public static void serializeSetToCSV(Set<Movie> movieSet, String filename) throws IOException {
        Path filepath = Paths.get(filename);

        StringBuilder createStr = new StringBuilder();
        createStr.append("name, genre, release").append(System.getProperty("line.separator"));

        for (Movie movie : movieSet) {
            createStr.append(movie.getName()).append(", ").append(movie.getGenre()).append(", ").append(movie.getYear()).append(System.getProperty("line.separator"));
        }

        String output = createStr.toString();
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8.name()));
        out.write(output);
        out.close();
    }

    /**
     * This method deserializes a Set of Movie objects from a CSV file.
     *
     * @param filename The name of the CSV file from which to deserialize the Movie objects.
     * @return The deserialized Set of Movie objects.
     * @throws IOException If an error occurs during the file reading process.
     */
    public static Set<Movie> deserializeSetFromCSV(String filename) throws IOException {
        Path file = Paths.get(filename);
        List<String> lines = Files.readAllLines(file);

        Set<Movie> movieSet = new TreeSet<>();
        if (lines.size() > 1) {
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] stateArray = line.split(",");

                if (stateArray.length >= 3) {
                    String name = stateArray[0].trim();
                    String genre = stateArray[1].trim();
                    int release = Integer.parseInt(stateArray[2].trim());

                    Movie movie = new Movie(name, release, genre);
                    movieSet.add(movie);
                }
            }
        }
        return movieSet;
    }

    /**
     This method returns a formatted string representation of the Movie object.
     The returned string includes the movie's name, genre, and release year in a structured manner.
     @return A string representing the pretty-printed movie information.
     */
    public String prettyPrint() {
        return "Movie Name: " + name + "\nGenre: " + genre + "\nYear: " + release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return release == movie.release && Objects.equals(name, movie.name) && Objects.equals(genre, movie.genre);
    }

    @Override
    public int compareTo(Movie otherMovie) {
        return this.name.compareToIgnoreCase(otherMovie.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, genre, release);
    }
    @XmlElement
    public String getName() {
        return name;
    }
    @XmlElement
    public String getGenre() {
        return genre;
    }
    @XmlElement
    public int getYear() {
        return release;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setYear(int release) {
        this.release = release;
    }
}



