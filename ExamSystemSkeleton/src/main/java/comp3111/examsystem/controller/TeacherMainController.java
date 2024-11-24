package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main interface of the teacher in the examination system.
 * This class manages the navigation to different UI components such as question management,
 * exam management, and grade statistics.
 */
public class TeacherMainController implements Initializable {

    /**
     * Initializes the controller after its root element has been processed.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code can go here if needed
    }

    /**
     * Opens the Question Management UI when called.
     * This method loads the QuestionManageUI FXML and displays it in a new window.
     */
    @FXML
    public void openQuestionManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("QuestionManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Question Bank Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    /**
     * Opens the Exam Management UI when called.
     * This method loads the ExamManageUI FXML and displays it in a new window.
     */
    @FXML
    public void openExamManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExamManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    /**
     * Opens the Grade Statistics UI when called.
     * This method loads the TeacherGradeStatistic FXML and displays it in a new window.
     */
    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherGradeStatistic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    /**
     * Exits the application when called.
     * This method terminates the Java application.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
}