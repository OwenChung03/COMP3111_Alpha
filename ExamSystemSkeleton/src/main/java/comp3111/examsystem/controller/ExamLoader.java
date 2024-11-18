package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.Database;

import java.util.ArrayList;
import java.util.List;

public class ExamLoader {

    // Load exams from the exam.txt file
    public List<Exam> loadExamsFromDatabase() {
        Database<Exam> examDatabase = new Database<>(Exam.class); // Assuming Exam has a no-arg constructor
        return examDatabase.getAll();
    }

    // Load a specific exam by its ID
    public Exam loadExamById(Long examId) {
        Database<Exam> examDatabase = new Database<>(Exam.class);
        return examDatabase.queryByKey(examId.toString());
    }

    // Get all questions for a specific exam
    public List<Question> loadQuestionsForExam(Exam exam) {
        List<Long> questionKeys = exam.getQuestionKeys(); // questionKeys are stored as Long
        List<String> questionKeyStrings = convertToStringList(questionKeys); // Convert to List<String>

        QuestionLoader questionLoader = new QuestionLoader();
        return questionLoader.loadQuestionsByIds(questionKeyStrings);
    }

    // Helper method to convert List<Long> to List<String>
    private List<String> convertToStringList(List<Long> longList) {
        List<String> stringList = new ArrayList<>();
        for (Long l : longList) {
            stringList.add(l.toString());
        }
        return stringList;
    }


}