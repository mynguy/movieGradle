/**
 * Name: My Nguyen
 * BinarySerializer.java
 *
 * Description: This class provides methods to serialize and deserialize a Set of Movie objects to/from binary format.
 * Serialization is performed by writing the serialized object data to a file using ObjectOutputStream. Deserialization
 * reads the binary data from a file and reconstructs the Set of Movie objects using ObjectInputStream.
 */
package org.cirdles;
import java.io.*;
import java.util.Set;

public class BinarySerializer implements Serializable {

    /**
     * Serializes a Set of objects to binary format and saves it to a file.
     *
     * @param movieSet The Set of objects to be serialized.
     * @param filename The name or path of the file to save the serialized binary data.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static void serializeToBinary(Set<?> movieSet, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(movieSet);
        }
    }

    /**
     * Deserializes a Set of objects from binary format stored in a file and returns it.
     *
     * @param filename The name or path of the file containing the serialized binary data.
     * @return The deserialized Set of objects.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws ClassNotFoundException If the class of the objects being deserialized cannot be found.
     */
    public static Set<?> deserializeFromBinary(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Set<?>) objectIn.readObject();
        }
    }
}