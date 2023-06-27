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

    public static void serializeToBinary(Set<?> movieSet, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(movieSet);
        }
    }

    public static Set<?> deserializeFromBinary(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Set<?>) objectIn.readObject();
        }
    }
}