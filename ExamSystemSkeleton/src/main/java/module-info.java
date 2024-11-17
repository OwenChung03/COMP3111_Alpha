module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens comp3111.examsystem to javafx.fxml;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    opens comp3111.examsystem.Entity to javafx.base; // Allow reflection for JavaFX
    // If you have other packages with FXML controllers, you may need to open those too
    exports comp3111.examsystem.controller;
}