package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.tools.Database;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static comp3111.examsystem.controller.StudentLoginController.*;
import static org.junit.jupiter.api.Assertions.*;

class ExamScreenControllerTest {

    void TestLoadQuestion(){
        ExamScreenController examScreenController = new ExamScreenController();
        assertEquals(false,examScreenController.loadQuestion(0));
    }

    void TestSaveQuestion(){
        ExamScreenController examScreenController = new ExamScreenController();

    }

}