package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import comp3111.examsystem.controller.StudentManageController;

public class ManagerMainController implements Initializable {
    @FXML
    private VBox mainbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic can go here if needed
    }

    @FXML
    public void openStudentManageUI(ActionEvent e) {
        openNewWindow("StudentManageUI.fxml", "Student Management");
    }

    @FXML
    public void openTeacherManageUI(ActionEvent e) {
        openNewWindow("TeacherManageUI.fxml", "Teacher Management");
    }

    @FXML
    public void openCourseManageUI(ActionEvent e) {
        openNewWindow("CourseManageUI.fxml", "Course Management");
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    private void openNewWindow(String fxmlFile, String title) {
        try {
            // Create a new Stage (window)
            Stage stage = new Stage();
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            // Create a Scene from the loaded FXML
            Scene scene = new Scene(fxmlLoader.load());
            // Set the title and scene for the new stage
            stage.setTitle(title);
            stage.setScene(scene);
            // Show the new window
            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
            // Optionally, show an error dialog
        }
    }
}