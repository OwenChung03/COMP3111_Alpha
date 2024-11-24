package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
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
import java.util.*;
import java.util.stream.Collectors;

import static comp3111.examsystem.tools.MsgSender.showMsg;

/**
 * Controller class for managing exams in the examination system.
 * This class handles the user interface for creating, updating,
 * deleting, and filtering exams, as well as displaying associated questions.
 */
public class ExamManageController implements Initializable {

    @FXML
    public VBox MainBox2; // Main container for the UI
    @FXML
    public TextField examNameTextField; // Input for filtering exams by name
    @FXML
    public ComboBox<String> CourseIDComboBox; // Dropdown for selecting course IDs
    @FXML
    public ComboBox<String> PublishComboBox; // Dropdown for selecting publish status
    @FXML
    public Button resetButton1; // Button to reset exam filters
    @FXML
    public Button filterButton1; // Button to apply filters to exams
    @FXML
    public TextField scoreTextField1; // Input for filtering questions by score
    @FXML
    public TableView<Exam> ExamTable; // Table to display exams
    @FXML
    public TableColumn<Exam, String> examNameColumn; // Column for exam names
    @FXML
    public TableColumn<Exam, String> courseIDColumn; // Column for course IDs
    @FXML
    public TableColumn<Exam, String> examTimeColumn; // Column for exam times
    @FXML
    public TableColumn<Exam, String> publishColumn; // Column for publish status
    @FXML
    public TableView<Question> questionInExamTable; // Table to display questions in the selected exam
    @FXML
    public TableColumn<Question, String> questionInExamColumn; // Column for question content in the exam
    @FXML
    public TableColumn<Question, String> typeColumn1; // Column for question types in the exam
    @FXML
    public TableColumn<Question, String> scoreColumn1; // Column for question scores in the exam
    @FXML
    public Button deletefromleftButton; // Button to remove questions from the exam
    @FXML
    public TextField newexamNameTextField; // Input for creating/updating exam names
    @FXML
    public Button addtoleftButton; // Button to add questions to the exam
    @FXML
    public TextField newexamTimeTextField; // Input for setting exam time
    @FXML
    public ComboBox<String> PublishCombo; // Dropdown for publishing exam
    @FXML
    public Button deleteButton1; // Button to delete selected exam
    @FXML
    public Button addButton1; // Button to add a new exam
    @FXML
    public Button resetButton; // Button to reset question filters
    public Button filterButton; // Button to apply filters to questions
    public TextField questionTextField; // Input for filtering questions
    public ComboBox<String> TypeComboBox; // Dropdown for selecting question type
    public TableView<Question> questionTable; // Table to display all questions
    public TableColumn<Question, String> questionColumn; // Column for question content
    public TableColumn<Question, String> typeColumn; // Column for question types
    public TableColumn<Question, String> scoreColumn; // Column for question scores
    public Button refreshButton; // Button to refresh the question table
    public Button updateButton; // Button to update an existing exam
    public ComboBox<String> newCourseIDComboBox; // Dropdown for selecting course ID for a new exam

    private Database<Question> QuestionDatabase; // Database instance for questions
    private Database<Exam> ExamDatabase; // Database instance for exams
    private Database<Course> CourseDatabase; // Database instance for courses

    /**
     * Initializes the controller after its root element has been processed.
     * Sets up the table columns, loads initial data, and configures event listeners.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if not known.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class);
        ExamDatabase = new Database<>(Exam.class);
        CourseDatabase = new Database<>(Course.class);
        setupExamTableColumns();
        setupQuestionTableColumns(questionInExamColumn, typeColumn1, scoreColumn1);
        setupQuestionTableColumns(questionColumn, typeColumn, scoreColumn);
        refreshExam(null);
        refreshQuestionTable(null);
        List<String> courseIDs = fetchCourseIDs();
        CourseIDComboBox.setItems(FXCollections.observableArrayList(courseIDs));
        newCourseIDComboBox.setItems(FXCollections.observableArrayList(courseIDs));

        // Load questions when an exam is selected
        ExamTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!CheckNull(newValue)) {
                loadQuestionsForExam(newValue);
                populateExamDetails(newValue);
            }
        });
    }

    /**
     * Loads questions associated with the selected exam into the question table.
     *
     * @param selectedExam The exam whose questions should be loaded.
     */
    private void loadQuestionsForExam(Exam selectedExam) {
        questionInExamTable.getItems().clear();
        String questionKeys = selectedExam.getQuestionKeys();
        String[] keys = questionKeys.split("/");

        List<Question> questions = new ArrayList<>();
        for (String key : keys) {
            Question question = QuestionDatabase.queryByKey(key);
            if (!CheckNull(question)) {
                questions.add(question);
            }
        }
        questionInExamTable.setItems(FXCollections.observableArrayList(questions));
    }

