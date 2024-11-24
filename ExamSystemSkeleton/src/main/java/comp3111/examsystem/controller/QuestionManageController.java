package comp3111.examsystem.controller;

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

import static comp3111.examsystem.tools.MsgSender.showMsg;

/**
 * Controller class for managing questions in the examination system.
 * This class handles the user interface for adding, updating, deleting,
 * and filtering questions, as well as displaying them in a table.
 */
public class QuestionManageController implements Initializable {

    @FXML
    public VBox Mainbox; // Main container for the UI
    public TextField teacherquestionTextField; // Input for filtering questions by content
    public ComboBox<String> teachertypeComboBox; // Input for filtering questions by type
    public TextField teacherscoreTextField; // Input for filtering questions by score
    public Button resetButton; // Button to reset filters
    public Button filterButton; // Button to apply filters
    @FXML
    public TableView<Question> questionTable; // Table to display questions
    public TableColumn<Question, String> questionColumn; // Column for question content
    public TableColumn<Question, String> optionAColumn; // Column for option A
    public TableColumn<Question, String> optionBColumn; // Column for option B
    public TableColumn<Question, String> optionCColumn; // Column for option C
    public TableColumn<Question, String> optionDColumn; // Column for option D
    public TableColumn<Question, String> answerColumn; // Column for the correct answer
    public TableColumn<Question, String> typeColumn; // Column for question type
    public TableColumn<Question, String> scoreColumn; // Column for question score
    @FXML
    public TextField newQuestionTextField; // Input for new question content
    public TextField optionATextField; // Input for option A
    public TextField optionBTextField; // Input for option B
    public TextField optionCTextField; // Input for option C
    public TextField optionDTextField; // Input for option D
    public ComboBox<String> newTypeComboBox; // Selection for new question type
    public TextField AnswerTextField; // Input for the correct answer
    public TextField newScoreTextField; // Input for new question score
    @FXML
    public Button addButton; // Button to add a new question
    public Button refreshButton; // Button to refresh the question table
    public Button deleteButton; // Button to delete a question
    public Button updateButton; // Button to update an existing question

    private Database<Question> QuestionDatabase; // Database instance for questions

    /**
     * Initializes the controller after its root element has been processed.
     * Sets up the table columns and loads initial data.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class);
        setupTableColumns();
        refreshQUI(null);
        questionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!CheckNull(newValue)) {
                populateFields(newValue);
            }
        });
    }

    /**
     * Checks if the given question is null.
     *
     * @param question The question to check.
     * @return true if the question is null; false otherwise.
     */
    static boolean CheckNull(Question question) {
        return question == null;
    }

