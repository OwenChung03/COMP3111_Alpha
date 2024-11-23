package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.tools.Database;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import static comp3111.examsystem.controller.TeacherLoginController.*;
import static org.junit.jupiter.api.Assertions.*;

class TeacherLoginControllerTest {

    @Test
    void invalidLogin() {
        String username = "";
        String password = "";

        assertEquals(false,ValidLogin(username, password));
    }

    @Test
    void validLogin() {
        String username = "hi";
        String password = "hi";

        assertEquals(true,ValidLogin(username, password));
    }

    @Test
    void EmptyteacherList() {
        Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        String username = "";
        List< Teacher> teachers = TeacherDatabase.queryByField("username", username);

        assertEquals(true,CheckEmpty(teachers));
    }
    @Test
    void NonEmptyteacherList() {
        Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        String username = "hi";
        List< Teacher> teachers = TeacherDatabase.queryByField("username", username);

        assertEquals(false,CheckEmpty(teachers));
    }
    @Test
    void Wrongpassword(){
        Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        String username = "hi";
        String password = "bye";
        List< Teacher> teachers = TeacherDatabase.queryByField("username", username);

        assertEquals(false,Checkpassword(teachers, password));
    }
    @Test
    void Rightpassword(){
        Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        String username = "hi";
        String password = "hi";
        List< Teacher> teachers = TeacherDatabase.queryByField("username", username);

        assertEquals(true,Checkpassword(teachers, password));
    }
    @Test
    void WrongRegister(){
        String username = "a";
        String name = "a";
        String age = "20";
        String gender = "Male";
        String position = "Professor";
        String department = "CSE";
        String password = "a";
        String passwordConfirm = "";
        assertEquals(false,CheckRegister(username, name, gender, age, position, department, password, passwordConfirm));
    }
    @Test
    void RightRegister(){
        String username = "a";
        String name = "a";
        String age = "20";
        String gender = "Male";
        String position = "Professor";
        String department = "CSE";
        String password = "a";
        String passwordConfirm = "a";
        assertEquals(true,CheckRegister(username, name, gender, age, position, department, password, passwordConfirm));
    }
    @Test
    void wrongAge(){
        String age = "-20";
        assertEquals(false,CheckAge(age));
    }
    @Test
    void wrongAge2(){
        String age = "rwer";
        assertEquals(false,CheckAge(age));
    }
    @Test
    void RightAge(){
        String age = "20";
        assertEquals(true,CheckAge(age));
    }
    @Test
    void WrongPasswordRegister(){
        String password = "easy";
        String passwordConfirm = "hard";
        assertEquals(false,checkPassword(password, passwordConfirm));
    }
    @Test
    void RightPasswordRegister(){
        String password = "easy";
        String passwordConfirm = "easy";
        assertEquals(true,checkPassword(password, passwordConfirm));
    }
    @Test
    void SameUser(){
        String username = "hi";
        //Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        assertEquals(true,checkUser(username));
    }

    @Test
    void DifferentUser(){
        String username = "idontknow";
        //Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        assertEquals(false,checkUser(username));
    }

}