package org.cirdles;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;
import java.util.TreeSet;

/**
 * Wrapper class for a set of movies.
 */
@XmlRootElement
public class MovieSetWrapper {
    private Set<Movie> movies;

    public MovieSetWrapper() {
        movies = new TreeSet<>();
    }
    @XmlElement(name = "movie")
    public Set<Movie> getMovies() {
        return movies;
    }
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
