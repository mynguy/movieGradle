/**
 * Name: My Nguyen
 * MovieTest.java
 *
 * Description: This is the driver program for Movie.java. It demonstrates how to create a Movie object and serialize
 * it to a CSV file using the Java NIO API. It also shows how to deserialize the CSV file to a new Movie object and
 * compare it to the original object for equivalence. Additionally, it tests the serialization and deserialization of
 * Movie objects to and from binary files using the BinarySerializer class.
 */
package org.cirdles;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Set<Movie> movieSet;

    @BeforeEach
    public void setUp() {
        movieSet = new TreeSet<>();
        movieSet.add(new Movie("John Wick", 2014, "Action"));
        movieSet.add(new Movie("Hereditary", 2018, "Horror"));
        movieSet.add(new Movie("The Conjuring", 2013, "Horror"));
        movieSet.add(new Movie("Inception", 2010, "Science Fiction"));
    }

    @AfterEach
    void tearDown() {
        // Clean up the files generated during testing
        File csvFile = new File("movies.csv");
        File binFile = new File("movies.bin");
        File xmlFile = new File("movies.xml");

        csvFile.delete();
        binFile.delete();
        xmlFile.delete();
    }

    @Test
    public void testEquals() {
        Set<Movie> movieSetCopy = new TreeSet<>(movieSet);
        assertEquals(movieSet, movieSetCopy);
    }

    @Test
    public void testCSVSerializeAndDeserialize() throws IOException {

        Movie.serializeSetToCSV(movieSet, "movies.csv");
        Set<Movie> deserializedSet = Movie.deserializeSetFromCSV("movies.csv");

        // Check if the deserialized set is equal to the original set
        assertEquals(movieSet, deserializedSet);
    }

    @Test
    public void testBinarySerializeAndDeserialize() throws IOException, ClassNotFoundException {

        BinarySerializer.serializeToBinary(movieSet, "movies.bin");
        Set<Movie> deserializedSet = (TreeSet<Movie>) BinarySerializer.deserializeFromBinary("movies.bin");

        // Check if the deserialized set is equal to the original set
        assertEquals(movieSet, deserializedSet);
    }

    @Test
    public void testPrettyPrint() {
        Movie movie = new Movie("Inception", 2010, "Science Fiction");
        String expectedOutput = "Movie Name: Inception\nGenre: Science Fiction\nYear: 2010";
        assertEquals(expectedOutput, movie.prettyPrint());
    }

    @Test
    public void testSerializeToXML_FileCreated() {
        String filename = "movieTest.xml";

        try {
            // Serialize the movie set to XML
            XMLSerializer.serializeToXML(new MovieSetWrapper(new TreeSet<>(movieSet)), filename);

            // Verify that the file was created
            File xmlFile = new File(filename);
            assertTrue(xmlFile.exists());

            // Delete the file after verification
            //assertTrue(xmlFile.delete());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSerializeMovieSetWrapper_ToXML_FileCreated() {
        String filename = "movieSetWrapperTest.xml";

        try {
            // Create a MovieSetWrapper object with the test movieSet
            Set<Movie> movieSet = createTestMovieSet();
            MovieSetWrapper movieSetWrapper = new MovieSetWrapper(movieSet);

            XMLSerializer.serializeToXML(movieSetWrapper, filename);

            File xmlFile = new File(filename);
            assertTrue(xmlFile.exists());

            assertTrue(xmlFile.delete());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unexpected IOException occurred: " + e.getMessage());
        }
    }

    // Utility method to create a test MovieSet
    private Set<Movie> createTestMovieSet() {
        Set<Movie> movieSet = new TreeSet<>();
        movieSet.add(new Movie("Movie 1", 2022, "Action"));
        movieSet.add(new Movie("Movie 2", 2020, "Comedy"));
        movieSet.add(new Movie("Movie 3", 2019, "Drama"));
        // Add more test movies as needed
        return movieSet;
    }
}

