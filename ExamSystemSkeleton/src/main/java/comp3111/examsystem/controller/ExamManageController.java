package comp3111.examsystem.controller;

import comp3111.examsystem.Entity.Exam;
import comp3111.examsystem.Entity.Question;
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

public class ExamManageController implements Initializable {

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
    public TableView<Exam> ExamTable; //Question Table
    @FXML
    public TableColumn<Exam, String> examNameColumn;
    @FXML
    public TableColumn<Exam, String> courseIDColumn;
    @FXML
    public TableColumn<Exam, String> examTimeColumn;
    @FXML
    public TableColumn<Exam, String> PublishColumn;
    @FXML
    public TableView<Question> questionInExamTable;
    @FXML
    public TableColumn<Question, String> questionInExamColumn;
    @FXML
    public Button deletefromleftButton;
    @FXML
    public TextField newexamNameTextField;
    @FXML
    public ComboBox<String> CourseIDCombo;
    @FXML
    public TableColumn<Question, String> typeColumn1;
    @FXML
    public TableColumn<Question, String> scoreColumn1;
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
    @FXML
    public Button resetButton;
    public Button filterButton;
    public TextField questionTextField;
    public ComboBox<String> TypeComboBox;
    public TableColumn<Question, String> typeColumn;
    public TableColumn<Question, String> scoreColumn;
    public TableView<Question> questionTable;
    public TableColumn<Question, String> questionColumn;
    public Button refreshButton;
    public Button updateButton;

    public void initialize(URL location, ResourceBundle resources) {
    }

    //All the QuestionManageUI functions being handled
    public void resetExam(ActionEvent actionEvent) {
    }
    public void deleteExam(ActionEvent actionEvent) {
    }

    public void addExam(ActionEvent actionEvent) {
    }

    public void updateExam(ActionEvent actionEvent) {
    }

    public void filterExam(ActionEvent actionEvent) {
    }

    public void resetQuestion(ActionEvent actionEvent) {
    }

    public void filterQuestion(ActionEvent actionEvent) {
    }

    public void Deletefromleft(ActionEvent actionEvent) {

    }

    public void Addtoleft(ActionEvent actionEvent) {
    }

    public void refreshExam(ActionEvent actionEvent) {
    }

}
