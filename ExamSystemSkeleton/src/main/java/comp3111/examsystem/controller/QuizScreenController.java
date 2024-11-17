package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

public class QuizScreenController {
    @FXML
    private Button submitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

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
    private TextArea questionText;

    @FXML
    private RadioButton optionA, optionB, optionC, optionD;

    private ToggleGroup answerGroup;

    private int currentQuestionIndex = 0;
    private int totalQuestions = 4;  // Example with 4 questions
    private Timer quizTimer;
    private int remainingTime = 30;  // Example: 30 seconds for the quiz

    private List<String> studentAnswers; // To store the selected answers
    //private List<Question> questions;
    // QuestionLoader and ExamLoader instances
//    private QuestionLoader questionLoader;
//    private ExamLoader examLoader;


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
                // Update the timer on the JavaFX Application Thread
                Platform.runLater(() -> {
                    if (remainingTime > 0) {
                        remainingTime--;
                        updateTimerLabel();
                    } else {
                        quizTimer.cancel();
                        submitQuiz(null);  // Submit automatically when time is up
                    }
                });
            }
        }, 1000, 1000);  // Run every second
    }

    private void updateTimerLabel() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        timerLabel.setText(String.format("Remaining Time: %02d:%02d", minutes, seconds));
    }

    // Load the current question based on the index
    private void loadQuestion(int index) {
        questionLabel.setText("Question " + (index + 1));
        //questionText.setText(questionsListView.getSelectionModel().getSelectedItem());
        questionText.setText("What is the answer to this sample question?");
        optionA.setText("Option A");
        optionB.setText("Option B");
        optionC.setText("Option C");
        optionD.setText("Option D");

        // Highlight the current question in the ListView
        questionsListView.getSelectionModel().select(index);

        updateButtonVisibility();
    }

    // Update the visibility of the "Next" and "Previous" buttons

    private void updateButtonVisibility() {
        // Hide the "Previous" button if it's the first question
        if (currentQuestionIndex == 0) {
            prevButton.setVisible(false);
        } else {
            prevButton.setVisible(true);
        }

        // Hide the "Next" button if it's the last question
        if (currentQuestionIndex == totalQuestions - 1) {
            nextButton.setVisible(false);
        } else {
            nextButton.setVisible(true);
        }
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
        if (quizTimer != null) {
            quizTimer.cancel();  // Stop the timer when submitting
        }

        showAlert("xx/xx Correct, the precision is xx%, the score is xx/xx");
//        showConfirm("Notification","Quiz submitted!",);

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