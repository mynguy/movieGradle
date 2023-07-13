package org.cirdles.values;


import org.cirdles.utilities.file.ResourceExtractor;
import org.cirdles.utilities.file.MovieFileResources;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author James F. Bowring
 */
public enum MovieLibrary {
    ;
    public static final String VERSION;
    public static final String RELEASE_DATE;

    public static final StringBuilder ABOUT_WINDOW_CONTENT = new StringBuilder();
    public static final StringBuilder CONTRIBUTORS_CONTENT = new StringBuilder();
    public static final StringBuilder SUPPORTERS_CONTENT = new StringBuilder();

    public static final ResourceExtractor MOVIE_RESOURCE_EXTRACTOR = new ResourceExtractor(MovieLibrary.class);

    static {
        String version = "version";
        String releaseDate = "date";

        Path resourcePath = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsPath("version.txt");

        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(resourcePath, charset)) {
            String line = reader.readLine();
            if (null != line) {
                String[] versionText = line.split("=");
                version = versionText[1];
            }

            line = reader.readLine();
            if (null != line) {
                String[] versionDate = line.split("=");
                releaseDate = versionDate[1];
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        // format is "x.y.z"
        VERSION = version;
        RELEASE_DATE = releaseDate;

        // get content for about window
        resourcePath = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsPath("docs/aboutContent.txt");
        try (BufferedReader reader = Files.newBufferedReader(resourcePath, charset)) {
            String thisLine;
            while (null != (thisLine = reader.readLine())) {
                ABOUT_WINDOW_CONTENT.append(thisLine);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        resourcePath = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsPath("docs/contributorsContent.txt");
        try (BufferedReader reader = Files.newBufferedReader(resourcePath, charset)) {
            String thisLine;

            while (null != (thisLine = reader.readLine())) {
                CONTRIBUTORS_CONTENT.append(thisLine);
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        resourcePath = MOVIE_RESOURCE_EXTRACTOR.extractResourceAsPath("docs/supportersContent.txt");
        try (BufferedReader reader = Files.newBufferedReader(resourcePath, charset)) {
            String thisLine;

            while (null != (thisLine = reader.readLine())) {
                SUPPORTERS_CONTENT.append(thisLine);
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        try {
            MovieFileResources.initLocalResources();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
