package comp3111.examsystem.controller;

import comp3111.examsystem.Entity.Teacher;
import comp3111.examsystem.Main;
import comp3111.examsystem.tools.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import comp3111.examsystem.Entity.Question;

public class QuestionManageController implements Initializable {

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
    public TableView<Question> questionTable; //Question Table
    @FXML
    public TableColumn<Question, String> questionColumn;
    @FXML
    public TableColumn<Question, String> optionAColumn;
    @FXML
    public TableColumn<Question, String> optionBColumn;
    @FXML
    public TableColumn<Question, String> optionCColumn;
    @FXML
    public TableColumn<Question, String> optionDColumn;
    @FXML
    public TableColumn<Question, String> answerColumn;
    @FXML
    public TableColumn<Question, String> typeColumn;
    @FXML
    public TableColumn<Question, String> scoreColumn;
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
    private Database<Question> QuestionDatabase;

    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class);
        setupTableColumns();
        refreshQUI(null); // Load initial data
    }

    private void setupTableColumns() {
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionContent"));
        optionAColumn.setCellValueFactory(new PropertyValueFactory<>("OptionA"));
        optionBColumn.setCellValueFactory(new PropertyValueFactory<>("OptionB"));
        optionCColumn.setCellValueFactory(new PropertyValueFactory<>("OptionC"));
        optionDColumn.setCellValueFactory(new PropertyValueFactory<>("OptionD"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("Answer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
    }


    @FXML

    public void resetQUI(ActionEvent actionEvent) {
    }

    public void queryQUI(ActionEvent actionEvent) {
    }

    public void refreshQUI(ActionEvent actionEvent) {
        List<Question> questions = QuestionDatabase.getAll();
        questionTable.setItems(FXCollections.observableArrayList(questions));
    }

    public void addQuestion(ActionEvent actionEvent) {
        String questionContent = newQuestionTextField.getText();
        String optionA = optionATextField.getText();
        String optionB = optionBTextField.getText();
        String optionC = optionCTextField.getText();
        String optionD = optionDTextField.getText();
        String answer = AnswerTextField.getText();
        String type = newTypeComboBox.getValue();
        String score = newScoreTextField.getText();
        Question newQuestion = new Question(questionContent, optionA, optionB, optionC, optionD, answer, type, score);
        QuestionDatabase.add(newQuestion); // Assuming add method in Database class
        refreshQUI(null);
    }

    public void deleteQuestion(ActionEvent actionEvent) {
    }

    public void updateQuestion(ActionEvent actionEvent) {
    }

}
