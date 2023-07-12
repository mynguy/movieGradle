
module movieGradle.core.main {
    exports org.cirdles;

    opens org.cirdles to java.xml.bind;

    requires java.desktop;
    requires java.xml.bind;
}