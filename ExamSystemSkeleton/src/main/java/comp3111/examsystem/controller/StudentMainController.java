package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.tools.MsgSender;
import comp3111.examsystem.Main;
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
        examCombox.getItems().addAll(
                "COMP3111 Software Engineering | quiz1",
                "COMP3111 Software Engineering | quiz2",
                "COMP5111 Software Engineering II | quiz1",
                "COMP5111 Software Engineering II | quiz2"
        );
    }

    @FXML
    public void openExamUI(ActionEvent event) {
        String selectedQuiz = examCombox.getValue();
        if (selectedQuiz == null) {
            showMsg("Please select an exam before starting.");
            return;
        }

        // Load the Quiz UI screen
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("QuizScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Quiz: " + selectedQuiz);
            stage.setScene(new Scene(loader.load()));

            // Pass the selected quiz to the QuizScreenController
            QuizScreenController quizScreenController = loader.getController();
            quizScreenController.setQuiz(selectedQuiz);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showMsg("Failed to load quiz screen.");
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
