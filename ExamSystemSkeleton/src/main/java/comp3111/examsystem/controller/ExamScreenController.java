package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.StudentExamGrade;
import comp3111.examsystem.tools.Database;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    @FXML
    private CheckBox optionA_CB, optionB_CB, optionC_CB, optionD_CB;

    private ToggleGroup answerGroup;

    private int currentQuestionIndex = 0;
    private Timer examTimer;
    private int remainingTime = 30;  // Example: 30 seconds for the quiz
    private int totalQuestions;
    private List<Question> questions;  // List to store the loaded quiz questions
    private List<String> studentAnswers; // To store the selected answers

    private Exam exam;  // The selected exam object

    @FXML
    public void initialize() {
        // Initialize the ToggleGroup for the radio buttons
        answerGroup = new ToggleGroup();
        studentAnswers = new ArrayList<>();  // To store the selected answers

        optionA.setToggleGroup(answerGroup);
        optionB.setToggleGroup(answerGroup);
        optionC.setToggleGroup(answerGroup);
        optionD.setToggleGroup(answerGroup);

        // The question loading will happen after setting the exam
    }

    // Call this method to set the exam and load the corresponding quiz questions
    public void setExam(Exam exam) {
        this.exam = exam;
        loadExam();  // Load questions after setting the exam
    }

    
    // Method to load Exam from the text file
    private void loadExam() {
        examTitleLabel.setText("Exam: " + exam.getExamName());
        try {
            ExamLoader examLoader = new ExamLoader();
            List<Question> loadedQuestions = examLoader.loadQuestionsForExam(exam);

            if (loadedQuestions != null && !loadedQuestions.isEmpty()) {
                questions = loadedQuestions;
                totalQuestions = questions.size();
                totalQuestionsLabel.setText("Total Questions: " + totalQuestions);

                // Load the first question
                loadQuestion(currentQuestionIndex);
            } else {
                showMsg("No questions found for this exam.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMsg("Error: Failed to load quiz questions.");
        }
    }

    // Start the quiz countdown timer
    private void startTimer() {
        examTimer = new Timer();
        examTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Update the timer on the JavaFX Application Thread
                Platform.runLater(() -> {
                    if (remainingTime > 0) {
                        remainingTime--;
                        updateTimerLabel();
                    } else {
                        examTimer.cancel();
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

    // Modify the loadQuestion method to handle both Single and Multiple question types
    private void loadQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            return;  // Ensure index is within bounds
        }

        Question currentQuestion = questions.get(index);
        questionLabel.setText("Question " + (index + 1));
        questionText.setText(currentQuestion.getQuestionContent());

        // Check the question type to decide whether to use RadioButtons or CheckBoxes
        if (currentQuestion.getType().equals("Single")) {
            // Show RadioButtons for Single question type
            showRadioButtons();
            optionA.setText(currentQuestion.getOptionA());
            optionB.setText(currentQuestion.getOptionB());
            optionC.setText(currentQuestion.getOptionC());
            optionD.setText(currentQuestion.getOptionD());
        } else if (currentQuestion.getType().equals("Multiple")) {
            // Show CheckBoxes for Multiple question type
            showCheckBoxes();
            optionA_CB.setText(currentQuestion.getOptionA());
            optionB_CB.setText(currentQuestion.getOptionB());
            optionC_CB.setText(currentQuestion.getOptionC());
            optionD_CB.setText(currentQuestion.getOptionD());
        }

        // Highlight the current question in the ListView
        questionsListView.getSelectionModel().select(index);

        updateButtonVisibility();
    }

    // Show RadioButtons and hide CheckBoxes
    private void showRadioButtons() {
        optionA.setVisible(true);
        optionB.setVisible(true);
        optionC.setVisible(true);
        optionD.setVisible(true);

        optionA_CB.setVisible(false);
        optionB_CB.setVisible(false);
        optionC_CB.setVisible(false);
        optionD_CB.setVisible(false);
    }

    // Show CheckBoxes and hide RadioButtons
    private void showCheckBoxes() {
        optionA.setVisible(false);
        optionB.setVisible(false);
        optionC.setVisible(false);
        optionD.setVisible(false);

        optionA_CB.setVisible(true);
        optionB_CB.setVisible(true);
        optionC_CB.setVisible(true);
        optionD_CB.setVisible(true);
    }

    // Update the visibility of the "Next" and "Previous" buttons
    private void updateButtonVisibility() {
        // Hide the "Previous" button if it's the first question
        prevButton.setVisible(currentQuestionIndex > 0);

        // Hide the "Next" button if it's the last question
        nextButton.setVisible(currentQuestionIndex < totalQuestions - 1);
    }

    private void saveCurrentAnswer() {
        String selectedAnswer = null;

        Question currentQuestion = questions.get(currentQuestionIndex);

        // For Single-type questions, only one option is selected
        if (currentQuestion.getType().equals("Single")) {
            if (optionA.isSelected()) {
                selectedAnswer = "A";
            } else if (optionB.isSelected()) {
                selectedAnswer = "B";
            } else if (optionC.isSelected()) {
                selectedAnswer = "C";
            } else if (optionD.isSelected()) {
                selectedAnswer = "D";
            }
        }
        // For Multiple-type questions, the student can select multiple options
        else if (currentQuestion.getType().equals("Multiple")) {
            StringBuilder selectedAnswerBuilder = new StringBuilder();

            if (optionA_CB.isSelected()) {
                selectedAnswerBuilder.append("A");
            }
            if (optionB_CB.isSelected()) {
                selectedAnswerBuilder.append("B");
            }
            if (optionC_CB.isSelected()) {
                selectedAnswerBuilder.append("C");
            }
            if (optionD_CB.isSelected()) {
                selectedAnswerBuilder.append("D");
            }

            selectedAnswer = selectedAnswerBuilder.toString();  // Combine all selected options
        }

        // Ensure the studentAnswers list has enough space for all questions
        if (studentAnswers.size() > currentQuestionIndex) {
            studentAnswers.set(currentQuestionIndex, selectedAnswer);  // Update existing answer
        } else {
            studentAnswers.add(selectedAnswer);  // Add a new answer
        }
    }

    @FXML
    public void nextQuestion(ActionEvent event) {
        saveCurrentAnswer();  // Save the currently selected answer

        if (currentQuestionIndex < totalQuestions - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        }
    }

    @FXML
    public void prevQuestion(ActionEvent event) {
        saveCurrentAnswer();  // Save the currently selected answer

        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }

    @FXML
    public void submitQuiz(ActionEvent event) {
        if (examTimer != null) {
            examTimer.cancel();  // Stop the timer when submitting
        }

        saveCurrentAnswer();  // Save the answer for the final question

        // Calculate the score
        StudentGradeCalculator gradeCalculator = new StudentGradeCalculator();
        int totalScore = gradeCalculator.calculateGrade(exam, studentAnswers, questions);

        // Calculate the number of correct answers and precision
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (gradeCalculator.isAnswerCorrect(questions.get(i), studentAnswers.get(i), questions.get(i).getAnswer())) {
                correctAnswers++;
            }
        }

        int totalQuestions = questions.size();
        double precision = (double) correctAnswers / totalQuestions * 100;  // Precision in percentage

        // Show the results
        showMsg(correctAnswers + "/" + totalQuestions + " correct, the precision is " + String.format("%.2f", precision) +
                "%, the score is " + totalScore + "/" + (totalQuestions * 10));  // Assuming each question is worth 10 points

        // Create a StudentExamGrade object
        StudentExamGrade studentExamGrade = new StudentExamGrade(exam.getStudentId(), exam.getId(), totalScore);

        // Save the grade using the Database class
        try {
            Database database = new Database();
            database.add(studentExamGrade);  // Assuming Database.java has a method `persist()` or `saveEntity()`
        } catch (Exception e) {
            e.printStackTrace();
            showMsg("Error: Could not save grade to the database.");
        }

        // Close the quiz window after submission
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

}