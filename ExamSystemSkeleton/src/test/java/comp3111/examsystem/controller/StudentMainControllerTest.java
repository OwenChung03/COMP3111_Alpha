package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentMainControllerTest {

    @Test
    void TestloadExams() {
        List<Exam> exams = null;

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.handleLoadedExams(exams));
    }

    @Test
    void TestloadExams2() {
        List<Exam> exams = new ArrayList<>();

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.handleLoadedExams(exams));
    }


    @Test
    void TestValidExamName1() {
        Optional<Exam> exam = Optional.empty();

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.ValidExamName(null));
    }

    @Test
    void TestValidExamName2() {
        Optional<Exam> exam = Optional.empty();

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.ValidExamName(""));
    }

    @Test
    void TestValidExamName3() {
        Optional<Exam> exam = Optional.empty();

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(true, studentMainController.ValidExamName("final exam"));
    }

    @Test
    void TestNonEmptyExam() {

        List<Exam> exams = new ArrayList<>();

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.NonEmptyExam(exams));
    }

    @Test
    void TestNonEmptyExam2() {

        List<Exam> exams = null;

        StudentMainController studentMainController = new StudentMainController();

        assertEquals(false, studentMainController.NonEmptyExam(exams));
    }


}
