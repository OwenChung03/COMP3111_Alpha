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

public class QuestionManageController implements Initializable {

    @FXML
    public VBox Mainbox; //mainbox for QUI
    public TextField teacherquestionTextField; //usage in QUI filter
    public ComboBox<String> teachertypeComboBox; //usage in QUI filter
    public TextField teacherscoreTextField; //usage in QUI filter
    public Button resetButton; //usage in QUI filter
    public Button filterButton; //usage in QUI filter
    @FXML
    public TableView<Question> questionTable; //Question Table
    public TableColumn<Question, String> questionColumn;
    public TableColumn<Question, String> optionAColumn;
    public TableColumn<Question, String> optionBColumn;
    public TableColumn<Question, String> optionCColumn;
    public TableColumn<Question, String> optionDColumn;
    public TableColumn<Question, String> answerColumn;
    public TableColumn<Question, String> typeColumn;
    public TableColumn<Question, String> scoreColumn;
    @FXML
    public TextField newQuestionTextField;
    public TextField optionATextField;
    public TextField optionBTextField;
    public TextField optionCTextField;
    public TextField optionDTextField;
    public ComboBox<String> newTypeComboBox;
    public TextField AnswerTextField;
    public TextField newScoreTextField;
    @FXML
    public Button addButton;
    public Button refreshButton;
    public Button deleteButton;
    public Button updateButton;

    //Complete all the requirement of QuestionManage UI
    private Database<Question> QuestionDatabase;

