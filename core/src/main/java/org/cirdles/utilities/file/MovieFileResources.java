package org.cirdles.utilities.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.cirdles.utilities.file.GithubFileExtractor.extractGithubFile;

public enum MovieFileResources {
    ;

    private static final String MOVIE_RESOURCES_FOLDER = "MovieResources/";;

    public static void main(String[] args) {
        try {
            initLocalResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initLocalResources() throws IOException {
        // Use the current working directory to get the root path of the project
        String projectRoot = System.getProperty("user.dir");
        projectRoot = Paths.get(projectRoot).getParent().toString();

        String movieResourcesFolderPath = Paths.get(projectRoot, "MovieResources").toString();
        File movieResourcesFolder = new File(movieResourcesFolderPath);

        if (movieResourcesFolder.exists()) {
            FileUtilities.recursiveDelete(movieResourcesFolder.toPath());
        }
        if (!movieResourcesFolder.mkdir()) {
            throw new IOException("Failed to create the MovieResources folder.");
        }
        String librarySampleFolderPath = Paths.get(projectRoot, "core", "src", "main", "resources", "org.cirdles", "librarySample").toString();
        retrieveResourceFiles(movieResourcesFolder, librarySampleFolderPath);
        System.out.println("\nResource files loaded.");
    }

    public static void retrieveResourceFiles(File resourceTargetFolder, String resourceFolderName) throws IOException {
        // Path to the file containing a list of resource file names
        Path listOfResourceFiles = Paths.get(resourceFolderName, "listOfResourceFiles.txt");

        if (!resourceTargetFolder.exists()) {
            if (!resourceTargetFolder.mkdir() || listOfResourceFiles == null) {
                throw new IOException("Failed to create the target folder or retrieve the list of resource files.");
            }
        }

        List<String> fileNames = Files.readAllLines(listOfResourceFiles, ISO_8859_1);

        for (String name : fileNames) {
            if (name.trim().isEmpty()) {
                continue;
            }

            if (name.startsWith("https")) {
                // If the resource is a URL (GitHub file), extract it using the GitHubFileExtractor
                int fileNameIndex = name.lastIndexOf('/');
                String fileName = name.substring(fileNameIndex + 1);
                try {
                    extractGithubFile(name.trim(), resourceTargetFolder + "/" + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Assuming the resource names are relative paths within the resourceFolderName
                File resourceFileName = new File(resourceFolderName, name);
                File resourceLocalFileName = new File(resourceTargetFolder, name);

                // Use Files.copy to copy the file contents to the target folder
                Files.copy(resourceFileName.toPath(), resourceLocalFileName.toPath());
            }
        }

        // After copying files, move them to the MOVIE_RESOURCES_FOLDER
        File movieResourcesFolder = new File(MOVIE_RESOURCES_FOLDER);
        if (!movieResourcesFolder.exists()) {
            movieResourcesFolder.mkdir();
        }
        File[] resourceFiles = resourceTargetFolder.listFiles();
        if (resourceFiles != null) {
            for (File resourceFile : resourceFiles) {
                resourceFile.renameTo(new File(movieResourcesFolder, resourceFile.getName()));
            }
        }
    }
}