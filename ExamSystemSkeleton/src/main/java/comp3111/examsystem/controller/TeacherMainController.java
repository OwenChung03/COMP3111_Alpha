package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMainController implements Initializable {
    public static class QuestionExampleClass {
        public String getStudentName() {
            return "student";
        }
        public String getCourseNum() {
            return "comp3111";
        }
        public String getExamName() {
            return "final";
        }
        public String getScore() {
            return "100";
        }
        public String getFullScore() {
            return "100";
        }
        public String getTimeSpend() {
            return "60";
        }
    }
    public static class ExamExampleClass {

        public String getExamName() {
            return "final";
        }
        public String getCourseID() {
            return "COMP3111";
        }
        public String getExamTime() {
            return "100";
        }
        public String getPublish() {
            return "Yes";
        }
    }
    @FXML
    public VBox Mainbox; //mainbox for QUI
    @FXML
    public TextField questionTextField; //usage in QUI filter
    @FXML
    public ComboBox<String> TypeComboBox; //usage in QUI filter
    @FXML
    public TextField scoreTextField; //usage in QUI filter
    @FXML
    public Button resetButton; //usage in QUI filter
    @FXML
    public Button filterButton; //usage in QUI filter
    @FXML
    public TableView<QuestionExampleClass> questionTable; //Question Table
    @FXML
    public TableColumn<QuestionExampleClass, String> questionColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> optionAColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> optionBColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> optionCColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> optionDColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> answerColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> typeColumn;
    @FXML
    public TableColumn<QuestionExampleClass, String> scoreColumn;
    @FXML
    public TextField newQuestionTextField;
    @FXML
    public TextField optionATextField;
    @FXML
    public TextField optionBTextField;
    @FXML
    public TextField optionCTextField;
    @FXML
    public TextField optionDTextField;
    @FXML
    public TextField newScoreTextField;
    @FXML
    public ComboBox<String> newTypeComboBox;
    @FXML
    public TextField AnswerTextField;
    @FXML
    public Button addButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    //Complete all the requirement of QuestionManage UI
    @FXML
    public VBox MainBox2;
    @FXML
    public TextField examNameTextField;
    @FXML
    public ComboBox<String> CourseIDComboBox;
    @FXML
    public ComboBox<String> PublishComboBox;
    @FXML
    public Button resetButton1;
    @FXML
    public Button filterButton1;
    @FXML
    public TextField scoreTextField1;
    @FXML
    public TableView<ExamExampleClass> ExamTable; //Question Table
    @FXML
    public TableColumn<ExamExampleClass, String> examNameColumn;
    @FXML
    public TableColumn<ExamExampleClass, String> courseIDColumn;
    @FXML
    public TableColumn<ExamExampleClass, String> examTimeColumn;
    @FXML
    public TableColumn<ExamExampleClass, String> PublishColumn;
    @FXML
    public TableView<QuestionExampleClass> questionInExamTable;
    @FXML
    public TableColumn<QuestionExampleClass, String> questionInExamColumn;
    @FXML
    public Button deletefromleftButton;
    @FXML
    public TextField newexamNameTextField;
    @FXML
    public ComboBox<String> CourseIDCombo;
    @FXML
    public TableColumn<QuestionExampleClass, String> typeColumn1;
    @FXML
    public TableColumn<QuestionExampleClass, String> scoreColumn1;
    @FXML
    public Button addtoleftButton;
    @FXML
    public TextField newexamTimeTextField;
    @FXML
    public ComboBox<String> PublishCombo;
    @FXML
    public Button deleteButton1;
    @FXML
    public Button addButton1;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openQuestionManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("QuestionManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Question Bank Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openExamManageUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExamManageUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Management");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherGradeStatistic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

}
