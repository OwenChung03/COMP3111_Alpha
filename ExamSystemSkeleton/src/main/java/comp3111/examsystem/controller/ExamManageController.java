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
    public TableColumn<Exam, String> publishColumn;
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
    public ComboBox<String> newCourseIDComboBox;
    private Database<Question> QuestionDatabase;
    private Database<Exam> ExamDatabase;
    private Database<Course> CourseDatabase;

    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class); // Initialize the Question database
        ExamDatabase = new Database<>(Exam.class); // Initialize the Exam database
        CourseDatabase = new Database<>(Course.class); // Initialize the Course database
        setupExamTableColumns();
        setupQuestionTableColumns(questionInExamColumn,typeColumn1,scoreColumn1);
        setupQuestionTableColumns(questionColumn,typeColumn,scoreColumn);
        refreshExam(null); // Refresh exam table// Refresh questionInExam table
        refreshQuestionTable(null); // Refresh question table
        List<String> courseIDs = fetchCourseIDs(); // Implement this method to get Course IDs
        CourseIDComboBox.setItems(FXCollections.observableArrayList(courseIDs));
        newCourseIDComboBox.setItems(FXCollections.observableArrayList(courseIDs));
        // Add listener to load questions when an exam is selected
        ExamTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!CheckNull(newValue)) {
                loadQuestionsForExam(newValue);
                populateExamDetails(newValue); // New method to populate details
            }
        });
    }

    private void loadQuestionsForExam(Exam selectedExam) {
        // Clear the current questions in the QuestionInExam table
        questionInExamTable.getItems().clear();

        // Get the question keys from the selected exam
        String questionKeys = selectedExam.getQuestionKeys(); // Ensure this method exists

        // Split the keys into an array
        String[] keys = questionKeys.split("/");

        // Fetch questions based on IDs
        List<Question> questions = new ArrayList<>();
        for (String key : keys) {
            Question question = QuestionDatabase.queryByKey(key); // Fetch question by ID
            if (!CheckNull(question)) {
                questions.add(question);
            }
        }

        // Populate the QuestionInExam table
        questionInExamTable.setItems(FXCollections.observableArrayList(questions));
    }

    private void populateExamDetails(Exam selectedExam) {
        // Populate text fields and combo boxes with selected exam details
        newexamNameTextField.setText(selectedExam.getExamName());
        newCourseIDComboBox.setValue(selectedExam.getCourseKey());
        newexamTimeTextField.setText(selectedExam.getExamTime());
        PublishCombo.setValue(selectedExam.getPublish());
    }

    private List<String> fetchCourseIDs() {
        // Replace this with actual data fetching logic
        List<Course> allCourses = CourseDatabase.getAll();

        // Check if the course list is empty
        // Extract CourseIDs from the Course objects
        List<String> courseIDs = allCourses.stream()
                .map(Course::getCourseId) // Assuming getCourseID() returns the Course ID as a String
                .collect(Collectors.toList());

        return courseIDs;// Sample Course IDs
    }

    private void setupQuestionTableColumns(TableColumn<Question, String> QuestionColumn,TableColumn<Question, String> TypeColumn,TableColumn<Question, String> ScoreColumn) {
        QuestionColumn.setCellValueFactory(new PropertyValueFactory<>("questionContent")); // Adjust as necessary
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }


    private void setupExamTableColumns() {
        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseKey"));
        examTimeColumn.setCellValueFactory(new PropertyValueFactory<>("examTime"));
        publishColumn.setCellValueFactory(new PropertyValueFactory<>("publish"));
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
    static <T> boolean CheckNull(T exam){
        if(exam == null){
            return true;
        }
        return false;
    }

    public void deleteExam(ActionEvent actionEvent) {
        Exam selectedExam = ExamTable.getSelectionModel().getSelectedItem();

        // Check if a question is selected
        if (CheckNull(selectedExam)) {
            // Show an alert if no question is selected
            showMsg("No Selection", "Please select an exam to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this exam?");
        confirmationAlert.setContentText("Exam: " + selectedExam.getExamName());

        // Show the confirmation dialog and wait for the user's response
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the question from the database
                ExamDatabase.delByKey(String.valueOf(selectedExam.getId())); // Assuming you have a method to delete by ID

                // Refresh the table to show the updated list of questions
                refreshExam(actionEvent);
            }
        });
    }

    static boolean CheckEmptyExam(String examName, String courseID, String examTime, String publish){
        return examName.isEmpty() || courseID == null || examTime.isEmpty() || publish == null;
    }
    static boolean CheckTime(String examTimeText){
        int examTime;
        try {
            examTime = Integer.parseInt(examTimeText);
            if (examTime <= 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
    static boolean CheckExamMatch(Exam exam, String ExamName, String CourseID, String Publish){


        // Check if question content matches
        if (!(ExamName.isEmpty() || exam.getExamName().toLowerCase().contains(ExamName))||!(CourseID == null || CourseID.equals(String.valueOf(exam.getCourseKey())))||!(Publish == null || exam.getPublish().contains(Publish))){
            return false;
        }
        return true;
    }
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
    static boolean Validation(String examName,String courseID,String examTimeText,String publishStatusText){
        if (CheckEmptyExam(examName,courseID,examTimeText,publishStatusText)){
            showMsg("Error", "Please fill in all required fields.");
            return true;
        }

        if (CheckTime(examTimeText)){
            showMsg("Error", "Exam time must be a valid number.");
            return true;
        }
        return false;
    }
    public void addExam(ActionEvent actionEvent) {
        String examName = newexamNameTextField.getText().trim();
        String courseID = newCourseIDComboBox.getValue();
        String examTimeText = newexamTimeTextField.getText().trim();
        String publishStatusText = PublishCombo.getValue();

        // Validate input
        if (Validation(examName,courseID,examTimeText,publishStatusText)){
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

        // Create a new Exam object
        Exam newExam = new Exam(examName, courseID, examTimeText, publishStatusText, questionKeys);

        // Add the new exam to the database
        ExamDatabase.add(newExam); // Assuming add method exists in your Database class

        // Refresh the exam table to show the new exam
        refreshExam(actionEvent);
    }

    // Method to get question keys as a joined string

    public void updateExam(ActionEvent actionEvent) {
        Exam selectedExam = ExamTable.getSelectionModel().getSelectedItem();
        if (CheckNull(selectedExam)) {
            showMsg("No Selection", "Please select an exam to update.");
            return;
        }

        // Retrieve updated values from the UI components
        String newExamName = newexamNameTextField.getText().trim();
        String newCourseID = newCourseIDComboBox.getValue();
        String newExamTimeText = newexamTimeTextField.getText().trim();
        String newPublishStatus = PublishCombo.getValue();
// Validate input
        if (Validation(newExamName,newCourseID,newExamTimeText,newPublishStatus)){
            return;
        }

        // Retrieve existing question keys from the selected exam
        // Retrieve existing question keys from the selected exam
        String questionKeys = questionInExamTable.getItems().stream()
                .map(question -> {
                    long id = question.getId();
                    return (id == 0) ? question.getreferID() : String.valueOf(id);
                })
                .collect(Collectors.joining("/"));
        //String combinedKeys = String.join("/", oldquestionKeys, questionKeys);
        // Update the selected exam
        selectedExam.setExamName(newExamName);
        selectedExam.setCourseKey(newCourseID); // Assuming setCourseKey() exists
        selectedExam.setExamTime(newExamTimeText); // Assuming setExamTime() accepts a String
        selectedExam.setPublish(newPublishStatus); // Assuming setPublish() exists
        selectedExam.setQuestionKeys(questionKeys); // Assuming setQuestionKeys() exists

        // Update the exam in the database
        ExamDatabase.update(selectedExam); // Assuming update method exists in your Database class

        // Refresh the exam table to show the updated exam
        refreshExam(actionEvent);
        showMsg("Success", "Exam updated successfully.");
    }

    public void filterExam(ActionEvent actionEvent) {
        String ExamName = examNameTextField.getText().toLowerCase().trim();

        String CourseID = CourseIDComboBox.getValue();
        String Publish = PublishComboBox.getValue();
        // Get all questions from the database
        List<Exam> allExams = ExamDatabase.getAll();

        // Create a list to hold the filtered questions
        List<Exam> filteredExams = new ArrayList<>();

        // Filter questions based on the inputs
        for (Exam exam : allExams) {

            // If all checks pass, add the question to the filtered list
            if (CheckExamMatch(exam, ExamName, CourseID, Publish)) {
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

            // If all checks pass, add the question to the filtered list
            if (CheckQuestionMatch(question, questionContent, selectedType, scoreText)) {
                filteredQuestions.add(question);
            }
        }
        // Update the table with the filtered questions
        questionTable.setItems(FXCollections.observableArrayList(filteredQuestions));
    }

    public void Deletefromleft(ActionEvent actionEvent) {
        Question selectedQuestion = questionInExamTable.getSelectionModel().getSelectedItem();

        if (!CheckNull(selectedQuestion)) {
            // Remove the selected question from the questionInExamTable
            questionInExamTable.getItems().remove(selectedQuestion);

        } else {
            showMsg("Error", "Please select a question to delete.");
        }
    }
    public boolean HasDuplicate(Question newQuestion) {
        // Iterate through the items in the questionInExamTable
        for (Question existingQuestion : questionInExamTable.getItems()) {
            // Compare their IDs
            if (Objects.equals(existingQuestion.getreferID(), String.valueOf(newQuestion.getId()))) {
                return true; // Duplicate found
            }
        }
        return false;
    }
        public void Addtoleft(ActionEvent actionEvent) {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();

        if (!CheckNull(selectedQuestion)&&!(HasDuplicate(selectedQuestion)) ){
                // Create a full copy of the selected question
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
                //
                // Add the copied question to the questionInExamTable
                questionInExamTable.getItems().add(copiedQuestion);
                //printReferIDs();
        }else {
                showMsg("Error", "Please select a non duplicate question to add.");
            }
    }

        public void refreshExam (ActionEvent actionEvent){
            List<Exam> exams = ExamDatabase.getAll(); // Fetch all exams from the database
            ExamTable.setItems(FXCollections.observableArrayList(exams)); // Update the table
        }

        public void refreshQuestionTable (ActionEvent actionEvent){
            List<Question> questions = QuestionDatabase.getAll(); // Fetch all exams from the database
            questionTable.setItems(FXCollections.observableArrayList(questions)); // Update the table
        }
}
