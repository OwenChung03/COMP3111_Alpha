package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainController implements Initializable {
    @FXML
    private VBox mainbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic can go here if needed
    }

    @FXML
    public void openStudentManageUI() {
        openNewWindow("StudentManagement.fxml", "Student Management");
    }

    @FXML
    public void openTeacherManageUI() {
        openNewWindow("TeacherManagement.fxml", "Teacher Management");
    }

    @FXML
    public void openCourseManageUI() {
        openNewWindow("CourseManagement.fxml", "Course Management");
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    private void openNewWindow(String fxmlFile, String title) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            VBox newBox = loader.load();

            // Create a new Stage (window)
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(newBox));

            // Show the new window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally show an error dialog
        }
    }
}