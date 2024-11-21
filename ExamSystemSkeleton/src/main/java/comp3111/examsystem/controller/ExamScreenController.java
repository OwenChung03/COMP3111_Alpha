package comp3111.examsystem.controller;

import comp3111.examsystem.entity.*;
import comp3111.examsystem.tools.Database;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.time.Duration;
import java.time.LocalDateTime;


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
    private LocalDateTime examStartTime;
    private LocalDateTime examEndTime;
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

        startTimer();

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
                showMsg("Error","No questions found for this exam.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMsg("Error","Error: Failed to load quiz questions.");
        }
    }

    // Start the quiz countdown timer
    private void startTimer() {
        examStartTime = LocalDateTime.now();

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
        try {
            if (examTimer != null) {
                examTimer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();

            showMsg("Error","Exam Timer Error");

        }

        examEndTime = LocalDateTime.now();  // Record the end time of the exam
        Duration timeSpent = Duration.between(examStartTime, examEndTime);  // Calculate the time spent

        long minutesSpent = timeSpent.toMinutes();  // Total minutes spent on the exam
        long secondsSpent = timeSpent.getSeconds() % 60;  // Seconds part

        saveCurrentAnswer();

        // Calculate the score
        int[] result = calculateScore();
        int totalScore = result[0];
        int fullScore = result[1];


        // Get the logged-in student from the StudentLoginController
        Student loggedInStudent = StudentLoginController.getLoggedInStudent();

        if (loggedInStudent != null) {
            // Create a StudentExamGrade object for saving

            String studentIdStr = String.valueOf(loggedInStudent.getId());  // Convert student ID to String
            String courseIdStr = String.valueOf(exam.getCourseKey());
            String examNameStr = String.valueOf(exam.getExamName());                // Convert exam ID to String
            String totalScoreStr = String.valueOf(totalScore);           // Convert total score to String
            String fullScoreStr = String.valueOf(fullScore);
            String timeSpentStr = String.format("%d min %d sec", minutesSpent, secondsSpent);

            StudentGradeData studentGradeData = new StudentGradeData(studentIdStr,courseIdStr, examNameStr,
                    totalScoreStr,fullScoreStr,timeSpentStr);

            try {
                Database<StudentGradeData> database = new Database<>(StudentGradeData.class);
                database.add(studentGradeData);  // Save the grade to the database

            } catch (Exception e) {
                e.printStackTrace();
                showMsg("Error","Error: Could not save grade to the database.");
            }
        } else {
            showMsg("Error","Error: No logged-in student found.");
        }

        // Close the exam screen
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    private int[] calculateScore() {
        int correctAnswerCount = 0;
        int totalScore = 0;  // The student's total score
        int fullScore = 0;    // The total possible score (full mark)

        // Loop through all the questions and calculate the score
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String correctAnswer = question.getAnswer();  // Get the correct answer from the question
            String studentAnswer = studentAnswers.size() > i ? studentAnswers.get(i) : null;  // Get student's answer if available

            // Convert the score from String to Integer
            int questionScore;
            try {
                questionScore = Integer.parseInt(question.getScore());  // Assuming score is stored as a string
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid score format for question: " + question.getQuestionContent());
                continue;  // Skip this question if the score can't be parsed
            }

            // Add the score of the current question to the full mark
            fullScore += questionScore;

            // Compare student's answer with the correct answer
            if (studentAnswer != null && studentAnswer.equals(correctAnswer)) {
                // If the answer is correct, add the question's score to totalScore
                totalScore += questionScore;
                correctAnswerCount += 1;

            }
        }

        double precision = ((double)totalScore / fullScore) * 100;

        // Format the message to be displayed in the alert
        String message = String.format("%d/%d Correct, the precision is %.2f%%, the score is %d/%d",
                correctAnswerCount, totalQuestions, precision, totalScore, fullScore);

        showMsg("Result",message);


        return new int[] { totalScore, fullScore };  // Return an integer array with total score and full score
    }

}