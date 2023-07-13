package org.cirdles.values;

import java.io.File;

public enum MovieConstants {
    ;

    public static final File MOVIE_RESOURCES_FOLDER = new File("MovieResources");

    public static final File PARAMETER_MODELS_FOLDER = new File(MOVIE_RESOURCES_FOLDER.getAbsolutePath() + File.separator + "ParameterModels");

    public static final File SYNTHETIC_DATA_FOLDER = new File(MOVIE_RESOURCES_FOLDER.getAbsolutePath() + File.separator + "SyntheticData");

    public static final File SAMPLE_DATA_FOLDER = new File(MOVIE_RESOURCES_FOLDER.getAbsolutePath() + File.separator + "SampleData");

}