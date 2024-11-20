package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.Database;

import java.util.List;

public class StudentGradeCalculator {

    // Calculate the grade for a student based on their answers
    public int calculateGrade(Exam exam, List<String> studentAnswers, List<Question> questions) {
        int totalScore = 0;

        // Iterate through the questions in the exam
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String correctAnswer = question.getAnswer();  // Get the correct answer
            String studentAnswer = studentAnswers.get(i);  // Get the student's answer

            // Check if the answer is correct
            if (isAnswerCorrect(question, studentAnswer, correctAnswer)) {
                int questionScore = Integer.parseInt(question.getScore());
                totalScore += questionScore;  // Add the score for the correct answer
            }
        }

        return totalScore;  // Return the total score for the exam
    }

    // Helper method to check if the student's answer is correct
    private boolean isAnswerCorrect(Question question, String studentAnswer, String correctAnswer) {
        if (question.getType().equals("Single")) {
            // Single answer type: simple string comparison
            return studentAnswer.equals(correctAnswer);
        } else if (question.getType().equals("Multiple")) {
            // Multiple answer type: check if all correct answers are selected
            return studentAnswer.equals(correctAnswer);  // You can add more complex logic here if needed
        }

        return false;  // Default to false if unknown question type
    }
}