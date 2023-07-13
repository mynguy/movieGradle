
module movieGradle.core.main {
    exports org.cirdles;
    opens org.cirdles to java.xml.bind;
    exports org.cirdles.utilities.file;
    exports org.cirdles.values;

    requires java.desktop;
    requires java.xml.bind;
}