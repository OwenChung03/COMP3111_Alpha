package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.tools.Database;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static comp3111.examsystem.controller.StudentLoginController.*;
import static org.junit.jupiter.api.Assertions.*;
class StudentLoginControllerTest {

    @Test
    void InvalidLogin() {
        String username = "";
        String password = "fea";

        assertEquals(false,isValidLogin(username, password));
    }

    @Test
    void InvalidLogin2() {
        String username = "efa";
        String password = "";

        assertEquals(false,isValidLogin(username, password));
    }

    @Test
    void ValidLogin() {
        String username = "fae";
        String password = "fea";

        assertEquals(true,isValidLogin(username, password));
    }



    @Test
    void InvalidAge() {
        String ageString = "-2";

        assertEquals(false, AgeCheck(ageString));
    }

    @Test
    void InvalidAge2() {
        String ageString = "abc";

        assertEquals(false, AgeCheck(ageString));
    }

    @Test
    void ValidAge() {
        String ageString = "20";

        assertEquals(true, AgeCheck(ageString));
    }

    @Test
    void ValidAge2() {
        String ageString = "-5";

        assertEquals(false, AgeCheck(ageString));
    }

    @Test
    void InvalidPasswordConfirmed() {
        String password = "123456";
        String confirmPassword = "1234567";

        assertEquals(false, ConfirmedPasswordCheck(password,confirmPassword));
    }

    @Test
    void InvalidPasswordConfirmed2() {
        String password = "";
        String confirmPassword = "1234567";

        assertEquals(false, ConfirmedPasswordCheck(password,confirmPassword));
    }


    @Test
    void ValidPasswordConfirmed() {
        String password = "abc123";
        String confirmPassword = "abc123";

        assertEquals(true, ConfirmedPasswordCheck(password,confirmPassword));
    }

    @Test
    void TestForEmptyRegisterCheck() {
        assertEquals(false,EmptyRegisterCheck("terry","Terry",
                "Male","12","FINA","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck2() {
        assertEquals(true,EmptyRegisterCheck("","Terry",
                "Male","12","FINA","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck3() {
        assertEquals(true,EmptyRegisterCheck("terry","",
                "Male","12","FINA","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck4() {
        assertEquals(true,EmptyRegisterCheck("terry","fea",
                null,"12","FINA","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck5() {
        assertEquals(true,EmptyRegisterCheck("terry","fea",
                "Male","","FINA","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck6() {
        assertEquals(true,EmptyRegisterCheck("terry","fea",
                "Male","18","","1234","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck7() {
        assertEquals(true,EmptyRegisterCheck("terry","fea",
                "Male","18","FINA","","1234") );
    }

    @Test
    void TestForEmptyRegisterCheck8() {
        assertEquals(true,EmptyRegisterCheck("terry","fea",
                "Male","18","FINA","1234","") );
    }

    @Test
    void NonExistingUser() {
        Database<Student> mockDatabase = new Database<>(Student.class); // Create a mock database
        StudentLoginController.studentDatabase = mockDatabase; // Set the static database

        String username = "nonexistent_user";

        assertEquals(false,CheckExistingUser(username));
    }


    @Test
    void ExistingUser(){
        String username = "abc";
        //Database<Student> eacherDatabase;
        studentDatabase = new Database<>(Student.class);
        assertEquals(true,CheckExistingUser(username));
    }


    @Test
    void LoginUser2(){
        assertEquals(null,getLoggedInStudent());
    }

    @Test
    void PasswordMatchTest(){
        Database<Student> mockDatabase = new Database<>(Student.class); // Create a mock database
        StudentLoginController.studentDatabase = mockDatabase; // Set the static database

        String username = "abc";
        List<Student> students = mockDatabase.queryByField("username", username);

        assertEquals(true,passwordCorrect(students,"123"));
    }

    @Test
    void PasswordMatchTest2(){
        Database<Student> mockDatabase = new Database<>(Student.class); // Create a mock database
        StudentLoginController.studentDatabase = mockDatabase; // Set the static database

        String username = "abc";
        List<Student> students = mockDatabase.queryByField("username", username);

        assertEquals(false,passwordCorrect(students,"abc"));
    }

    @Test
    void emptyStudentTest(){

        Database<Student> mockDatabase = new Database<>(Student.class); // Create a mock database
        StudentLoginController.studentDatabase = mockDatabase; // Set the static database

        List<Student> students = mockDatabase.queryByField("username", "abc");
        assertEquals(false,emptyStudent(students));
    }

    @Test
    void emptyStudentTest2(){

        Database<Student> mockDatabase = new Database<>(Student.class); // Create a mock database
        StudentLoginController.studentDatabase = mockDatabase; // Set the static database

        List<Student> students = mockDatabase.queryByField("username", "123");
        assertEquals(true,emptyStudent(students));
    }





}