package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExamManageController implements Initializable {

    //Complete all the requirement of QuestionManage UI
    @FXML
    public VBox MainBox2;
    @FXML
    public TextField examNameTextField;
    @FXML
    public ComboBox<Long> CourseIDComboBox;
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
    public TableColumn<Question, String> typeColumn1;
    @FXML
    public TableColumn<Question, String> scoreColumn1;
    @FXML
    public Button deletefromleftButton;
    @FXML
    public TextField newexamNameTextField;
    @FXML
    public ComboBox<String> CourseIDCombo;
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
    public TableView<Question> questionTable;
    public TableColumn<Question, String> questionColumn;
    public TableColumn<Question, String> typeColumn;
    public TableColumn<Question, String> scoreColumn;
    public Button refreshButton;
    public Button updateButton;
    private Database<Question> QuestionDatabase;
    private Database<Question> QuestionInExamDatabase;
    private Database<Exam> ExamDatabase;

    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class); // Initialize the Question database
        ExamDatabase = new Database<>(Exam.class); // Initialize the Question database
        setupExamTableColumns();
        setupQuestionTableColumns();

        refreshExam(null); // Refresh exam table// Refresh questionInExam table
        refreshQuestionTable(null); // Refresh question table
    }

    private void refreshQuestionTable() {
    }

    private void setupQuestionTableColumns() {
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionContent")); // Adjust as necessary
        typeColumn1.setCellValueFactory(new PropertyValueFactory<>("Type"));
        scoreColumn1.setCellValueFactory(new PropertyValueFactory<>("Score"));
    }

    private void setupQuestionInExamTableColumns() {
        questionInExamColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionContent")); // Adjust as necessary
        typeColumn1.setCellValueFactory(new PropertyValueFactory<>("Type"));
        scoreColumn1.setCellValueFactory(new PropertyValueFactory<>("Score"));
    }

    private void setupExamTableColumns() {
        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("ExamName"));
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("CourID"));
        examTimeColumn.setCellValueFactory(new PropertyValueFactory<>("ExamTime"));
        PublishColumn.setCellValueFactory(new PropertyValueFactory<>("Publish"));
    }
    //All the QuestionManageUI functions being handled
    public void resetExam(ActionEvent actionEvent) {
        examNameTextField.clear();
        // Reset the type combo box to its default state (no selection)
        CourseIDComboBox.setValue(null);
        // Clear the text field for the score
        PublishComboBox.setValue(null);
        // Refresh the question table to show all questions without filters
        refreshExam(actionEvent);
    }
    public void deleteExam(ActionEvent actionEvent) {
    }

    public void addExam(ActionEvent actionEvent) {
    }

    public void updateExam(ActionEvent actionEvent) {
    }

    public void filterExam(ActionEvent actionEvent) {
        String ExamName = examNameTextField.getText().toLowerCase().trim();
        Long CourseID = CourseIDComboBox.getValue();
        String Publish = PublishComboBox.getValue();
        // Get all questions from the database
        List<Exam> allExams = ExamDatabase.getAll();

        // Create a list to hold the filtered questions
        List<Exam> filteredExams = new ArrayList<>();

        // Filter questions based on the inputs
        for (Exam exam : allExams) {
            boolean matches = true;

            // Check if question content matches
            if (!ExamName.isEmpty() && !exam.getExamName().toLowerCase().contains(ExamName)) {
                matches = false;
            }

            // Check if the CourseID matches
            if (CourseID != null && !exam.getCourseID().equals(CourseID)) {
                matches = false;
            }

            // Check if the score matches
            boolean publishBoolean = "Yes".equals(Publish); // Convert Publish String to boolean
            if (!Publish.isEmpty() && exam.getPublish() != publishBoolean) {
                matches = false;
            }

            // If all checks pass, add the question to the filtered list
            if (matches) {
                filteredExams.add(exam);
            }
        }
        // Update the table with the filtered questions
        ExamTable.setItems(FXCollections.observableArrayList(filteredExams));
    }

    public void resetQuestion(ActionEvent actionEvent) {
        questionTextField.clear();
        // Reset the type combo box to its default state (no selection)
        TypeComboBox.setValue(null);
        // Clear the text field for the score
        scoreTextField1.clear();
        // Refresh the question table to show all questions without filters
        refreshQuestionTable(actionEvent);
    }

    public void filterQuestion(ActionEvent actionEvent) {
        String questionContent = questionTextField.getText().toLowerCase().trim();
        String selectedType = TypeComboBox.getValue();
        String scoreText = scoreTextField1.getText().trim();

        // Get all questions from the database
        List<Question> allQuestions = QuestionDatabase.getAll();

        // Create a list to hold the filtered questions
        List<Question> filteredQuestions = new ArrayList<>();

        // Filter questions based on the inputs
        for (Question question : allQuestions) {
            boolean matches = true;

            // Check if question content matches
            if (!questionContent.isEmpty() && !question.getQuestionContent().toLowerCase().contains(questionContent)) {
                matches = false;
            }

            // Check if the type matches
            if (selectedType != null && !selectedType.isEmpty() && !question.getType().equals(selectedType)) {
                matches = false;
            }

            // Check if the score matches
            if (!scoreText.isEmpty()) {
                try {
                    int score = Integer.parseInt(scoreText);
                    if (question.getScore() == null || Integer.parseInt(question.getScore()) != score) {
                        matches = false;
                    }
                } catch (NumberFormatException e) {
                    matches = false; // Handle invalid score input
                }
            }
            // If all checks pass, add the question to the filtered list
            if (matches) {
                filteredQuestions.add(question);
            }
        }
        // Update the table with the filtered questions
        questionTable.setItems(FXCollections.observableArrayList(filteredQuestions));
    }

    public void Deletefromleft(ActionEvent actionEvent) {

    }

    public void Addtoleft(ActionEvent actionEvent) {
    }

    public void refreshExam(ActionEvent actionEvent) {
        List<Exam> exams = ExamDatabase.getAll(); // Fetch all exams from the database
        ExamTable.setItems(FXCollections.observableArrayList(exams)); // Update the table
    }

    public void refreshQuestionTable(ActionEvent actionEvent) {
        List<Question> questions = QuestionDatabase.getAll(); // Fetch all exams from the database
        questionTable.setItems(FXCollections.observableArrayList(questions)); // Update the table
    }

}
