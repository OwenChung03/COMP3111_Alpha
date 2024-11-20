package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.tools.MsgSender;
import comp3111.examsystem.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.tools.MsgSender.showMsg;

public class StudentMainController implements Initializable {

    @FXML
    private ListView<String> examListView;

    @FXML
    private Button startQuizButton;

    private List<Exam> exams;

    @FXML
    ComboBox<String> examCombox;

    public void initialize(URL location, ResourceBundle resources) {
        ExamLoader examLoader = new ExamLoader(); // Create an instance of ExamLoader
        try {
            // Attempt to load exams from the database
            exams = examLoader.loadExamsFromDatabase();

            // Debugging: Check if the exams list is null or empty
            if (exams == null) {
                System.out.println("Debug: exams object is null.");
                showMsg("Error: Failed to load exams. No exams data available.");
                return;
            }

            if (exams.isEmpty()) {
                System.out.println("Debug: exams list is empty.");
                showMsg("Error: Failed to load exams. No exams found in the database.");
                return;
            }

            // Populate ComboBox with exam names if exams are successfully loaded
            List<String> examNames = exams.stream()
                    .map(Exam::getExamName)
                    .toList();
            
            // Update ComboBox with the list of exam names
            examCombox.setItems(FXCollections.observableArrayList(examNames));

        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            showMsg("Error: Failed to load exams due to an exception.123");
        }
    }

    @FXML
    public void openExamUI(ActionEvent event) {
        // Get the selected exam name from the ComboBox
        String selectedExamName = examCombox.getValue();
        if (selectedExamName == null) {
            showMsg("Please select an exam before starting.");
            return;
        }

        // Find the corresponding Exam object by the selected name
        Exam selectedExam = exams.stream()
                .filter(exam -> exam.getExamName().equals(selectedExamName))
                .findFirst()
                .orElse(null);

        if (selectedExam != null) {
            // Load the Quiz UI screen
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("ExamScreen.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Quiz: " + selectedExamName);
                stage.setScene(new Scene(loader.load()));

                // Pass the selected exam to the QuizScreenController
                ExamScreenController examScreenController = loader.getController();
                examScreenController.setExam(selectedExam);  // Pass the `Exam` object

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showMsg("Failed to load quiz screen.");
            }
        } else {
            showMsg("Error: Selected exam not found.");
        }
    }


    @FXML
    public void openGradeStatistic(ActionEvent event) {
        // Logic for opening the Grade Statistics screen (e.g., loading another FXML)
        // Load the Student Main UI
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentGradeStat.fxml"));
        Stage stage1 = new Stage();
        stage1.setTitle("Grade Statistics");
        try {
            stage1.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage1.show();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