    public void initialize(URL location, ResourceBundle resources) {
        QuestionDatabase = new Database<>(Question.class);
        setupTableColumns();
        refreshQUI(null);
        questionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });// Load initial data
    }

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


    @FXML

    public void resetQUI(ActionEvent actionEvent) {
        // Clear the text field for the question content
        teacherquestionTextField.clear();
        // Reset the type combo box to its default state (no selection)
        teachertypeComboBox.setValue(null);
        // Clear the text field for the score
        teacherscoreTextField.clear();
        // Refresh the question table to show all questions without filters
        refreshQUI(actionEvent);
    }

    static boolean QuestionChecking(Question question, String questionContent, String selectedType, String scoreText) {
        // Check if question content matches
        if (!questionContent.isEmpty()){
            return false;
        }
        if(!question.getQuestionContent().toLowerCase().contains(questionContent)){
            return false;
        }
        // Check if the type matches
        if (!(selectedType == null || selectedType.isEmpty() || question.getType().equals(selectedType))) {
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
    public void queryQUI(ActionEvent actionEvent) {
        // Get the filter values from the UI components
        String questionContent = teacherquestionTextField.getText().toLowerCase().trim();
        String selectedType = teachertypeComboBox.getValue();
        String scoreText = teacherscoreTextField.getText().trim();

        // Get all questions from the database
        List<Question> allQuestions = QuestionDatabase.getAll();

        // Create a list to hold the filtered questions
        List<Question> filteredQuestions = new ArrayList<>();

        // Filter questions based on the inputs
        for (Question question : allQuestions) {
            // If all checks pass, add the question to the filtered list
            if (QuestionChecking(question, questionContent, selectedType, scoreText)) {
                filteredQuestions.add(question);
            }
        }
        // Update the table with the filtered questions
        questionTable.setItems(FXCollections.observableArrayList(filteredQuestions));
    }
    public void refreshQUI(ActionEvent actionEvent) {
        List<Question> questions = QuestionDatabase.getAll();
        questionTable.setItems(FXCollections.observableArrayList(questions));
    }
    static boolean CheckEmptyInput(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score){
        if (questionContent.isEmpty() || optionA.isEmpty() || optionB.isEmpty() ||
                optionC.isEmpty() || optionD.isEmpty() || answer.isEmpty()||type.isEmpty()||score.isEmpty()) {
            return true;// Early exit on validation failure
        }
        return false;
    }
    static boolean CheckNegative(String score){
        try {
            // Attempt to parse the score
            int parsedScore = Integer.parseInt(score);
            // Check if the score is negative
            return parsedScore < 0; // Return true if negative
        } catch (NumberFormatException e) {
            // Return true for invalid inputs (e.g., empty, non-numeric)
            return true;
        }
    }
    static boolean Validation(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score){
        if(CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score)){
            showMsg("Error","Error: All fields must be filled.");
            return false;
        }
        if(CheckNegative(score)){
            showMsg("Error","Error: Score is negative.");
            return false;
        }
        if ("Single".equals(type)) {
            // Validate single choice answer
            if (!isValidSingleAnswer(answer)) {
                showMsg("Error","Error: For single type, answer must be one of: A, B, C, D.");
                return false; // Early exit on validation failure
            }
        } else if ("Multiple".equals(type)) {
            // Validate multiple choice answer
            if (!isValidMultipleAnswer(answer)) {
                showMsg("Error","Error: For multiple type, answer must be a combination of letters A, B, C, D.");
                return false; // Early exit on validation failure
            }
        } else {
            showMsg("Error","Error: Invalid question type selected.");
            return false; // Early exit on validation failure// Early exit on validation failure
        }
        return true;
    }
    public void addQuestion(ActionEvent actionEvent) {
        String questionContent = newQuestionTextField.getText().trim();
        String optionA = optionATextField.getText().trim();
        String optionB = optionBTextField.getText().trim();
        String optionC = optionCTextField.getText().trim();
        String optionD = optionDTextField.getText().trim();
        String answer = AnswerTextField.getText().trim();
        String type = newTypeComboBox.getValue();
        String score = newScoreTextField.getText().trim();

        // Validate inputs
        if(!Validation(questionContent,optionA,optionB,optionC,optionD,answer,type,score)){
            return;
        }
        // Create a new Question object and add it to the database
        Question newQuestion = new Question(questionContent, optionA, optionB, optionC, optionD, answer, type, score);
        QuestionDatabase.add(newQuestion); // Assuming add method in Database class
        refreshQUI(null);
    }

    // Helper method to validate single answer
    static boolean isValidSingleAnswer(String answer) {
        return "A".equals(answer) || "B".equals(answer) || "C".equals(answer) || "D".equals(answer);
    }

    // Helper method to validate multiple answers
    static boolean isValidMultipleAnswer(String answer) {
        // Check if the answer contains only valid characters and has no duplicates
        // Check that the answer is not longer than 4 characters
        if (answer.length() > 4) {
            return false;
        }

        // Check for valid characters and ensure they are in order
        String validOptions = "ABCD";
        for (char c : answer.toCharArray()) {
            if (validOptions.indexOf(c) == -1) {
                return false; // Invalid character found
            }
        }
        // Check for duplicates
        for (int i = 0; i < answer.length(); i++) {
            for (int j = i + 1; j < answer.length(); j++) {
                if (answer.charAt(i) == answer.charAt(j)) {
                    return false; // Duplicate character found
                }
            }
        }
        // Ensure the answer is in the order of appearance in "ABCD"
        for (int i = 0; i < answer.length() - 1; i++) {
            if (answer.charAt(i) > answer.charAt(i + 1)) {
                return false; // Not in order
            }
        }

        return true; // All checks passed
    }

    public void deleteQuestion(ActionEvent actionEvent) {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();

        // Check if a question is selected
        if (selectedQuestion == null) {
            // Show an alert if no question is selected
            showMsg("No Selection","Please select a question to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this question?");
        confirmationAlert.setContentText("Question: " + selectedQuestion.getQuestionContent());

        // Show the confirmation dialog and wait for the user's response
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the question from the database
                QuestionDatabase.delByKey(String.valueOf(selectedQuestion.getId())); // Assuming you have a method to delete by ID

                // Refresh the table to show the updated list of questions
                refreshQUI(actionEvent);
            }
        });
    }

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

  public void updateQuestion(ActionEvent actionEvent) {
      Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
      String questionContent = newQuestionTextField.getText().trim();
      String optionA = optionATextField.getText().trim();
      String optionB = optionBTextField.getText().trim();
      String optionC = optionCTextField.getText().trim();
      String optionD = optionDTextField.getText().trim();
      String answer = AnswerTextField.getText().trim();
      String type = newTypeComboBox.getValue();
      String score = newScoreTextField.getText().trim();
      if(!Validation(questionContent,optionA,optionB,optionC,optionD,answer,type,score)){
          return;
      }
      if (selectedQuestion != null) {
          // Update the question's fields with values from the text fields
          selectedQuestion.setQuestionContent(questionContent);
          selectedQuestion.setOptionA(optionA);
          selectedQuestion.setOptionB(optionB);
          selectedQuestion.setOptionC(optionC);
          selectedQuestion.setOptionD(optionD);
          selectedQuestion.setAnswer(answer);
          selectedQuestion.setType(type);
          selectedQuestion.setScore(score);

          // Refresh the TableView to show updated data
          questionTable.refresh();

          // Optionally, show a confirmation message
          showMsg("Notice","Question updated successfully!");
      } else {
          showMsg("Notice","No question selected for update.");
      }
  }
}

