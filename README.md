# movieGradle  
#
# Serialization and deserialization of Movie sets

### README:

This repository demonstrates serialization and deserialization of movie sets using various formats, including CSV, XML, and binary. Serialization refers to the process of converting objects into a serialized format, which can be stored or transmitted, while deserialization is the reverse process of restoring objects from the serialized format.

The Movie.java file contains a class that represents a movie, with instance variables for name, release year, and genre. Additionally, there is a MovieSet class that represents a set of movies using a TreeSet data structure, ensuring unique and sorted elements based on natural ordering.

To serialize and deserialize movie sets, the repository provides implementations for CSVSerializer, XMLSerializer, and BinarySerializer classes.

### CSV Serialization and Deserialization:

The CSVSerializer class includes methods to serialize a movie set to a CSV file and deserialize it back to a set of movies.
To serialize a movie set, call the serializeSetToCSV() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from a CSV file, call the deserializeSetFromCSV() method and provide the filename as an argument. This will return the deserialized set of movies.
XML Serialization and Deserialization:

The XMLSerializer class provides methods to serialize a movie set to an XML file and deserialize it back to a set of movies.
To serialize a movie set, use the serializeToXML() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from an XML file, use the deserializeFromXML() method and provide the filename as an argument. This will return the deserialized set of movies.
Binary Serialization and Deserialization:

The BinarySerializer class includes methods to serialize a movie set to a binary file and deserialize it back to a set of movies.
To serialize a movie set, call the serializeToBinary() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from a binary file, call the deserializeFromBinary() method and provide the filename as an argument. This will return the deserialized set of movies.
Additionally, the repository includes unit tests using JUnit to validate the serialization and deserialization functionality for each format. The MovieTest.java file contains test cases for CSV, XML, and binary serialization/deserialization, as well as other tests for functionality such as pretty printing of movie objects.

Serialization and deserialization provide a convenient way to store and exchange data in different formats. By using the provided classes, you can easily serialize and deserialize sets of movies, allowing for data persistence and interchangeability in your Java applications.

Feel free to explore the code and utilize the serialization and deserialization capabilities for movie sets.
