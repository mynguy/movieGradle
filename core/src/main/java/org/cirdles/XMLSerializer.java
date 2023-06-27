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

    public static void serializeToXML(Object object, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(object);
        }
    }

    public static Set<?> deserializeFromXML(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Set<?>) objectIn.readObject();
        }
    }
}
