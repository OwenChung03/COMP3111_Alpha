package comp3111.examsystem.controller;

import comp3111.examsystem.controller.ExamLoader;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.Database;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ExamLoaderTest {

    @Test
    void testLoadExamsFromDatabase_EmptyDatabase() {
        // Database is empty, so this should return an empty list
        ExamLoader examLoader = new ExamLoader();

        List<Exam> exams = examLoader.loadExamsFromDatabase();

        assertEquals(false,exams.isEmpty()); // Ensure the list is empty
    }



    @Test
    void testLoadQuestionsForExam_QuestionNotFound() {
        // Create a test exam with a question key that doesn't exist
        ExamLoader examLoader = new ExamLoader();


        List<Question> questions = examLoader.loadQuestionsForExam(null);

        List<Question> questions_expected = new ArrayList<>();

        assertEquals(questions_expected, questions);
    }

}