module org.cirdles.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires movieGradle.core.main;


    opens org.circle.app to javafx.fxml;
    exports org.cirdles.app;
}