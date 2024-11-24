package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.tools.Database;
import org.junit.jupiter.api.Test;


import static comp3111.examsystem.controller.QuestionManageController.*;
import static comp3111.examsystem.controller.TeacherLoginController.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionManageControllerTest {
    @Test
    void NullFilter() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "";

        assertEquals(true,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongQuestion() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "I don't know";
        String selectedType = "Single";
        String scoreText = "10";
        assertEquals(false,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongType() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "Multiple";
        String scoreText = "10";

        assertEquals(false,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void NullType() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = null;
        String scoreText = "10";

        assertEquals(true,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void InvalidScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "what";

        assertEquals(false,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void WrongScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        String questionContent = "";
        String selectedType = "";
        String scoreText = "20";

        assertEquals(false,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void NullScore() {
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        question.setScore(null);
        String questionContent = "";
        String selectedType = "";
        String scoreText = "10";

        assertEquals(false,QuestionChecking(question, questionContent, selectedType, scoreText) );
    }
    @Test
    void FullInput(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(false,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput(){
        String questionContent = "";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput1(){
        String questionContent = "a";
        String optionA = "";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput2(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput3(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput4(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput5(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput6(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "";
        String score = "10";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void EmptyInput7(){
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "";

        // Validate inputs
        assertEquals(true,CheckEmptyInput(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void SingleAnswer() {

        String answer = "A";
        // Validate inputs
        assertEquals(true, isValidSingleAnswer(answer));
    }
    @Test
    void SingleAnswer1() {

        String answer = "B";
        // Validate inputs
        assertEquals(true, isValidSingleAnswer(answer));
    }
    @Test
    void SingleAnswer2() {

        String answer = "C";
        // Validate inputs
        assertEquals(true, isValidSingleAnswer(answer));
    }
    @Test
    void SingleAnswer3() {

        String answer = "D";
        // Validate inputs
        assertEquals(true, isValidSingleAnswer(answer));
    }
    @Test
    void WrongSingleAnswer() {

        String answer = "E";
        // Validate inputs
        assertEquals(false, isValidSingleAnswer(answer));
    }
    @Test
    void MultipleAnswer() {

        String answer = "BD";
        // Validate inputs
        assertEquals(true, isValidMultipleAnswer(answer));
    }
    @Test
    void WrongMultipleAnswer() {

        String answer = "ABCDE";
        // Validate inputs
        assertEquals(false, isValidMultipleAnswer(answer));
    }
    @Test
    void WrongMultipleAnswer1() {

        String answer = "AA";
        // Validate inputs
        assertEquals(false, isValidMultipleAnswer(answer));
    }
    @Test
    void WrongMultipleAnswer2() {

        String answer = "DCB";
        // Validate inputs
        assertEquals(false, isValidMultipleAnswer(answer));
    }
    @Test
    void WrongMultipleAnswer3() {

        String answer = "EFG";
        // Validate inputs
        assertEquals(false, isValidMultipleAnswer(answer));
    }
    @Test
    void TestNegative() {

        String score = "-10";
        // Validate inputs
        assertEquals(true, CheckNegative(score));
    }
    @Test
    void TestNegative1() {

        String score = "10";
        // Validate inputs
        assertEquals(false, CheckNegative(score));
    }
    @Test
    void TestNegative2() {

        String score = "gg";
        // Validate inputs
        assertEquals(true, CheckNegative(score));
    }
    @Test
    void CorrectValidation() {
        String questionContent = "a";
        String optionA = "a";
        String optionB = "b";
        String optionC = "c";
        String optionD = "d";
        String answer = "A";
        String type = "Single";
        String score = "10";

        // Validate inputs
        assertEquals(true,Validation(questionContent,optionA,optionB,optionC,optionD,answer,type,score));
    }
    @Test
    void NullQuestion(){
        Question question = null;
        assertEquals(true,CheckNull(question));
    }
    @Test
    void Non_NullQuestion(){
        Question question = new Question("What is your name","owen","terry","jerry","other","D","Single","10");
        assertEquals(false,CheckNull(question));
    }
}