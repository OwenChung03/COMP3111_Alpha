package comp3111.examsystem.controller;

import org.junit.jupiter.api.Test;

import static comp3111.examsystem.controller.TeacherLoginController.checkOddOrEven;
import static org.junit.jupiter.api.Assertions.*;

class TeacherLoginControllerTest {

    @Test
    void test_zero() {
        int test_var = 0;
        assertEquals("Even",checkOddOrEven(test_var));
    }

    @Test
    void test_negative() {
        int test_var = -2;
        assertEquals("Even",checkOddOrEven(test_var));
    }
}