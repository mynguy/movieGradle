package org.cirdles;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "movies")
public class MovieSetWrapper {

    private Set<Movie> movieSet;

    public MovieSetWrapper() {
        this.movieSet = new TreeSet<>();
    }

    public MovieSetWrapper(Set<Movie> movieSet) {
        this.movieSet = movieSet;
    }

    @XmlElement(name = "movie")
    public Set<Movie> getMovieSet() {
        return movieSet;
    }

    public void setMovieSet(Set<Movie> movieSet) {
        this.movieSet = movieSet;
    }
}