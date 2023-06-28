/**
 * Name: My Nguyen
 * XMLSerializer.java
 * Description: This class provides methods to serialize Java objects to XML format and deserialize objects from XML files.
 * It enables data persistence and interchangeability by storing objects in XML format. The class utilizes the Java Beans
 * XMLEncoder and XMLDecoder for the serialization and deserialization processes.
 * */
package org.cirdles;

import java.io.*;
import java.util.Set;

public class XMLSerializer {

    /**
     * Serializes an object to XML format and saves it to a file.
     *
     * @param object The object to be serialized.
     * @param filename The name or path of the file to save the serialized XML data.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static void serializeToXML(Object object, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(object);
        }
    }

    /**
     * Deserializes an object from XML format stored in a file and returns it as a Set.
     *
     * @param filename The name or path of the file containing the serialized XML data.
     * @return The deserialized object as a Set.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws ClassNotFoundException If the class of the object being deserialized cannot be found.
     */
    public static Set<?> deserializeFromXML(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Set<?>) objectIn.readObject();
        }
    }
}
