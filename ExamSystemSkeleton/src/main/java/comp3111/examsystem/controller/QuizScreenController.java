package comp3111.examsystem.controller;

import comp3111.examsystem.tools.MsgSender;
import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

public class QuizScreenController {
    @FXML
    private Button submitButton;

    @FXML
    private Label quizTitleLabel;

    @FXML
    private Label totalQuestionsLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private ListView<String> questionsListView;

    @FXML
    private Label questionLabel;

    @FXML
    private RadioButton optionA, optionB, optionC, optionD;

    private ToggleGroup answerGroup;

    private int currentQuestionIndex = 0;
    private int totalQuestions = 4;  // Example with 4 questions
    private Timer quizTimer;
    private int remainingTime = 30;  // Example: 30 seconds for the quiz

    @FXML
    public void initialize() {
        // Initialize the ToggleGroup for the radio buttons
        answerGroup = new ToggleGroup();
        optionA.setToggleGroup(answerGroup);
        optionB.setToggleGroup(answerGroup);
        optionC.setToggleGroup(answerGroup);
        optionD.setToggleGroup(answerGroup);

        // Load the first question
        loadQuestion(currentQuestionIndex);

        // Start the timer
        startTimer();
    }

    // Set the quiz title and initialize the quiz
    public void setQuiz(String quizTitle) {
        quizTitleLabel.setText(quizTitle);
        totalQuestionsLabel.setText("Total Questions: " + totalQuestions);
    }

    // Start the quiz countdown timer
    private void startTimer() {
        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                timerLabel.setText("Remaining Time: " + remainingTime + "s");
                if (remainingTime <= 0) {
                    quizTimer.cancel();
                    submitQuiz(null);
                }
            }
        }, 1000, 1000);  // Run every second
    }

    // Load the current question based on the index
    private void loadQuestion(int index) {
        questionLabel.setText("Question " + (index + 1) + ": What is the answer to this sample question?");
        optionA.setText("Option A");
        optionB.setText("Option B");
        optionC.setText("Option C");
        optionD.setText("Option D");

        // Highlight the current question in the ListView
        questionsListView.getSelectionModel().select(index);
    }

    @FXML
    public void nextQuestion(ActionEvent event) {
        if (currentQuestionIndex < totalQuestions - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    }

    @FXML
    public void prevQuestion(ActionEvent event) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }

    @FXML
    public void submitQuiz(ActionEvent event) {
        quizTimer.cancel();  // Stop the timer when submitting
        showAlert("Quiz submitted!");

        // Here you can add logic to grade the quiz and show the result (not implemented)

        // Close the quiz window after submission
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    // Utility method to show alert messages
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}