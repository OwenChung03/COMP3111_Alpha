package comp3111.examsystem.controller;

import static comp3111.examsystem.controller.StudentLoginController.isValidLogin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class StudentLoginControllerTest {

    @Test
    void EmptyLogin() {
        String username = "";
        String password = "";

        assertEquals(false,isValidLogin(username, password));
    }

    @Test
    void InvalidUsername() {
        String username = "&123hahaha";
        String password = "eafce#$";

        assertEquals(false,isValidLogin(username, password));
    }

}