    /**
     * Sets up the table columns for displaying question attributes.
     */
    private void setupTableColumns() {
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("questionContent"));
        optionAColumn.setCellValueFactory(new PropertyValueFactory<>("optionA"));
        optionBColumn.setCellValueFactory(new PropertyValueFactory<>("optionB"));
        optionCColumn.setCellValueFactory(new PropertyValueFactory<>("optionC"));
        optionDColumn.setCellValueFactory(new PropertyValueFactory<>("optionD"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    /**
     * Resets the question filtering inputs and refreshes the question table.
     *
     * @param actionEvent The ActionEvent triggered by the reset button.
     */
    @FXML
    public void resetQUI(ActionEvent actionEvent) {
        teacherquestionTextField.clear();
        teachertypeComboBox.setValue(null);
        teacherscoreTextField.clear();
        refreshQUI(actionEvent);
    }

    /**
     * Checks whether a question meets the filtering criteria based on question content, type, and score.
     *
     * @param question The question to check.
     * @param questionContent The question content to filter by.
     * @param selectedType The type of question to filter by.
     * @param scoreText The score to filter by.
     * @return true if the question matches the criteria; false otherwise.
     */
    static boolean QuestionChecking(Question question, String questionContent, String selectedType, String scoreText) {
        if (!questionContent.isEmpty() && !question.getQuestionContent().toLowerCase().contains(questionContent)) {
            return false;
        }
        if (!(selectedType == null || selectedType.isEmpty() || question.getType().equals(selectedType))) {
            return false;
        }
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
     * Filters the questions based on the input fields and updates the question table.
     *
     * @param actionEvent The ActionEvent triggered by the filter button.
     */
    public void queryQUI(ActionEvent actionEvent) {
        String questionContent = teacherquestionTextField.getText().toLowerCase().trim();
        String selectedType = teachertypeComboBox.getValue();
        String scoreText = teacherscoreTextField.getText().trim();
        List<Question> allQuestions = QuestionDatabase.getAll();
        List<Question> filteredQuestions = new ArrayList<>();

        for (Question question : allQuestions) {
            if (QuestionChecking(question, questionContent, selectedType, scoreText)) {
                filteredQuestions.add(question);
            }
        }
        questionTable.setItems(FXCollections.observableArrayList(filteredQuestions));
    }

    /**
     * Refreshes the question table to display all questions from the database.
     *
     * @param actionEvent The ActionEvent triggered by the refresh button.
     */
    public void refreshQUI(ActionEvent actionEvent) {
        List<Question> questions = QuestionDatabase.getAll();
        questionTable.setItems(FXCollections.observableArrayList(questions));
    }

    /**
     * Checks if any required input fields are empty.
     *
     * @param questionContent The question content input.
     * @param optionA The option A input.
     * @param optionB The option B input.
     * @param optionC The option C input.
     * @param optionD The option D input.
     * @param answer The correct answer input.
     * @param type The question type input.
     * @param score The score input.
     * @return true if any field is empty; false otherwise.
     */
    static boolean CheckEmptyInput(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score) {
        return questionContent.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || answer.isEmpty() || type.isEmpty() || score.isEmpty();
    }

    /**
     * Checks if the provided score is negative or invalid.
     *
     * @param score The score input as a string.
     * @return true if the score is negative or invalid; false otherwise.
     */
    static boolean CheckNegative(String score) {
        try {
            int parsedScore = Integer.parseInt(score);
            return parsedScore < 0;
        } catch (NumberFormatException e) {
            return true; // Return true for invalid inputs
        }
    }

    /**
     * Validates the inputs for creating or updating a question.
     *
     * @param questionContent The question content input.
     * @param optionA The option A input.
     * @param optionB The option B input.
     * @param optionC The option C input.
     * @param optionD The option D input.
     * @param answer The correct answer input.
     * @param type The question type input.
     * @param score The score input.
     * @return true if all validations pass; false otherwise.
     */
    static boolean Validation(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score) {
        if (CheckEmptyInput(questionContent, optionA, optionB, optionC, optionD, answer, type, score)) {
            showMsg("Error", "Error: All fields must be filled.");
            return false;
        }
        if (CheckNegative(score)) {
            showMsg("Error", "Error: Score is negative.");
            return false;
        }
        if ("Single".equals(type) && !isValidSingleAnswer(answer)) {
            showMsg("Error", "Error: For single type, answer must be one of: A, B, C, D.");
            return false;
        } else if ("Multiple".equals(type) && !isValidMultipleAnswer(answer)) {
            showMsg("Error", "Error: For multiple type, answer must be a combination of letters A, B, C, D.");
            return false;
        } else if (!("Single".equals(type) || "Multiple".equals(type))) {
            showMsg("Error", "Error: Invalid question type selected.");
            return false;
        }
        return true;
    }

    /**
     * Adds a new question to the database based on the input fields.
     *
     * @param actionEvent The ActionEvent triggered by the add button.
     */
    public void addQuestion(ActionEvent actionEvent) {
        String questionContent = newQuestionTextField.getText().trim();
        String optionA = optionATextField.getText().trim();
        String optionB = optionBTextField.getText().trim();
        String optionC = optionCTextField.getText().trim();
        String optionD = optionDTextField.getText().trim();
        String answer = AnswerTextField.getText().trim();
        String type = newTypeComboBox.getValue();
        String score = newScoreTextField.getText().trim();

        if (!Validation(questionContent, optionA, optionB, optionC, optionD, answer, type, score)) {
            return;
        }

        Question newQuestion = new Question(questionContent, optionA, optionB, optionC, optionD, answer, type, score);
        QuestionDatabase.add(newQuestion);
        refreshQUI(null);
    }

    /**
     * Validates a single choice answer.
     *
     * @param answer The answer input.
     * @return true if the answer is valid; false otherwise.
     */
    static boolean isValidSingleAnswer(String answer) {
        return "A".equals(answer) || "B".equals(answer) || "C".equals(answer) || "D".equals(answer);
    }

    /**
     * Validates a multiple choice answer.
     *
     * @param answer The answer input.
     * @return true if the answer is valid; false otherwise.
     */
    static boolean isValidMultipleAnswer(String answer) {
        if (answer.length() > 4) {
            return false;
        }
        String validOptions = "ABCD";
        for (char c : answer.toCharArray()) {
            if (validOptions.indexOf(c) == -1) {
                return false; // Invalid character found
            }
        }
        for (int i = 0; i < answer.length(); i++) {
            for (int j = i + 1; j < answer.length(); j++) {
                if (answer.charAt(i) == answer.charAt(j)) {
                    return false; // Duplicate character found
                }
            }
        }
        for (int i = 0; i < answer.length() - 1; i++) {
            if (answer.charAt(i) > answer.charAt(i + 1)) {
                return false; // Not in order
            }
        }
        return true; // All checks passed
    }

    /**
     * Deletes the selected question from the database after confirmation.
     *
     * @param actionEvent The ActionEvent triggered by the delete button.
     */
    public void deleteQuestion(ActionEvent actionEvent) {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();

        if (CheckNull(selectedQuestion)) {
            showMsg("No Selection", "Please select a question to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this question?");
        confirmationAlert.setContentText("Question: " + selectedQuestion.getQuestionContent());

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                QuestionDatabase.delByKey(String.valueOf(selectedQuestion.getId()));
                refreshQUI(actionEvent);
            }
        });
    }

    /**
     * Populates the input fields with the data from the selected question.
     *
     * @param question The selected question to populate the fields with.
     */
    private void populateFields(Question question) {
        newQuestionTextField.setText(question.getQuestionContent());
        optionATextField.setText(question.getOptionA());
        optionBTextField.setText(question.getOptionB());
        optionCTextField.setText(question.getOptionC());
        optionDTextField.setText(question.getOptionD());
        AnswerTextField.setText(question.getAnswer());
        newTypeComboBox.setValue(question.getType());
        newScoreTextField.setText(String.valueOf(question.getScore()));
    }

    /**
     * Updates the selected question with the data from the input fields.
     *
     * @param actionEvent The ActionEvent triggered by the update button.
     */
    public void updateQuestion(ActionEvent actionEvent) {
        Question selectedQuestion_1 = questionTable.getSelectionModel().getSelectedItem();

        String questionContent = newQuestionTextField.getText().trim();
        String optionA = optionATextField.getText().trim();
        String optionB = optionBTextField.getText().trim();
        String optionC = optionCTextField.getText().trim();
        String optionD = optionDTextField.getText().trim();
        String answer = AnswerTextField.getText().trim();
        String type = newTypeComboBox.getValue();
        String score = newScoreTextField.getText().trim();

        if (!Validation(questionContent, optionA, optionB, optionC, optionD, answer, type, score)) {
            return;
        }

        if (!CheckNull(selectedQuestion_1)) {
            Question selectedQuestion = QuestionDatabase.queryByKey(String.valueOf(selectedQuestion_1.getId()));
            selectedQuestion.setQuestionContent(questionContent);
            selectedQuestion.setOptionA(optionA);
            selectedQuestion.setOptionB(optionB);
            selectedQuestion.setOptionC(optionC);
            selectedQuestion.setOptionD(optionD);
            selectedQuestion.setAnswer(answer);
            selectedQuestion.setType(type);
            selectedQuestion.setScore(score);
            QuestionDatabase.update(selectedQuestion);
            questionTable.refresh();
            showMsg("Notice", "Question updated successfully!");
        } else {
            showMsg("Notice", "No question selected for update.");
        }
    }
}