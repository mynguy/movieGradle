### README:

## Prerequisites
Before you begin, make sure you have the following software installed on your system:

# Liberica JDK 17 (BellSoft) or a compatible version
Step 1: Download Liberica JDK 17
Download Liberica JDK 17 (BellSoft) from the BellSoft website: Download Liberica JDK 17.
Follow the installation instructions for your operating system to complete the JDK installation.

# Step 2: Configure JDK in IntelliJ IDEA (or Preferred IDE)
Open IntelliJ IDEA (or your preferred IDE).
Open the cloned project by selecting "File" -> "Open" and navigating to the project directory.
In IntelliJ IDEA, configure the JDK for the project:

Go to "File" -> "Project Structure" -> "Project."
Make sure the "Project SDK" drop-down menu is set to Liberica JDK 17 (or the compatible version you installed).
Click "Apply" and then "OK" to save the JDK configuration.

# Step 3: Run the GUI Application
In IntelliJ IDEA, locate and open the MovieApplication.java file.
Right-click within the MovieApplication class and select "Run 'MovieApplication.main()'." This will launch the GUI application.
The GUI window will appear, providing input fields for entering movie details (name, release year, and genre).
Use the "Add Movie" button to add a movie to the movie set.
Use the "Save Movies" button to save the movie set to a CSV file and serialize it to binary and XML formats.
For more information and instructions about the application, click the "Help" button to display a dialog box.

# Serialization and Deserialization of Movie Sets

### README:
This repository demonstrates serialization and deserialization of movie sets using various formats, including CSV, XML, and binary. Serialization refers to the process of converting objects into a serialized format, which can be stored or transmitted, while deserialization is the reverse process of restoring objects from the serialized format.

The Movie.java file contains a class that represents a movie, with instance variables for name, release year, and genre. Additionally, there is a MovieSet class that represents a set of movies using a TreeSet data structure, ensuring unique and sorted elements based on natural ordering.

To serialize and deserialize movie sets, the repository provides implementations for CSVSerializer, XMLSerializer, and BinarySerializer classes.

### CSV Serialization and Deserialization:
The CSVSerializer class includes methods to serialize a movie set to a CSV file and deserialize it back to a set of movies.
To serialize a movie set, call the serializeSetToCSV() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from a CSV file, call the deserializeSetFromCSV() method and provide the filename as an argument. This will return the deserialized set of movies.

### XML Serialization and Deserialization:
The XMLSerializer class provides methods to serialize a movie set to an XML file and deserialize it back to a set of movies.
To serialize a movie set, use the serializeToXML() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from an XML file, use the deserializeFromXML() method and provide the filename as an argument. This will return the deserialized set of movies.

### Binary Serialization and Deserialization:
The BinarySerializer class includes methods to serialize a movie set to a binary file and deserialize it back to a set of movies.
To serialize a movie set, call the serializeToBinary() method and provide the movie set and the desired filename as arguments.
To deserialize a movie set from a binary file, call the deserializeFromBinary() method and provide the filename as an argument. This will return the deserialized set of movies.

### Graphical User Interface (GUI):
The repository now includes a GUI application for managing movie sets and performing serialization/deserialization operations using the provided classes. The MovieController class and the MovieApplication class implement the GUI functionality using JavaFX.

### The GUI application features the following:

Input fields for entering the details of a movie, including name, release year, and genre.
An "Add Movie" button that adds the movie to the movie set when clicked.
A "Save Movies" button that allows the user to save the movie set to a CSV file and serialize it to binary and XML formats.
A "Help" button that displays a dialog box with instructions and information about the application.
To run the GUI application, launch the MovieApplication class. This will open a window where you can enter movie details, add movies to the set, and save the movie set in various formats.

Serialization and deserialization provide a convenient way to store and exchange data in different formats. By using the provided classes and the GUI application, you can easily manage movie sets, perform serialization and deserialization operations, and ensure data persistence and interchangeability in your Java applications.

Feel free to explore the code, run the GUI application, and utilize the serialization and deserialization capabilities for movie sets.
