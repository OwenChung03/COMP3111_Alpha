package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.Database;

import java.util.ArrayList;
import java.util.List;
import static comp3111.examsystem.tools.MsgSender.showMsg;

public class ExamLoader {

    // Load exams from the exam.txt file
    public List<Exam> loadExamsFromDatabase() {
        Database<Exam> examDatabase = new Database<>(Exam.class);
        List<Exam> exams = new ArrayList<>();

        try {
            exams = examDatabase.getAll();  // Load all exams from the exam.txt file
            if (exams.isEmpty()) {

                showMsg("Error","Error: Failed to load exams. No exams found in the database.");
            }
        } catch (Exception e) {
            showMsg("Error","Error: Failed to load exams due to an exception.(loadExamsFromDatabase)");

            e.printStackTrace();  // Log the exception for debugging
        }

        return exams;
    }

    // Load a specific exam by its ID
    // Load a specific exam by its ID
    public Exam loadExamById(Long examId) {
        Database<Exam> examDatabase = new Database<>(Exam.class);
        Exam exam = null;

        try {
            exam = examDatabase.queryByKey(examId.toString());  // Query exam by ID
            if (exam == null) {

                showMsg("Error","Error: Failed to load exam with ID: " + examId);
            }
        } catch (Exception e) {
            showMsg("Error","Error: Failed to load exam due to an exception.(loadExamById)");

            e.printStackTrace();
        }

        return exam;
    }


    // Get all questions for a specific exam
    public List<Question> loadQuestionsForExam(Exam exam) {
        Database<Question> questionDatabase = new Database<>(Question.class);
        List<Question> questions = new ArrayList<>();

        // Get the question keys from the exam
        String questionKeys = exam.getQuestionKeys();

        // Check if questionKeys is not null or empty
        if (questionKeys == null || questionKeys.isEmpty()) {

            showMsg("Error","No questions found for this exam.");

            return questions;  // Return empty list if no question keys
        }

        // Split the question keys string into individual keys (assuming '/' is the delimiter)
        String[] keys = questionKeys.split("/");

        // Query each question by its key and add it to the questions list
        for (String key : keys) {
            try {

                Question question = questionDatabase.queryByKey(key.trim());
                // Trim any extra spaces
                if (question != null) {
                    questions.add(question);  // Add the question to the list if found
                } else {

                    showMsg("Error","Warning: Question with key " + key + " not found.");
                }
            } catch (Exception e) {
                showMsg("Error","Error: Failed to load question with key: " + key);
                e.printStackTrace();  // Log the exception for debugging
            }
        }

        return questions;  // Return the list of questions

    }



}