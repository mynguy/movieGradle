package org.cirdles.utilities.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.cirdles.utilities.file.GithubFileExtractor.extractGithubFile;
import static org.cirdles.values.MovieLibrary.MOVIE_RESOURCE_EXTRACTOR;
import static org.cirdles.values.MovieConstants.*;

public enum MovieFileResources {
    ;
    public static void initLocalResources() throws IOException {
        if (MOVIE_RESOURCES_FOLDER.exists()) {
            FileUtilities.recursiveDelete(MOVIE_RESOURCES_FOLDER.toPath());
        }
        if (!MOVIE_RESOURCES_FOLDER.mkdir()) {
            throw new IOException();
        }

        if (PARAMETER_MODELS_FOLDER.exists()) {
            FileUtilities.recursiveDelete(PARAMETER_MODELS_FOLDER.toPath());
        }
        if (!PARAMETER_MODELS_FOLDER.mkdir()) {
            throw new IOException();
        }
        if (!SYNTHETIC_DATA_FOLDER.mkdir()) {
            throw new IOException();
        }

        retrieveResourceFiles(SAMPLE_DATA_FOLDER, "org.cirdles");

        System.out.println("Movie Resources loaded");
    }

    /**
     * Provides a clean copy of resource files every time MovieSerializer runs
     */
    // https://www.atlassian.com/blog/developer/2006/12/how_to_use_file_separator_when
    public static void retrieveResourceFiles(File resourceTargetFolder, String resourceFolderName)
            throws IOException {

        Path listOfResourceFiles = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsPath(resourceFolderName + "/" + "listOfResourceFiles.txt");
        if (resourceTargetFolder.exists()) {
            FileUtilities.recursiveDelete(resourceTargetFolder.toPath());
        }
        if (resourceTargetFolder.mkdir() && (null != listOfResourceFiles)) {
            List<String> fileNames = Files.readAllLines(listOfResourceFiles, ISO_8859_1);
            for (String name : fileNames) {
                if (0 < name.trim().length()) {
                    if (name.startsWith("https")) {
                        int fileNameIndex = name.lastIndexOf('/');
                        String fileName = name.substring(fileNameIndex + 1);
                        try {
                            extractGithubFile(
                                    name.trim(),
                                    resourceTargetFolder + "/" + fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        File resourceFileName = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsFile(resourceFolderName + "/" + name);
                        File resourceLocalFileName = new File(resourceTargetFolder.getCanonicalPath() + "/" + name);
                        if (null != resourceFileName) {
                            boolean renameTo = resourceFileName.renameTo(resourceLocalFileName);
                            if (!renameTo) {
                                throw new IOException();
                            }
                        }
                    }
                }
            }
        }
    }
}