    /**
     * Populates input fields with details from the selected exam.
     *
     * @param selectedExam The exam to populate fields with.
     */
    private void populateExamDetails(Exam selectedExam) {
        newexamNameTextField.setText(selectedExam.getExamName());
        newCourseIDComboBox.setValue(selectedExam.getCourseKey());
        newexamTimeTextField.setText(selectedExam.getExamTime());
        PublishCombo.setValue(selectedExam.getPublish());
    }

    /**
     * Fetches course IDs from the course database.
     *
     * @return A list of course IDs.
     */
    private List<String> fetchCourseIDs() {
        List<Course> allCourses = CourseDatabase.getAll();
        return allCourses.stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList());
    }

    /**
     * Sets up the columns for the question tables.
     *
     * @param QuestionColumn The column for question content.
     * @param TypeColumn The column for question types.
     * @param ScoreColumn The column for question scores.
     */
    private void setupQuestionTableColumns(TableColumn<Question, String> QuestionColumn,
                                           TableColumn<Question, String> TypeColumn,
                                           TableColumn<Question, String> ScoreColumn) {
        QuestionColumn.setCellValueFactory(new PropertyValueFactory<>("questionContent"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    /**
     * Sets up the columns for the exam table.
     */
    private void setupExamTableColumns() {
        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseKey"));
        examTimeColumn.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        publishColumn.setCellValueFactory(new PropertyValueFactory<>("publish"));
    }

    /**
     * Resets the exam filtering inputs and refreshes the exam table.
     *
     * @param actionEvent The ActionEvent triggered by the reset button.
     */
    public void resetExam(ActionEvent actionEvent) {
        examNameTextField.clear();
        CourseIDComboBox.setValue(null);
        PublishComboBox.setValue(null);
        refreshExam(actionEvent);
    }

    /**
     * Checks if the given exam object is null.
     *
     * @param exam The exam to check.
     * @return true if the exam is null; false otherwise.
     */
    static <T> boolean CheckNull(T exam) {
        return exam == null;
    }

    /**
     * Deletes the selected exam after user confirmation.
     *
     * @param actionEvent The ActionEvent triggered by the delete button.
     */
    public void deleteExam(ActionEvent actionEvent) {
        Exam selectedExam = ExamTable.getSelectionModel().getSelectedItem();
        if (CheckNull(selectedExam)) {
            showMsg("No Selection", "Please select an exam to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this exam?");
        confirmationAlert.setContentText("Exam: " + selectedExam.getExamName());

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ExamDatabase.delByKey(String.valueOf(selectedExam.getId()));
                refreshExam(actionEvent);
            }
        });
    }

    /**
     * Checks if any required exam input fields are empty.
     *
     * @param examName The exam name input.
     * @param courseID The course ID input.
     * @param examTime The exam time input.
     * @param publish The publish status input.
     * @return true if any field is empty; false otherwise.
     */
    static boolean CheckEmptyExam(String examName, String courseID, String examTime, String publish) {
        return examName.isEmpty() || courseID == null || examTime.isEmpty() || publish == null;
    }

    /**
     * Validates if the provided exam time is a positive integer.
     *
     * @param examTimeText The exam time input as a string.
     * @return true if the exam time is negative or invalid; false otherwise.
     */
    static boolean CheckTime(String examTimeText) {
        int examTime;
        try {
            examTime = Integer.parseInt(examTimeText);
            return examTime <= 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Validates the exam inputs for adding or updating an exam.
     *
     * @param examName The exam name input.
     * @param courseID The course ID input.
     * @param examTimeText The exam time input.
     * @param publishStatusText The publish status input.
     * @return true if validation fails; false otherwise.
     */
    static boolean Validation(String examName, String courseID, String examTimeText, String publishStatusText) {
        if (CheckEmptyExam(examName, courseID, examTimeText, publishStatusText)) {
            showMsg("Error", "Please fill in all required fields.");
            return true;
        }
        if (CheckTime(examTimeText)) {
            showMsg("Error", "Exam time must be a valid number.");
            return true;
        }
        return false;
    }

    /**
     * Adds a new exam based on the input fields.
     *
     * @param actionEvent The ActionEvent triggered by the add button.
     */
    public void addExam(ActionEvent actionEvent) {
        String examName = newexamNameTextField.getText().trim();
        String courseID = newCourseIDComboBox.getValue();
        String examTimeText = newexamTimeTextField.getText().trim();
        String publishStatusText = PublishCombo.getValue();

        if (Validation(examName, courseID, examTimeText, publishStatusText)) {
            return;
        }

        // Collect question IDs from the questionInExamTable
        StringBuilder questionKeysBuilder = new StringBuilder();
        for (Question question : questionInExamTable.getItems()) {
            if (questionKeysBuilder.length() > 0) {
                questionKeysBuilder.append("/"); // Append separator before adding the next ID
            }
            questionKeysBuilder.append(question.getId()); // Assuming you have a method getId() in your Question class
        }
        String questionKeys = questionKeysBuilder.toString(); // Convert to string

        Exam newExam = new Exam(examName, courseID, examTimeText, publishStatusText, questionKeys);
        ExamDatabase.add(newExam);
        refreshExam(actionEvent);
    }

    /**
     * Updates the selected exam with new details from the input fields.
     *
     * @param actionEvent The ActionEvent triggered by the update button.
     */
    public void updateExam(ActionEvent actionEvent) {
        Exam selectedExam = ExamTable.getSelectionModel().getSelectedItem();
        if (CheckNull(selectedExam)) {
            showMsg("No Selection", "Please select an exam to update.");
            return;
        }

        String newExamName = newexamNameTextField.getText().trim();
        String newCourseID = newCourseIDComboBox.getValue();
        String newExamTimeText = newexamTimeTextField.getText().trim();
        String newPublishStatus = PublishCombo.getValue();

        if (Validation(newExamName, newCourseID, newExamTimeText, newPublishStatus)) {
            return;
        }

        String questionKeys = questionInExamTable.getItems().stream()
                .map(question -> {
                    long id = question.getId();
                    return (id == 0) ? question.getreferID() : String.valueOf(id);
                })
                .collect(Collectors.joining("/"));

        selectedExam.setExamName(newExamName);
        selectedExam.setCourseKey(newCourseID);
        selectedExam.setExamTime(newExamTimeText);
        selectedExam.setPublish(newPublishStatus);
        selectedExam.setQuestionKeys(questionKeys);

        ExamDatabase.update(selectedExam);
        refreshExam(actionEvent);
        showMsg("Success", "Exam updated successfully.");
    }

    /**
     * Filters the exams based on the input fields and updates the exam table.
     *
     * @param actionEvent The ActionEvent triggered by the filter button.
     */
    public void filterExam(ActionEvent actionEvent) {
        String ExamName = examNameTextField.getText().toLowerCase().trim();
        String CourseID = CourseIDComboBox.getValue();
        String Publish = PublishComboBox.getValue();

        List<Exam> allExams = ExamDatabase.getAll();
        List<Exam> filteredExams = allExams.stream()
                .filter(exam -> CheckExamMatch(exam, ExamName, CourseID, Publish))
                .collect(Collectors.toList());

        ExamTable.setItems(FXCollections.observableArrayList(filteredExams));
    }

    /**
     * Resets the question filtering inputs and refreshes the question table.
     *
     * @param actionEvent The ActionEvent triggered by the reset button.
     */
    public void resetQuestion(ActionEvent actionEvent) {
        questionTextField.clear();
        TypeComboBox.setValue(null);
        scoreTextField1.clear();
        refreshQuestionTable(actionEvent);
    }

    /**
     * Filters the questions based on the input fields and updates the question table.
     *
     * @param actionEvent The ActionEvent triggered by the filter button.
     */
    public void filterQuestion(ActionEvent actionEvent) {
        String questionContent = questionTextField.getText().toLowerCase().trim();
        String selectedType = TypeComboBox.getValue();
        String scoreText = scoreTextField1.getText().trim();

        List<Question> allQuestions = QuestionDatabase.getAll();
        List<Question> filteredQuestions = allQuestions.stream()
                .filter(question -> CheckQuestionMatch(question, questionContent, selectedType, scoreText))
                .collect(Collectors.toList());

        questionTable.setItems(FXCollections.observableArrayList(filteredQuestions));
    }
    /**
     * Checks if the given exam matches the specified filter criteria.
     *
     * @param exam     The exam to be checked against the filter criteria.
     * @param ExamName The name filter to match against the exam's name.
     * @param CourseID The course ID filter to match against the exam's course key.
     * @param Publish  The publish status filter to match against the exam's publish status.
     * @return true if the exam matches all provided criteria; false otherwise.
     */
    static boolean CheckExamMatch(Exam exam, String ExamName, String CourseID, String Publish){


        // Check if question content matches
        if (!(ExamName.isEmpty() || exam.getExamName().toLowerCase().contains(ExamName))||!(CourseID == null || CourseID.equals(String.valueOf(exam.getCourseKey())))||!(Publish == null || exam.getPublish().contains(Publish))){
            return false;
        }
        return true;
    }
    /**
     * Checks if the given question matches the specified filter criteria.
     *
     * @param question      The question to be checked against the filter criteria.
     * @param questionContent The content filter to match against the question's content.
     * @param selectedType  The question type filter to match against the question's type.
     * @param scoreText     The score filter to match against the question's score.
     * @return true if the question matches all provided criteria; false otherwise.
     */
    static boolean CheckQuestionMatch(Question question, String questionContent, String selectedType, String scoreText){

        // Check if question content matches
        if (!questionContent.isEmpty() && !question.getQuestionContent().toLowerCase().contains(questionContent)) {
            return false;
        }

        // Check if the type matches
        if (selectedType != null && !selectedType.isEmpty() && !question.getType().equals(selectedType)) {
            return false;
        }

        // Check if the score matches
        if (!scoreText.isEmpty()) {
            try {
                int score = Integer.parseInt(scoreText);
                if (question.getScore() == null || Integer.parseInt(question.getScore()) != score) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false; // Handle invalid score input
            }
        }
        return true;
    }
    /**
     * Deletes a selected question from the exam's question list.
     *
     * @param actionEvent The ActionEvent triggered by the delete from left button.
     */
    public void Deletefromleft(ActionEvent actionEvent) {
        Question selectedQuestion = questionInExamTable.getSelectionModel().getSelectedItem();
        if (!CheckNull(selectedQuestion)) {
            questionInExamTable.getItems().remove(selectedQuestion);
        } else {
            showMsg("Error", "Please select a question to delete.");
        }
    }

    /**
     * Checks for duplicates before adding a question to the exam.
     *
     * @param newQuestion The question to check for duplicates.
     * @return true if a duplicate is found; false otherwise.
     */
    public boolean HasDuplicate(Question newQuestion) {
        return questionInExamTable.getItems().stream()
                .anyMatch(existingQuestion -> Objects.equals(existingQuestion.getreferID(), String.valueOf(newQuestion.getId())));
    }

    /**
     * Adds a selected question to the exam's question list.
     *
     * @param actionEvent The ActionEvent triggered by the add to left button.
     */
    public void Addtoleft(ActionEvent actionEvent) {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
        if (!CheckNull(selectedQuestion) && !HasDuplicate(selectedQuestion)) {
            Question copiedQuestion = new Question(
                    selectedQuestion.getQuestionContent(),
                    selectedQuestion.getOptionA(),
                    selectedQuestion.getOptionB(),
                    selectedQuestion.getOptionC(),
                    selectedQuestion.getOptionD(),
                    selectedQuestion.getAnswer(),
                    selectedQuestion.getType(),
                    selectedQuestion.getScore(),
                    String.valueOf(selectedQuestion.getId())
            );
            questionInExamTable.getItems().add(copiedQuestion);
        } else {
            showMsg("Error", "Please select a non-duplicate question to add.");
        }
    }

    /**
     * Refreshes the exam table to display all exams from the database.
     *
     * @param actionEvent The ActionEvent triggered by the refresh button.
     */
    public void refreshExam(ActionEvent actionEvent) {
        List<Exam> exams = ExamDatabase.getAll();
        ExamTable.setItems(FXCollections.observableArrayList(exams));
    }

    /**
     * Refreshes the question table to display all questions from the database.
     *
     * @param actionEvent The ActionEvent triggered by the refresh button.
     */
    public void refreshQuestionTable(ActionEvent actionEvent) {
        List<Question> questions = QuestionDatabase.getAll();
        questionTable.setItems(FXCollections.observableArrayList(questions));
    }
}