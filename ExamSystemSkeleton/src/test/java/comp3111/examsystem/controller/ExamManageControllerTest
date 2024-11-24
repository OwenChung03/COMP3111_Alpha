package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.tools.Database;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static comp3111.examsystem.controller.ExamManageController.*;
import static comp3111.examsystem.controller.QuestionManageController.*;
import static comp3111.examsystem.controller.TeacherLoginController.*;
import static org.junit.jupiter.api.Assertions.*;

class ExamManageControllerTest {
    @Test
    void FullInputExam() {
        String examName = "Quiz 1";
        String courseID = "Comp2211";
        String examTimeText = "10";
        String publishStatusText = "Yes";

        assertEquals(false,CheckEmptyExam(examName,courseID,examTimeText,publishStatusText));
    }
    @Test
    void EmptyInputExam() {
        String examName = "";
        String courseID = "Comp2211";
        String examTimeText = "10";
        String publishStatusText = "Yes";

        assertEquals(true,CheckEmptyExam(examName,courseID,examTimeText,publishStatusText));
    }
    @Test
    void EmptyInputExam1() {
        String examName = "Quiz 1";
        String courseID = null;
        String examTimeText = "10";
        String publishStatusText = "Yes";

        assertEquals(true,CheckEmptyExam(examName,courseID,examTimeText,publishStatusText));
    }
    @Test
    void EmptyInputExam2() {
        String examName = "Quiz 1";
        String courseID = "Comp2211";
        String examTimeText = "";
        String publishStatusText = "Yes";

        assertEquals(true,CheckEmptyExam(examName,courseID,examTimeText,publishStatusText));
    }
    @Test
    void EmptyInputExam3() {
        String examName = "Quiz 1";
        String courseID = "Comp2211";
        String examTimeText = "10";
        String publishStatusText = null;

        assertEquals(true,CheckEmptyExam(examName,courseID,examTimeText,publishStatusText));
    }
    @Test
    void ValidExamTime() {
        String examTimeText = "10";

        assertEquals(false,CheckTime(examTimeText));
    }
    @Test
    void InvalidExamTime() {
        String examTimeText = "-10";

        assertEquals(true,CheckTime(examTimeText));
    }
    @Test
    void InvalidExamTime1() {
        String examTimeText = "eh";

        assertEquals(true,CheckTime(examTimeText));
    }
    @Test
    void Empty_ExamName() {
        String examName = "Quiz1";
        String courseKey = "COMP2211";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam(examName, courseKey, examTime, publish, questionKeys);
        String new_examName = "";
        assertEquals(true,CheckExamMatch(exam,new_examName, courseKey, publish));
    }
    @Test
    void Wrong_ExamName() {
        String examName = "Quiz1";
        String courseKey = "COMP2211";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam(examName, courseKey, examTime, publish, questionKeys);
        String new_examName = "easy";
        assertEquals(false,CheckExamMatch(exam,new_examName, courseKey, publish));
    }
    @Test
    void Empty_CourseKey() {
        String examName = "";
        String courseKey = "COMP2211";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam("Quiz1", courseKey, examTime, publish, questionKeys);
        String new_courseKey = null;
        assertEquals(true,CheckExamMatch(exam,examName, new_courseKey, publish));
    }
    @Test
    void Wrong_CourseKey() {
        String examName = "";
        String courseKey = "COMP2211";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam("Quiz1", courseKey, examTime, publish, questionKeys);
        String new_courseKey = "easy";
        assertEquals(false,CheckExamMatch(exam,examName, new_courseKey, publish));
    }
    @Test
    void Empty_Publish() {
        String examName = "";
        String courseKey = "";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam("Quiz1", "COMP2211", examTime, publish, questionKeys);
        String new_publish = null;
        assertEquals(false,CheckExamMatch(exam,examName, courseKey, new_publish));
    }
    @Test
    void Wrong_Publish() {
        String examName = "";
        String courseKey = "";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam("Quiz1", "COMP2211", examTime, publish, questionKeys);
        String new_publish = "No";
        assertEquals(false,CheckExamMatch(exam,examName, courseKey, new_publish));
    }
    @Test
    void NullFilter() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "";

        assertEquals(true,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongQuestion() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "I don't know";
        String selectedType = "Single";
        String scoreText = "10";
        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongType() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "Multiple";
        String scoreText = "10";

        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void NullType() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = null;
        String scoreText = "10";

        assertEquals(true,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void InvalidScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "what";

        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "20";

        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void NullScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        question.setScore(null);
        String questionContent = "";
        String selectedType = "";
        String scoreText = "10";

        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void NegativeScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        question.setScore(null);
        String questionContent = "";
        String selectedType = "";
        String scoreText = "-10";

        assertEquals(false,CheckQuestionMatch(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void test_Validation() {
        String examName = "Quiz1";
        String courseID = "COMP2211";
        String examTime = "20";
        String publish = "Yes";

        assertEquals(false,Validation(examName,courseID,examTime,publish));
    }
    @Test
    void NullExam(){
        Exam exam = null;
        assertEquals(true,CheckNull(exam));
    }
    @Test
    void Non_NullExam(){
        String examName = "";
        String courseKey = "COMP2211";
        String examTime = "20";
        String publish = "Yes";
        String questionKeys = "0";
        Exam exam = new Exam("Quiz1", courseKey, examTime, publish, questionKeys);;
        assertEquals(false,CheckNull(exam));
    }

}
