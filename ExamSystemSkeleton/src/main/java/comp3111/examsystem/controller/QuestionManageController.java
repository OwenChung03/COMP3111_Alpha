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
import java.util.ArrayList;
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
        // Clear the text field for the question content
        questionTextField.clear();
        // Reset the type combo box to its default state (no selection)
        TypeComboBox.setValue(null);
        // Clear the text field for the score
        scoreTextField.clear();
        // Refresh the question table to show all questions without filters
        refreshQUI(actionEvent);
    }

    public void queryQUI(ActionEvent actionEvent) {
        // Get the filter values from the UI components
        String questionContent = questionTextField.getText().toLowerCase().trim();
        String selectedType = TypeComboBox.getValue();
        String scoreText = scoreTextField.getText().trim();

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
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();

        // Check if a question is selected
        if (selectedQuestion == null) {
            // Show an alert if no question is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a question to delete.");
            alert.showAndWait();
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

  public void updateQuestion(ActionEvent actionEvent) {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
//getSelectedItem
        // Check if a question is selected
        if (selectedQuestion == null) {
            // Show an alert if no question is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a question to update.");
            alert.showAndWait();
            return;
        }

        // Retrieve the updated values from the input fields
        String updatedQuestionContent = newQuestionTextField.getText().trim();
        String updatedOptionA = optionATextField.getText().trim();
        String updatedOptionB = optionBTextField.getText().trim();
        String updatedOptionC = optionCTextField.getText().trim();
        String updatedOptionD = optionDTextField.getText().trim();
        String updatedAnswer = AnswerTextField.getText().trim();
        String updatedType = newTypeComboBox.getValue();
        String updatedScore = newScoreTextField.getText().trim();

        // Check for changes
        boolean isChanged = false;

        if (!updatedQuestionContent.equals(selectedQuestion.getQuestionContent())) {
            selectedQuestion.setQuestionContent(updatedQuestionContent);
            isChanged = true;
        }

        if (!updatedOptionA.equals(selectedQuestion.getOptionA())) {
            selectedQuestion.setOptionA(updatedOptionA);
            isChanged = true;
        }

        if (!updatedOptionB.equals(selectedQuestion.getOptionB())) {
            selectedQuestion.setOptionB(updatedOptionB);
            isChanged = true;
        }

        if (!updatedOptionC.equals(selectedQuestion.getOptionC())) {
            selectedQuestion.setOptionC(updatedOptionC);
            isChanged = true;
        }

        if (!updatedOptionD.equals(selectedQuestion.getOptionD())) {
            selectedQuestion.setOptionD(updatedOptionD);
            isChanged = true;
        }

        if (!updatedAnswer.equals(selectedQuestion.getAnswer())) {
            selectedQuestion.setAnswer(updatedAnswer);
            isChanged = true;
        }

        if (!updatedType.equals(selectedQuestion.getType())) {
            selectedQuestion.setType(updatedType);
            isChanged = true;
        }

        if (!updatedScore.equals(selectedQuestion.getScore())) {
            selectedQuestion.setScore(updatedScore);
            isChanged = true;
        }

        // If changes were made, update the database
        if (isChanged) {
            QuestionDatabase.update(selectedQuestion); // Assuming you have an update method in your Database class
            refreshQUI(actionEvent); // Refresh the table to show updated values
        } else {
            // Show a reminder message if no changes were made
            Alert noChangeAlert = new Alert(Alert.AlertType.INFORMATION);
            noChangeAlert.setTitle("No Changes Made");
            noChangeAlert.setHeaderText(null);
            noChangeAlert.setContentText("No changes have been made to the question.");
            noChangeAlert.showAndWait();
        }
    }

}
