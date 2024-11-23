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
    void Wrongpassword(){
        Database<Teacher> TeacherDatabase;
        TeacherDatabase = new Database<>(Teacher.class);
        String username = "hi";
        String password = "bye";
        List< Teacher> teachers = TeacherDatabase.queryByField("username", username);

        assertEquals(false,Checkpassword(teachers, password));
    }
}