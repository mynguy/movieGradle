package org.cirdles.utilities.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author My Nguyen
 * @inspiredby Dr. Bowring
 */
public enum FileUtilities {
    ;
    public static void recursiveDelete(Path pathToBeDeleted)
            throws IOException {
        Files.walk(pathToBeDeleted)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

}