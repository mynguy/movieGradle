package org.cirdles;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.TreeSet;

@XmlRootElement
public class MovieSetWrapper {
    private TreeSet<Movie> movieSet;

    // Default constructor for JAXB
    public MovieSetWrapper() {
    }

    public MovieSetWrapper(TreeSet<Movie> movieSet) {
        this.movieSet = movieSet;
    }

    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    public TreeSet<Movie> getMovieSet() {
        return movieSet;
    }

    public void setMovieSet(TreeSet<Movie> movieSet) {
        this.movieSet = movieSet;
    }
}