package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.List;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

import static comp3111.examsystem.tools.MsgSender.showMsg;

public class ExamScreenController {

    @FXML
    private Button submitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Label examTitleLabel;

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
    private Timer quizTimer;
    private int remainingTime = 30;  // Example: 30 seconds for the quiz
    private int totalQuestions;
    private List<Question> questions;  // List to store the loaded quiz questions
    private List<String> studentAnswers; // To store the selected answers

    private Exam exam;  // The selected exam object

    @FXML
    public void initialize() {
        // Initialize the ToggleGroup for the radio buttons
        answerGroup = new ToggleGroup();
        optionA.setToggleGroup(answerGroup);
        optionB.setToggleGroup(answerGroup);
        optionC.setToggleGroup(answerGroup);
        optionD.setToggleGroup(answerGroup);

        // The question loading will happen after setting the exam
    }

    // Call this method to set the exam and load the corresponding quiz questions
//    public void setExam(Exam exam) {
//        this.exam = exam;
//        loadExam();  // Load questions after setting the exam
//    }

    
    // Method to load Exam from the text file
//    private void loadExam() {
//        examTitleLabel.setText("Exam: " + exam.getExamName());
//        try {
//            ExamLoader examLoader = new ExamLoader();
//            List<Question> loadedQuestions = examLoader.loadQuestionsForExam(exam);
//
//            if (loadedQuestions != null && !loadedQuestions.isEmpty()) {
//                questions = loadedQuestions;
//                totalQuestions = questions.size();
//                totalQuestionsLabel.setText("Total Questions: " + totalQuestions);
//
//                // Load the first question
//                loadQuestion(currentQuestionIndex);
//            } else {
//                showMsg("Error","No questions found for this exam.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            showMsg("Error","Error: Failed to load quiz questions.");
//        }
//    }

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
        if (index < 0 || index >= questions.size()) {
            return;  // Ensure index is within bounds
        }

        Question currentQuestion = questions.get(index);
        questionLabel.setText("Question " + (index + 1));
        questionText.setText(currentQuestion.getQuestionContent());
        optionA.setText(currentQuestion.getOptionA());
        optionB.setText(currentQuestion.getOptionB());
        optionC.setText(currentQuestion.getOptionC());
        optionD.setText(currentQuestion.getOptionD());

        // Highlight the current question in the ListView
        questionsListView.getSelectionModel().select(index);

        updateButtonVisibility();
    }

    // Update the visibility of the "Next" and "Previous" buttons
    private void updateButtonVisibility() {
        // Hide the "Previous" button if it's the first question
        prevButton.setVisible(currentQuestionIndex > 0);

        // Hide the "Next" button if it's the last question
        nextButton.setVisible(currentQuestionIndex < totalQuestions - 1);
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

        showMsg("Result","xx/xx Correct, the precision is xx%, the score is xx/xx");

        // Close the quiz window after submission
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

}