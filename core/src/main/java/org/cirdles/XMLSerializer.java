/**
 * Name: My Nguyen
 * XMLSerializer.java
 * Description: This class provides methods to serialize Java objects to XML format and deserialize objects from XML files.
 * It enables data persistence and interchangeability by storing objects in XML format. The class utilizes the Java Beans
 * XMLEncoder and XMLDecoder for the serialization and deserialization processes.
 * */
package org.cirdles;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Set;

/**
 * Utility class for serializing and deserializing objects to/from XML.
 */
public class XMLSerializer {

    /**
     * Serializes a set of objects to XML.
     *
     * @param objectSet the set of objects to serialize
     * @param filename  the name of the XML file to create
     * @throws IOException if an error occurs while serializing to XML
     */
    public static void serializeToXML(Set<?> objectSet, String filename) throws IOException {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))) {
            encoder.writeObject(objectSet);
        }
    }

    /**
     * Deserializes a set of objects from XML.
     *
     * @param filename the name of the XML file to deserialize from
     * @return the deserialized set of objects
     * @throws IOException            if an error occurs while deserializing from XML
     * @throws ClassNotFoundException if the class for the deserialized objects is not found
     */
    public static Set<?> deserializeFromXML(String filename) throws IOException, ClassNotFoundException {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))) {
            return (Set<?>) decoder.readObject();
        }
    }
}