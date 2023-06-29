/**
 * Name: My Nguyen
 * XMLSerializer.java
 * Description: This class provides methods to serialize Java objects to XML format and deserialize objects from XML files.
 * It enables data persistence and interchangeability by storing objects in XML format. The class utilizes the Java Beans
 * XMLEncoder and XMLDecoder for the serialization and deserialization processes.
 * */
package org.cirdles;

import javax.xml.bind.*;
import java.io.*;
import java.util.Set;

/**
 * Utility class for serializing and deserializing a set of movies to/from XML.
 */
public class XMLSerializer {

    /**
     * Serializes a set of movies to XML.
     *
     * @param object   the set of movies to serialize
     * @param filename the name of the XML file to create
     * @throws IOException if an error occurs while serializing to XML
     */
    public static void serializeToXML(Set<Movie> object, String filename) throws IOException {
        try {
            MovieSetWrapper wrapper = new MovieSetWrapper();
            wrapper.setMovies(object);

            JAXBContext context = JAXBContext.newInstance(MovieSetWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, new File(filename));
        } catch (JAXBException e) {
            throw new IOException("Error occurred while serializing to XML.", e);
        }
    }

    /**
     * Deserializes a set of movies from XML.
     *
     * @param filename the name of the XML file to deserialize from
     * @return the deserialized set of movies
     * @throws IOException            if an error occurs while deserializing from XML
     * @throws ClassNotFoundException if the class for the deserialized object is not found
     */
    public static Set<?> deserializeFromXML(String filename) throws IOException, ClassNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(MovieSetWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MovieSetWrapper wrapper = (MovieSetWrapper) unmarshaller.unmarshal(new File(filename));
            return wrapper.getMovies();
        } catch (JAXBException e) {
            throw new IOException("Error occurred while deserializing from XML.", e);
        }
    }
}

