# movieGradle

### Prerequisites
Before you begin, make sure you have the following software installed on your system:

# Step 1: Download Liberica JDK 17 with JavaFX
Please note that in order to utilize JavaFX to its fullest extent, it is essential to have the complete JDK 17 version installed on your system. JavaFX, a rich user interface toolkit, is included as part of the standard JDK distribution up to JDK 8. However, starting from JDK 11, JavaFX is no longer included in the standard JDK distribution and must be added separately.

To make the most of JavaFX's features and capabilities, it's recommended to ensure you have the full JDK 17 version installed, which includes the necessary JavaFX libraries and tools. This will enable you to create interactive and visually appealing graphical user interfaces for your Java applications seamlessly.

When installing Liberica JDK 17 (BellSoft), ensure that you have selected the full JDK package to guarantee access to JavaFX and its associated functionalities. Following this approach will empower you to develop modern and engaging user interfaces for your Java applications with ease.

Download the JDK/JRE 17 for your operating system as a compressed archive and expand it anywhere you choose. If you want to make this version the default on your operating system, there are many online tutorials to follow. 

# Step 2: Configure JDK in IntelliJ IDEA (or Preferred IDE)
Open IntelliJ IDEA (or your preferred IDE).
Open the cloned project by selecting "File" -> "Open" and navigating to the project directory.
In IntelliJ IDEA, configure the JDK for the project:

Go to "File" -> "Project Structure" -> "Project."
Make sure the "Project SDK" drop-down menu is set to Liberica JDK 17 (or the compatible version you installed).
Click "Apply" and then "OK" to save the JDK configuration.

# Step 3: Build and Run the GUI Application Using Gradle (Crucial Step)
After configuring the JDK in IntelliJ IDEA, you'll need to build and run the GUI application through Gradle. This step is crucial to ensure that all necessary dependencies are correctly managed and that the application runs smoothly. 

Important: Whenever you need to run the program in the future, make sure to run it through Gradle using the tasks in the Gradle tool window on the right. This ensures proper execution with all dependencies.

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
