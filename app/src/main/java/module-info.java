module org.cirdles.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires movieGradle.core.main;

    opens org.cirdles.app to javafx.fxml;
    exports org.cirdles.app;
}