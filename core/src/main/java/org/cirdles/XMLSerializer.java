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

public class XMLSerializer {

    /**
     * Serializes an object to XML format using JAXB and saves it to a file.
     *
     * @param object   the object to serialize
     * @param filename the name of the XML file to create
     * @throws IOException if an error occurs during serialization
     */
    public static void serializeToXML(Object object, String filename) throws IOException {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(filename);
            marshaller.marshal(object, file);
        } catch (JAXBException e) {
            throw new IOException("Error occurred during XML serialization.", e);
        }
    }

    /**
     * Deserializes an object from XML format using JAXB.
     *
     * @param filename the name of the XML file to deserialize from
     * @return the deserialized object
     * @throws IOException            if an error occurs during deserialization
     * @throws ClassNotFoundException if the class for the deserialized object is not found
     */
    public static Set<Movie> deserializeFromXML(String filename) throws IOException, ClassNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(MovieSetWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(filename);
            MovieSetWrapper movieSetWrapper = (MovieSetWrapper) unmarshaller.unmarshal(file);
            return movieSetWrapper.getMovieSet();
        } catch (JAXBException e) {
            throw new IOException("Error occurred during XML deserialization.", e);
        }
    }

    public static String convertXMLtoString(MovieSetWrapper set, String filename) throws IOException {
        serializeToXML(set, filename);
        return "Hi";
    }
}